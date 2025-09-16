package com.vetclinicsystem.mapper;

import com.vetclinicsystem.dto.OwnerDto;
import com.vetclinicsystem.model.Owner;

public class OwnerMapper {
    public static OwnerDto toDto(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .email(owner.getEmail())
                .phone(owner.getPhone())
                .gender(owner.getGender())
                .address(owner.getAddress())
                .build();
    }

    public static Owner toEntity(OwnerDto dto) {
        return Owner.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .build();
    }
}
