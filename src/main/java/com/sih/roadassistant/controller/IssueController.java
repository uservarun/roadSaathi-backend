package com.sih.roadassistant.controller;

import com.sih.roadassistant.model.Pothole;
import com.sih.roadassistant.model.Alert;
import com.sih.roadassistant.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issues")
@CrossOrigin(origins = "*")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/report", consumes = "multipart/form-data")
    public ResponseEntity<Pothole> reportPothole(
            @RequestParam("userId") UUID userId,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        if (imageFile != null && !imageFile.isEmpty()) {
            com.sih.roadassistant.util.FileValidator.validateImage(imageFile);
        }

        byte[] imageBytes = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imageBytes = imageFile.getBytes();
            } catch (Exception e) {
                throw new RuntimeException("Failed to read image file", e);
            }
        }

        Pothole pothole = issueService.reportPothole(userId, latitude, longitude, description, imageBytes);
        return new ResponseEntity<>(pothole, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/gate")
    public ResponseEntity<Alert> updateRailwayGate(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("status") String status,
            @RequestParam("userId") UUID userId) {
        Alert alert = issueService.updateRailwayGateStatus(latitude, longitude, status, userId);
        return ResponseEntity.ok(alert);
    }

    @GetMapping("/nearby")
    public ResponseEntity<Map<String, Object>> getNearbyHazards(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "radius", defaultValue = "5000") double radiusMeters) {
        Map<String, Object> hazards = issueService.getNearbyHazards(latitude, longitude, radiusMeters);
        return ResponseEntity.ok(hazards);
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @PutMapping("/pothole/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pothole> updatePotholeStatus(
            @PathVariable("id") UUID id,
            @RequestParam("status") String status,
            @RequestParam(value = "severity", required = false) String severity) {
        Pothole pothole = issueService.updatePotholeStatus(id, status, severity);
        return ResponseEntity.ok(pothole);
    }

    @PutMapping("/alert/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Alert> updateAlertStatus(
            @PathVariable("id") UUID id,
            @RequestParam("status") String status,
            @RequestParam(value = "isActive", required = false) Boolean isActive) {
        Alert alert = issueService.updateAlertStatus(id, status, isActive);
        return ResponseEntity.ok(alert);
    }
}