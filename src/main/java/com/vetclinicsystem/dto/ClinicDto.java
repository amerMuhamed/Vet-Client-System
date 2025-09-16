package com.vetclinicsystem.dto;

import com.vetclinicsystem.model.enums.Day;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private List<Day> workingDays;
    private String workingHours;
    private String email;
    private Map<String, String> socialNetworks;
}
