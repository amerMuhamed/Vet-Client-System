package com.vetclinicsystem.mapper;

import com.vetclinicsystem.dto.ClinicDto;
import com.vetclinicsystem.model.Clinic;

public class ClinicMapper {
    public static ClinicDto toDto(Clinic clinic) {
        return ClinicDto.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .address(clinic.getAddress())
                .phone(clinic.getPhone())
                .workingDays(clinic.getWorkingDays())
                .workingHours(clinic.getWorkingHours())
                .email(clinic.getEmail())
                .socialNetworks(clinic.getSocialNetworks())
                .build();
    }

    public static Clinic toEntity(ClinicDto dto) {
        return Clinic.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .workingDays(dto.getWorkingDays())
                .workingHours(dto.getWorkingHours())
                .email(dto.getEmail())
                .socialNetworks(dto.getSocialNetworks())
                .build();
    }
}
