package com.vetclinicsystem.model;

import com.vetclinicsystem.model.enums.Day;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;

@Entity
@Table(
        name = "clinics",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"phone"}),
                @UniqueConstraint(columnNames = {"email"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Clinic name is required")
    @Size(min = 2, max = 100, message = "Clinic name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @NotBlank(message = "Phone is required")
    @Pattern( regexp = "^(\\+\\d{1,3})?[0-9]{10,15}$",
    message = "Phone number must be between 10 and 15 digits and may include country code (e.g., +20)")
    @Column(unique = true, nullable = false)
    private String phone;

    @ElementCollection(targetClass = Day.class)
    @CollectionTable(
            name = "clinic_working_days",
            joinColumns = @JoinColumn(name = "clinic_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private List<Day> workingDays = new ArrayList<>();

    @NotBlank(message = "Working hours are required")
    @Column(name = "working_hours")
    private String workingHours;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(
            name = "clinic_social_networks",
            joinColumns = @JoinColumn(name = "clinic_id")
    )
    @MapKeyColumn(name = "network_name")
    @Column(name = "url")
    private Map<String, String> socialNetworks = new HashMap<>();


    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();


}
