package com.sih.roadassistant.service;

import com.sih.roadassistant.model.Commute;
import com.sih.roadassistant.model.User;
import com.sih.roadassistant.repository.CommuteRepository;
import com.sih.roadassistant.repository.UserRepository;
import com.sih.roadassistant.util.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CommuteService {

    @Autowired
    private CommuteRepository commuteRepository;

    @Autowired
    private UserRepository userRepository;

    public Commute saveCommute(UUID userId, String name, double startLat, double startLng,
                               double endLat, double endLng, String startName, String endName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Point startPoint = GeometryUtils.createPoint(startLat, startLng);
        Point endPoint = GeometryUtils.createPoint(endLat, endLng);

        Commute commute = Commute.builder()
                .user(user)
                .name(name)
                .startCoordinate(startPoint)
                .endCoordinate(endPoint)
                .startName(startName)
                .endName(endName)
                .build();

        return commuteRepository.save(commute);
    }

    public List<Commute> getCommutesByUserId(UUID userId) {
        return commuteRepository.findByUserId(userId);
    }

    public void deleteCommute(UUID id) {
        if (!commuteRepository.existsById(id)) {
            throw new RuntimeException("Commute not found");
        }
        commuteRepository.deleteById(id);
    }
}