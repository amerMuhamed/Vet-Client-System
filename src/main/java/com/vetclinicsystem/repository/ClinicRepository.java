package com.vetclinicsystem.repository;

import com.vetclinicsystem.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findByPhone(String phone);
    List<Clinic> findByAddressContainingIgnoreCase(String address);
    List<Clinic> findByPhoneAndAddressContainingIgnoreCase(String phone, String address);

}
