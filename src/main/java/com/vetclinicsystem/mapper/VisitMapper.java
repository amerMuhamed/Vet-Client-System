package com.vetclinicsystem.mapper;

import com.vetclinicsystem.dto.VisitDto;
import com.vetclinicsystem.model.*;

public class VisitMapper {
    public static VisitDto toDto(Visit visit) {
        return VisitDto.builder()
                .id(visit.getId())
                .date(visit.getDate())
                .petId(visit.getPet().getId())
                .ownerId(visit.getOwner().getId())
                .doctorId(visit.getDoctor().getId())
                .clinicId(visit.getClinic().getId())
                .build();
    }

    public static Visit toEntity(VisitDto dto, Pet pet, Owner owner, Doctor doctor, Clinic clinic) {
        return Visit.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .pet(pet)
                .owner(owner)
                .doctor(doctor)
                .clinic(clinic)
                .build();
    }
}
