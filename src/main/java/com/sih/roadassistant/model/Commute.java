package com.sih.roadassistant.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(name = "start_coordinate", nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point startCoordinate;

    @Column(name = "end_coordinate", nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point endCoordinate;

    @Column(name = "start_name", length = 255)
    private String startName;

    @Column(name = "end_name", length = 255)
    private String endName;

}
