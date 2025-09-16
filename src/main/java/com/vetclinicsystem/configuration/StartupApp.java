package com.vetclinicsystem.configuration;

import com.vetclinicsystem.model.*;
import com.vetclinicsystem.model.enums.Day;
import com.vetclinicsystem.model.enums.Gender;
import com.vetclinicsystem.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class StartupApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupApp.class);

    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;
    private final ProfileImageRepository profileImageRepository;

    @Override
    public void run(String... args) {
        try {
            seedAll();
        } catch (Exception e) {
            log.error("Error during startup seeding: {}", e.getMessage(), e);
        }
    }

    private void seedAll() {
        seedClinics();
        seedDoctors();
        seedOwners();
        seedPets();
        seedVisits();
        log.info("Data seeding finished.");
    }

    private void seedClinics() {
        try {
            if (clinicRepository.count() == 0) {
                Clinic c1 = Clinic.builder()
                        .name("Paws & Care Animal Hospital")
                        .address("123 Pet Street, Cairo, Egypt")
                        .phone("+201234567890")
                        .email("info@pawsandcare.com")
                        .workingDays(Arrays.asList(Day.SUNDAY, Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SATURDAY))
                        .workingHours("09:00 AM - 05:00 PM")
                        .socialNetworks(Map.of(
                                "Facebook", "https://facebook.com/pawsandcare",
                                "Instagram", "https://instagram.com/pawsandcare"
                        ))
                        .build();

                Clinic c2 = Clinic.builder()
                        .name("Happy Pets Veterinary Clinic")
                        .address("456 Animal Road, Giza, Egypt")
                        .phone("+201112223344")
                        .email("contact@happypets.com")
                        .workingDays(Arrays.asList(Day.SUNDAY, Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY, Day.SATURDAY))
                        .workingHours("09:00 AM - 05:00 PM")
                        .socialNetworks(Map.of(
                                "Facebook", "https://facebook.com/happypets",
                                "Instagram", "https://instagram.com/happypets"
                        ))
                        .build();

                clinicRepository.save(c1);
                clinicRepository.save(c2);
                log.info("Clinics seeded");
            } else {
                log.info("Clinics already exist — skipping");
            }
        } catch (ConstraintViolationException cve) {
            printValidationErrors("Clinic", cve);
        } catch (Exception e) {
            log.error("Failed to seed clinics: {}", e.getMessage(), e);
        }
    }

    private void seedDoctors() {
        try {
            if (doctorRepository.count() == 0) {
                List<Clinic> clinics = clinicRepository.findAll();
                Clinic clinic1 = clinics.size() > 0 ? clinics.get(0) : null;
                Clinic clinic2 = clinics.size() > 1 ? clinics.get(1) : clinic1;

                Doctor d1 = Doctor.builder()
                        .name("Dr. Sarah Smith")
                        .phone("+201001234567")
                        .bio("Specialized in small animals and surgery with 10 years of experience.")
                        .clinic(clinic1)
                        .build();

                Doctor d2 = Doctor.builder()
                        .name("Dr. Ahmed Johnson")
                        .phone("+201112345678")
                        .bio("Expert in exotic animals and avian medicine.")
                        .clinic(clinic2)
                        .build();

                doctorRepository.save(d1);
                doctorRepository.save(d2);
                log.info("Doctors seeded");
            } else {
                log.info("Doctors already exist — skipping");
            }
        } catch (ConstraintViolationException cve) {
            printValidationErrors("Doctor", cve);
        } catch (Exception e) {
            log.error("Failed to seed doctors: {}", e.getMessage(), e);
        }
    }

    private void seedOwners() {
        try {
            if (ownerRepository.count() == 0) {
                Owner o1 = Owner.builder()
                        .name("Mohamed Ali")
                        .email("mohamed.ali@example.com")
                        .phone("+201001112233")
                        .gender(Gender.MALE)
                        .address("Heliopolis, Cairo")
                        .build();

                Owner o2 = Owner.builder()
                        .name("Nouran Hassan")
                        .email("nouran.hassan@example.com")
                        .phone("+201002223344")
                        .gender(Gender.FEMALE)
                        .address("Dokki, Giza")
                        .build();

                ownerRepository.save(o1);
                ownerRepository.save(o2);
                log.info(" Owners seeded");
            } else {
                log.info("Owners already exist — skipping");
            }
        } catch (ConstraintViolationException cve) {
            printValidationErrors("Owner", cve);
        } catch (Exception e) {
            log.error("Failed to seed owners: {}", e.getMessage(), e);
        }
    }

    private void seedPets() {
        try {
            if (petRepository.count() == 0) {
                List<Owner> owners = ownerRepository.findAll();
                if (owners.isEmpty()) {
                    log.warn("No owners available to assign pets to. Skipping pets seeding.");
                    return;
                }
                Owner o1 = owners.get(0);
                Owner o2 = owners.size() > 1 ? owners.get(1) : o1;

                Pet p1 = Pet.builder()
                        .name("Max")
                        .animalKind("Dog")
                        .gender(Gender.MALE)
                        .dateOfBirth(LocalDate.of(2020, 5, 15))
                        .weight(32.5)
                        .owner(o1)
                        .build();

                Pet p2 = Pet.builder()
                        .name("Luna")
                        .animalKind("Cat")
                        .gender(Gender.FEMALE)
                        .dateOfBirth(LocalDate.of(2021, 2, 20))
                        .weight(4.2)
                        .owner(o2)
                        .build();

                petRepository.save(p1);
                petRepository.save(p2);
                log.info("Pets seeded");
            } else {
                log.info("Pets already exist — skipping");
            }
        } catch (ConstraintViolationException cve) {
            printValidationErrors("Pet", cve);
        } catch (Exception e) {
            log.error("Failed to seed pets: {}", e.getMessage(), e);
        }
    }

    private void seedVisits() {
        try {
            if (visitRepository.count() == 0) {
                List<Doctor> doctors = doctorRepository.findAll();
                List<Pet> pets = petRepository.findAll();
                List<Owner> owners = ownerRepository.findAll();
                List<Clinic> clinics = clinicRepository.findAll();

                if (doctors.size() >= 1 && pets.size() >= 1 && owners.size() >= 1 && clinics.size() >= 1) {
                    Visit v1 = Visit.builder()
                            .date(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                            .pet(pets.get(0))
                            .owner(owners.get(0))
                            .doctor(doctors.get(0))
                            .clinic(clinics.get(0))
                            .build();

                    Visit v2 = Visit.builder()
                            .date(LocalDateTime.now().plusDays(2).withHour(15).withMinute(30))
                            .pet(pets.size() > 1 ? pets.get(1) : pets.get(0))
                            .owner(owners.size() > 1 ? owners.get(1) : owners.get(0))
                            .doctor(doctors.size() > 1 ? doctors.get(1) : doctors.get(0))
                            .clinic(clinics.size() > 1 ? clinics.get(1) : clinics.get(0))
                            .build();

                    visitRepository.save(v1);
                    visitRepository.save(v2);
                    log.info("Visits seeded");
                } else {
                    log.warn("Not enough entities to create visits. Skipping visits seeding.");
                }
            } else {
                log.info("Visits already exist — skipping");
            }
        } catch (ConstraintViolationException cve) {
            printValidationErrors("Visit", cve);
        } catch (Exception e) {
            log.error("Failed to seed visits: {}", e.getMessage(), e);
        }
    }

    private void printValidationErrors(String entityName, ConstraintViolationException cve) {
        log.error("Validation error while saving {}: {}", entityName, cve.getMessage());
        for (ConstraintViolation<?> v : cve.getConstraintViolations()) {
            log.error(" - {}: {}", v.getPropertyPath(), v.getMessage());
        }
    }
}
