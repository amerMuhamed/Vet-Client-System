package com.vetclinicsystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    private Long id;
    private String name;
    private String phone;
    private String bio;
    private String profileImageUrl;
    private Long clinicId;
}
