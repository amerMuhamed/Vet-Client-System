package com.vetclinicsystem.service;

import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.model.Clinic;
import com.vetclinicsystem.model.Doctor;
import com.vetclinicsystem.repository.ClinicRepository;
import com.vetclinicsystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;

    public Clinic createClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public Clinic getClinicById(Long id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
    }

    public List<Doctor> getDoctorsByClinicId(Long clinicId) {
        if (!clinicRepository.existsById(clinicId)) {
            throw new ResourceNotFoundException("Clinic not found with id: " + clinicId);
        }
        return doctorRepository.findByClinicId(clinicId);
    }

    public List<Clinic> searchByPhone(String phone) {
        return clinicRepository.findByPhone(phone);
    }

    public List<Clinic> searchByAddress(String address) {
        return clinicRepository.findByAddressContainingIgnoreCase(address);
    }

    public List<Clinic> searchClinics(String phone, String address) {
        if (phone != null && !phone.isEmpty() && address != null && !address.isEmpty()) {
            return clinicRepository.findByPhoneAndAddressContainingIgnoreCase(phone, address);
        } else if (phone != null && !phone.isEmpty()) {
            return searchByPhone(phone);
        } else if (address != null && !address.isEmpty()) {
            return searchByAddress(address);
        } else {
            return new ArrayList<>();
        }
    }
}
