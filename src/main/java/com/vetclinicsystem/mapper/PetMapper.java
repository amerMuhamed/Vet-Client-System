package com.vetclinicsystem.mapper;

import com.vetclinicsystem.dto.PetDto;
import com.vetclinicsystem.model.Owner;
import com.vetclinicsystem.model.Pet;

public class PetMapper {
    public static PetDto toDto(Pet pet) {
        return PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .gender(pet.getGender())
                .dateOfBirth(pet.getDateOfBirth())
                .animalKind(pet.getAnimalKind())
                .weight(pet.getWeight())
                .ownerId(pet.getOwner().getId())
                .build();
    }

    public static Pet toEntity(PetDto dto, Owner owner) {
        return Pet.builder()
                .id(dto.getId())
                .name(dto.getName())
                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .animalKind(dto.getAnimalKind())
                .weight(dto.getWeight())
                .owner(owner)
                .build();
    }
}
