package com.sih.roadassistant.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

@Entity
@Table(name = "commutes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonIgnore
    @Column(name = "start_coordinate", nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point startCoordinate;

    @JsonIgnore
    @Column(name = "end_coordinate", nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point endCoordinate;

    @Column(name = "start_name", length = 255)
    private String startName;

    @Column(name = "end_name", length = 255)
    private String endName;

    @Transient
    public double getStartLatitude() {
        return startCoordinate != null ? startCoordinate.getY() : 0.0;
    }

    @Transient
    public double getStartLongitude() {
        return startCoordinate != null ? startCoordinate.getX() : 0.0;
    }

    @Transient
    public double getEndLatitude() {
        return endCoordinate != null ? endCoordinate.getY() : 0.0;
    }

    @Transient
    public double getEndLongitude() {
        return endCoordinate != null ? endCoordinate.getX() : 0.0;
    }
}
