package com.vetclinicsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Doctor name is required")
    @Size(min = 2, max = 100, message = "Doctor name must be between 2 and 50 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Pattern(regexp = "^(\\+\\d{1,3})?[0-9]{10,15}$",
            message = "Phone number must be between 10 and 15 digits and may include country code (e.g., +20)")
    @Column(nullable = false, unique = true)
    private String phone;

    @OneToOne
    @JoinColumn(name = "profile_image_id")
    public ProfileImage profileImage;

    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    private String bio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();


}
