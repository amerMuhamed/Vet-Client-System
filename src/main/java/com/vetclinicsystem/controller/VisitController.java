package com.vetclinicsystem.controller;

import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.dto.VisitDto;
import com.vetclinicsystem.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<ApiResponse<VisitDto>> createVisit(@RequestBody VisitDto visitDto) {
        VisitDto savedVisit = visitService.saveVisit(visitDto);
        return ResponseEntity.ok(ApiResponse.success(savedVisit, "Visit created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VisitDto>> getVisit(@PathVariable Long id) {
        VisitDto visit = visitService.getVisitById(id);
        return ResponseEntity.ok(ApiResponse.success(visit, "Visit retrieved successfully"));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<VisitDto>>> getVisitsByPet(@PathVariable Long petId) {
        List<VisitDto> visits = visitService.getVisitsByPetId(petId);
        return ResponseEntity.ok(ApiResponse.success(visits, "Visits for pet retrieved successfully"));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<VisitDto>>> getVisitsByOwner(@PathVariable Long ownerId) {
        List<VisitDto> visits = visitService.getVisitsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success(visits, "Visits for owner retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VisitDto>>> getAllVisits() {
        List<VisitDto> visits = visitService.getAllVisits();
        return ResponseEntity.ok(ApiResponse.success(visits, "All visits retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Visit deleted successfully"));
    }
}
