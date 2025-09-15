package com.vetclinicsystem.service;

import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.model.Pet;
import com.vetclinicsystem.model.ProfileImage;
import com.vetclinicsystem.repository.PetRepository;
import com.vetclinicsystem.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final ProfileImageService profileImageService;
    private final ProfileImageRepository profileImageRepository;

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

    public Pet updatePet(Long id, Pet petDetails) {
        Pet pet = getPetById(id);
        pet.setName(petDetails.getName());
        pet.setGender(petDetails.getGender());
        pet.setDateOfBirth(petDetails.getDateOfBirth());
        pet.setAnimalKind(petDetails.getAnimalKind());
        pet.setWeight(petDetails.getWeight());
        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        Pet pet = getPetById(id);
        petRepository.delete(pet);
    }

    public Pet addPetImage(Long petId, MultipartFile file) throws IOException {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        Map<String, String> uploadResult = profileImageService.uploadImage(file.getBytes());

        ProfileImage image = ProfileImage.builder()
                .profileImageUrl(uploadResult.get("imageUrl"))
                .publicId(uploadResult.get("publicId"))
                .build();
        profileImageRepository.save(image);

        pet.getPhotos().add(image);
        return petRepository.save(pet);
    }
}

