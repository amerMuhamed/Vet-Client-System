package com.vetclinicsystem.repository;

import com.vetclinicsystem.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPetId(Long petId);
    List<Visit> findByOwnerId(Long ownerId);
}
