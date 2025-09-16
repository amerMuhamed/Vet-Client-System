package com.vetclinicsystem.service;

import com.vetclinicsystem.dto.VisitDto;
import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.mapper.VisitMapper;
import com.vetclinicsystem.model.*;
import com.vetclinicsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;

    public VisitDto saveVisit(VisitDto dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + dto.getPetId()));
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + dto.getOwnerId()));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + dto.getClinicId()));

        Visit visit = VisitMapper.toEntity(dto, pet, owner, doctor, clinic);
        return VisitMapper.toDto(visitRepository.save(visit));
    }

    public VisitDto getVisitById(Long id) {
        return visitRepository.findById(id)
                .map(VisitMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
    }

    public List<VisitDto> getVisitsByPetId(Long petId) {
        return visitRepository.findByPetId(petId)
                .stream()
                .map(VisitMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VisitDto> getVisitsByOwnerId(Long ownerId) {
        return visitRepository.findByOwnerId(ownerId)
                .stream()
                .map(VisitMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteVisit(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
        visitRepository.delete(visit);
    }

    public List<VisitDto> getAllVisits() {
        return visitRepository.findAll()
                .stream()
                .map(VisitMapper::toDto)
                .collect(Collectors.toList());
    }
}
