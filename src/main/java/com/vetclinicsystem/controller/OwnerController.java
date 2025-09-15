package com.vetclinicsystem.controller;
import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.model.Owner;
import com.vetclinicsystem.model.Pet;
import com.vetclinicsystem.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Owner>> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.getOwnerById(id);
        return ResponseEntity.ok(ApiResponse.success(owner, "Owner retrieved successfully"));
    }

    @GetMapping("/{ownerId}/pets")
    public ResponseEntity<ApiResponse<List<Pet>>> getPetsByOwnerId(@PathVariable Long ownerId) {
        List<Pet> pets = ownerService.getPetsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success(pets, "Owner's pets retrieved successfully"));
    }
}
