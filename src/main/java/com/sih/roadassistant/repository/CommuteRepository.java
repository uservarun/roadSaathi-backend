package com.sih.roadassistant.repository;

import com.sih.roadassistant.model.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, UUID> {
    List<Commute> findByUserId(UUID userId);
}