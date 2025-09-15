package com.vetclinicsystem.controller;
import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.model.Clinic;
import com.vetclinicsystem.model.Doctor;
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
    public ResponseEntity<ApiResponse<List<Doctor>>> getDoctorsByClinicId(@PathVariable Long clinicId) {
        List<Doctor> doctors = clinicService.getDoctorsByClinicId(clinicId);
        return ResponseEntity.ok(ApiResponse.success(doctors, "Doctors retrieved successfully for clinic"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Clinic>>> searchClinics(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address
    ) {
        List<Clinic> clinics = clinicService.searchClinics(phone, address);
        return ResponseEntity.ok(ApiResponse.success(clinics, "Clinics search completed successfully"));
    }
}
