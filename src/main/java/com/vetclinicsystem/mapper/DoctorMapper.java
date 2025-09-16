package com.vetclinicsystem.mapper;

import com.vetclinicsystem.dto.DoctorDto;
import com.vetclinicsystem.model.Clinic;
import com.vetclinicsystem.model.Doctor;
import com.vetclinicsystem.model.ProfileImage;

public class DoctorMapper {
    public static DoctorDto toDto(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .phone(doctor.getPhone())
                .bio(doctor.getBio())
                .profileImageUrl(
                        doctor.getProfileImage() != null ? doctor.getProfileImage().getProfileImageUrl() : null
                )
                .clinicId(
                        doctor.getClinic() != null ? doctor.getClinic().getId() : null
                )
                .build();
    }

    public static Doctor toEntity(DoctorDto dto, Clinic clinic, ProfileImage profileImage) {
        return Doctor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .phone(dto.getPhone())
                .bio(dto.getBio())
                .clinic(clinic)
                .profileImage(profileImage)
                .build();
    }
}
