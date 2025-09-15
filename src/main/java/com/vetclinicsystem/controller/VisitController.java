package com.vetclinicsystem.controller;
import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.model.Visit;
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
    public ResponseEntity<ApiResponse<Visit>> createVisit(@RequestBody Visit visit) {
        Visit savedVisit = visitService.createVisit(visit);
        return ResponseEntity.ok(ApiResponse.success(savedVisit, "Visit created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Visit>> getVisit(@PathVariable Long id) {
        Visit visit = visitService.getVisitById(id);
        return ResponseEntity.ok(ApiResponse.success(visit, "Visit retrieved successfully"));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<Visit>>> getVisitsByPet(@PathVariable Long petId) {
        List<Visit> visits = visitService.getVisitsByPetId(petId);
        return ResponseEntity.ok(ApiResponse.success(visits, "Visits for pet retrieved successfully"));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<Visit>>> getVisitsByOwner(@PathVariable Long ownerId) {
        List<Visit> visits = visitService.getVisitsByOwnerId(ownerId);
        return ResponseEntity.ok(ApiResponse.success(visits, "Visits for owner retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Visit deleted successfully"));
    }
}
