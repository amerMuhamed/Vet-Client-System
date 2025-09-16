package com.vetclinicsystem.dto;


import com.vetclinicsystem.model.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDto {
    private Long id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String animalKind;
    private Double weight;
    private Long ownerId;
}
