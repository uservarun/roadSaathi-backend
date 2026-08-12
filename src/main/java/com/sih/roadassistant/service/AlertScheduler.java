package com.sih.roadassistant.service;

import com.sih.roadassistant.model.Alert;
import com.sih.roadassistant.repository.AlertRepository;
import com.sih.roadassistant.util.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import com.sih.roadassistant.service.TripService;

@Service
public class AlertScheduler {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private TripService tripService;

    @Autowired
    private WeatherService weatherService;

    // List of flood-prone coordinate check-points (e.g. Mathura junctions) to monitor for weather risks
    private static final List<double[]> FLOOD_PRONE_POINTS = List.of(
        new double[]{27.4924, 77.6737}, // Junction 1
        new double[]{27.5010, 77.6850}  // Junction 2
    );

    /**
     * Runs every minute. Automatically reopens closed railway crossings
     * if their status hasn't been updated in the last 15 minutes.
     */
    @Scheduled(cron = "0 * * * * ?")
    public void autoReopenRailwayGates() {
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minusMinutes(15);

        // Find gates that need to be auto-reopened
        List<Alert> expiredGates = alertRepository.findAll().stream()
                .filter(a -> "RAILWAY_GATE".equalsIgnoreCase(a.getAlertType())
                        && "CLOSED".equalsIgnoreCase(a.getStatus())
                        && a.getUpdatedAt() != null
                        && a.getUpdatedAt().isBefore(fifteenMinutesAgo))
                .toList();

        for (Alert gate : expiredGates) {
            gate.setStatus("OPEN");
            gate.setUpdatedAt(LocalDateTime.now());
            alertRepository.save(gate);
            tripService.clearGateCache(gate.getId()); // Evict from active telemetry map
            System.out.println("Auto-reopened closed railway gate: " + gate.getId());
        }
    }

    /**
     * Runs every hour. Automatically checks for heavy rainfall at low-lying crossings.
     * Generates or deactivates WATER_LOGGING alerts autonomously using the weather API.
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void autoCheckWaterloggingAlerts() {
        for (double[] coord : FLOOD_PRONE_POINTS) {
            double lat = coord[0];
            double lng = coord[1];
            
            boolean isFlooding = weatherService.checkHeavyRain(lat, lng);
            Point geomPoint = GeometryUtils.createPoint(lat, lng);

            // Find existing active flood alerts within 100 meters
            List<Alert> existing = alertRepository.findActiveAlertsWithinRadius(geomPoint, 100.0);
            Alert floodAlert = existing.stream()
                    .filter(a -> "WATER_LOGGING".equalsIgnoreCase(a.getAlertType()))
                    .findFirst()
                    .orElse(null);

            if (isFlooding && floodAlert == null) {
                // Generate waterlogging alert automatically
                Alert newAlert = Alert.builder()
                        .alertType("WATER_LOGGING")
                        .status("ACTIVE")
                        .coordinate(geomPoint)
                        .description("Automated Alert: Heavy rainfall detected by Weather API. High risk of waterlogging.")
                        .isActive(true)
                        .build();
                alertRepository.save(newAlert);
                System.out.println("ALERT: Automated flood alert generated at (" + lat + ", " + lng + ")");
            } else if (!isFlooding && floodAlert != null) {
                // Deactivate the alert since rain stopped
                floodAlert.setIsActive(false);
                alertRepository.save(floodAlert);
                System.out.println("ALERT: Automated flood alert deactivated at (" + lat + ", " + lng + ")");
            }
        }
    }
}
