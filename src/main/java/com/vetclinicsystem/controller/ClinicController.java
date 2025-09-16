package com.vetclinicsystem.controller;

import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.dto.ClinicDto;
import com.vetclinicsystem.dto.DoctorDto;
import com.vetclinicsystem.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping("/{clinicId}/doctors")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> getDoctorsByClinicId(@PathVariable Long clinicId) {
        List<DoctorDto> doctors = clinicService.getDoctorsByClinicId(clinicId);
        return ResponseEntity.ok(ApiResponse.success(doctors, "Doctors retrieved successfully for clinic"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ClinicDto>>> searchClinics(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address
    ) {
        List<ClinicDto> clinics = clinicService.searchClinics(phone, address);
        return ResponseEntity.ok(ApiResponse.success(clinics, "Clinics search completed successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClinicDto>>> getAllClinics() {
        List<ClinicDto> clinics = clinicService.getAllClinics();
        return ResponseEntity.ok(ApiResponse.success(clinics, "All clinics retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClinicDto>> createClinic(@RequestBody ClinicDto clinicDto) {
        ClinicDto saved = clinicService.saveClinic(clinicDto);
        return ResponseEntity.ok(ApiResponse.success(saved, "Clinic created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClinicDto>> getClinicById(@PathVariable Long id) {
        ClinicDto clinic = clinicService.getClinicById(id);
        return ResponseEntity.ok(ApiResponse.success(clinic, "Clinic retrieved successfully"));
    }
}
