package com.vetclinicsystem.controller;

import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.dto.PetDto;
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
    public ResponseEntity<ApiResponse<PetDto>> createPet(@RequestBody PetDto petDto) {
        PetDto savedPet = petService.createPet(petDto);
        return ResponseEntity.ok(ApiResponse.success(savedPet, "Pet created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDto>> getPet(@PathVariable Long id) {
        PetDto pet = petService.getPetById(id);
        return ResponseEntity.ok(ApiResponse.success(pet, "Pet retrieved successfully"));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByOwner(@PathVariable Long ownerId) {
        List<PetDto> pets = petService.getPetsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success(pets, "Owner's pets retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetDto>>> getAllPets() {
        List<PetDto> pets = petService.getAllPets();
        return ResponseEntity.ok(ApiResponse.success(pets, "All pets retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDto>> updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
        PetDto updatedPet = petService.updatePet(id, petDto);
        return ResponseEntity.ok(ApiResponse.success(updatedPet, "Pet updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Pet deleted successfully"));
    }

    @PostMapping("/{petId}/photos")
    public ResponseEntity<ApiResponse<PetDto>> uploadPetPhoto(
            @PathVariable Long petId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        PetDto pet = petService.addPetImage(petId, file);
        return ResponseEntity.ok(ApiResponse.success(pet, "Pet photo uploaded successfully"));
    }
}
