package com.vetclinicsystem.service;

import com.vetclinicsystem.dto.OwnerDto;
import com.vetclinicsystem.dto.PetDto;
import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.mapper.OwnerMapper;
import com.vetclinicsystem.mapper.PetMapper;
import com.vetclinicsystem.model.Owner;
import com.vetclinicsystem.model.Pet;
import com.vetclinicsystem.repository.OwnerRepository;
import com.vetclinicsystem.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public OwnerDto createOwner(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return OwnerMapper.toDto(savedOwner); // نستخدمه static
    }
    public Owner getOwnerEntityById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
    }

    public OwnerDto getOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
        return OwnerMapper.toDto(owner);
    }

    public List<PetDto> getPetsByOwnerId(Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException("Owner not found with id: " + ownerId);
        }
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        return pets.stream()
                .map(PetMapper::toDto) // برضه static
                .collect(Collectors.toList());
    }

    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerMapper::toDto) // برضه static
                .collect(Collectors.toList());
    }
}
