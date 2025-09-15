package com.vetclinicsystem.service;

import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.model.Clinic;
import com.vetclinicsystem.model.Doctor;
import com.vetclinicsystem.model.ProfileImage;
import com.vetclinicsystem.repository.ClinicRepository;
import com.vetclinicsystem.repository.DoctorRepository;
import com.vetclinicsystem.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final ProfileImageService profileImageService;
    private final ProfileImageRepository profileImageRepository;
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }
    public Doctor assignDoctorToClinic(Long doctorId, Long clinicId) {
        Doctor doctor = getDoctorById(doctorId);
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
        doctor.setClinic(clinic);
        clinic.getDoctors().add(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor deAssignDoctorFromClinic(Long doctorId) {
        Doctor doctor = getDoctorById(doctorId);

        Clinic clinic = doctor.getClinic();
        if (clinic != null) {
            clinic.getDoctors().remove(doctor);
            doctor.setClinic(null);
        }

        return doctorRepository.save(doctor);
    }
    public Doctor assignProfileImage(Long doctorId, MultipartFile file) throws IOException {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        // رفع الصورة على Cloudinary
        Map<String, String> uploadResult = profileImageService.uploadImage(file.getBytes());

        // إنشاء ProfileImage وربطه بالـ Doctor
        ProfileImage profileImage = ProfileImage.builder()
                .profileImageUrl(uploadResult.get("imageUrl"))
                .publicId(uploadResult.get("publicId"))
                .build();
        profileImageRepository.save(profileImage);

        doctor.setProfileImage(profileImage);
        return doctorRepository.save(doctor);
    }

    public void deleteProfileImage(Long doctorId) throws IOException {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        ProfileImage image = doctor.getProfileImage();
        if (image != null) {
            profileImageService.deleteImage(image.getPublicId());
            doctor.setProfileImage(null);
            doctorRepository.save(doctor);
        }

}
}
