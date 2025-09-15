package com.vetclinicsystem.service;

import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.model.Owner;
import com.vetclinicsystem.model.Pet;
import com.vetclinicsystem.repository.OwnerRepository;
import com.vetclinicsystem.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + id));
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException("Owner not found with id: " + ownerId);
        }
        return petRepository.findByOwnerId(ownerId);
    }
}
