package com.vetclinicsystem.controller;
import com.vetclinicsystem.model.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.model.Pet;
import com.vetclinicsystem.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<ApiResponse<Pet>> createPet(@RequestBody Pet pet) {
        Pet savedPet = petService.createPet(pet);
        return ResponseEntity.ok(ApiResponse.success(savedPet, "Pet created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pet>> getPet(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.ok(ApiResponse.success(pet, "Pet retrieved successfully"));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<Pet>>> getPetsByOwner(@PathVariable Long ownerId) {
        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success(pets, "Owner's pets retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Pet>> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        Pet updatedPet = petService.updatePet(id, pet);
        return ResponseEntity.ok(ApiResponse.success(updatedPet, "Pet updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Pet deleted successfully"));
    }

    @PostMapping("/{petId}/photos")
    public ResponseEntity<ApiResponse<Pet>> uploadPetPhoto(
            @PathVariable Long petId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Pet pet = petService.addPetImage(petId, file);
        return ResponseEntity.ok(ApiResponse.success(pet, "Pet photo uploaded successfully"));
    }
}
