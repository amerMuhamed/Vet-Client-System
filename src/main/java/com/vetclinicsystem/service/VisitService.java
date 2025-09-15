package com.vetclinicsystem.service;

import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.model.Visit;
import com.vetclinicsystem.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;

    public Visit createVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
    }

    public List<Visit> getVisitsByPetId(Long petId) {
        return visitRepository.findByPetId(petId);
    }

    public List<Visit> getVisitsByOwnerId(Long ownerId) {
        return visitRepository.findByOwnerId(ownerId);
    }

    public void deleteVisit(Long id) {
        Visit visit = getVisitById(id);
        visitRepository.delete(visit);
    }
}
