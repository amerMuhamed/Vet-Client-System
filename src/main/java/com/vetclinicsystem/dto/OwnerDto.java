package com.vetclinicsystem.dto;

import com.vetclinicsystem.model.enums.Gender;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private String address;
}
