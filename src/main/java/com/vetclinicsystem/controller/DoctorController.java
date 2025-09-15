package com.vetclinicsystem.controller;
import com.vetclinicsystem.dto.ApiResponse;
import com.vetclinicsystem.model.Doctor;
import com.vetclinicsystem.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PutMapping("/{doctorId}/assign/{clinicId}")
    public ResponseEntity<ApiResponse<Doctor>> assignDoctorToClinic(
            @PathVariable Long doctorId,
            @PathVariable Long clinicId
    ) {
        Doctor updatedDoctor = doctorService.assignDoctorToClinic(doctorId, clinicId);
        return ResponseEntity.ok(ApiResponse.success(updatedDoctor, "Doctor assigned to clinic successfully"));
    }

    @PutMapping("/{doctorId}/deassign")
    public ResponseEntity<ApiResponse<Doctor>> deAssignDoctorFromClinic(
            @PathVariable Long doctorId
    ) {
        Doctor updatedDoctor = doctorService.deAssignDoctorFromClinic(doctorId);
        return ResponseEntity.ok(ApiResponse.success(updatedDoctor, "Doctor de-assigned from clinic successfully"));
    }

    @PostMapping("/{doctorId}/profile-image")
    public ResponseEntity<ApiResponse<Doctor>> uploadProfileImage(
            @PathVariable Long doctorId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Doctor doctor = doctorService.assignProfileImage(doctorId, file);
        return ResponseEntity.ok(ApiResponse.success(doctor, "Profile image uploaded successfully"));
    }

    @DeleteMapping("/{doctorId}/profile-image")
    public ResponseEntity<ApiResponse<String>> deleteProfileImage(@PathVariable Long doctorId) throws IOException {
        doctorService.deleteProfileImage(doctorId);
        return ResponseEntity.ok(ApiResponse.success(null, "Profile image deleted successfully"));
    }
}
