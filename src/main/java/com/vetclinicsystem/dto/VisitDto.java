package com.vetclinicsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitDto {
    private Long id;
    private LocalDateTime date;
    private Long petId;
    private Long ownerId;
    private Long doctorId;
    private Long clinicId;
}
