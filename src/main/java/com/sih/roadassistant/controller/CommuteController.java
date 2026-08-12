package com.sih.roadassistant.controller;

import com.sih.roadassistant.model.Commute;
import com.sih.roadassistant.service.CommuteService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/commutes")
@CrossOrigin(origins = "*")
public class CommuteController {

    @Autowired
    private CommuteService commuteService;

    @PostMapping
    public ResponseEntity<Commute> saveCommute(@RequestBody CommuteRequest request) {
        Commute commute = commuteService.saveCommute(
                request.getUserId(),
                request.getName(),
                request.getStartLatitude(),
                request.getStartLongitude(),
                request.getEndLatitude(),
                request.getEndLongitude(),
                request.getStartName(),
                request.getEndName()
        );
        return new ResponseEntity<>(commute, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Commute>> getCommutes(@PathVariable("userId") UUID userId) {
        List<Commute> commutes = commuteService.getCommutesByUserId(userId);
        return ResponseEntity.ok(commutes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommute(@PathVariable("id") UUID id) {
        commuteService.deleteCommute(id);
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class CommuteRequest {
        private UUID userId;
        private String name;
        private double startLatitude;
        private double startLongitude;
        private double endLatitude;
        private double endLongitude;
        private String startName;
        private String endName;
    }
}