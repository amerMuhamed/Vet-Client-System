package com.vetclinicsystem.controller;

import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.dto.OwnerDto;
import com.vetclinicsystem.dto.PetDto;
import com.vetclinicsystem.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OwnerDto>> getOwnerById(@PathVariable Long id) {
        OwnerDto owner = ownerService.getOwnerById(id);
        return ResponseEntity.ok(ApiResponse.success(owner, "Owner retrieved successfully"));
    }

    @GetMapping("/{ownerId}/pets")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByOwnerId(@PathVariable Long ownerId) {
        List<PetDto> pets = ownerService.getPetsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success(pets, "Owner's pets retrieved successfully"));
    }
}
