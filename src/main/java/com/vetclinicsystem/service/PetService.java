package com.vetclinicsystem.service;

import com.vetclinicsystem.dto.PetDto;
import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.mapper.PetMapper;
import com.vetclinicsystem.model.Owner;
import com.vetclinicsystem.model.Pet;
import com.vetclinicsystem.model.ProfileImage;
import com.vetclinicsystem.repository.OwnerRepository;
import com.vetclinicsystem.repository.PetRepository;
import com.vetclinicsystem.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final ProfileImageService profileImageService;
    private final ProfileImageRepository profileImageRepository;
    private final OwnerService ownerService;
    private final OwnerRepository ownerRepository;

    public PetDto createPet(PetDto petDto) {
        Owner owner = ownerRepository.findById(petDto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + petDto.getOwnerId()));

        Pet pet = PetMapper.toEntity(petDto, owner);
        Pet savedPet = petRepository.save(pet);

        return PetMapper.toDto(savedPet);
    }


    public PetDto getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
        return PetMapper.toDto(pet);
    }

    public List<PetDto> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId)
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }

    public PetDto updatePet(Long id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));

        Owner owner = ownerRepository.getById(petDto.getOwnerId());
        Pet updatedPet = PetMapper.toEntity(petDto, owner);
        updatedPet.setId(existingPet.getId());

        return PetMapper.toDto(petRepository.save(updatedPet));
    }

    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
        petRepository.delete(pet);
    }

    public PetDto addPetImage(Long petId, MultipartFile file) throws IOException {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        Map<String, String> uploadResult = profileImageService.uploadImage(file.getBytes());

        ProfileImage image = ProfileImage.builder()
                .profileImageUrl(uploadResult.get("imageUrl"))
                .publicId(uploadResult.get("publicId"))
                .build();
        profileImageRepository.save(image);

        pet.getPhotos().add(image);
        Pet updatedPet = petRepository.save(pet);

        return PetMapper.toDto(updatedPet);
    }

    public List<PetDto> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(PetMapper::toDto)
                .collect(Collectors.toList());
    }
}
