package com.vetclinicsystem.service;

import com.vetclinicsystem.dto.ClinicDto;
import com.vetclinicsystem.dto.DoctorDto;
import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.mapper.ClinicMapper;
import com.vetclinicsystem.mapper.DoctorMapper;
import com.vetclinicsystem.model.Clinic;
import com.vetclinicsystem.model.Doctor;
import com.vetclinicsystem.repository.ClinicRepository;
import com.vetclinicsystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;

    public ClinicDto saveClinic(ClinicDto clinicDto) {
        Clinic clinic = ClinicMapper.toEntity(clinicDto);
        Clinic saved = clinicRepository.save(clinic);
        return ClinicMapper.toDto(saved);
    }

    public ClinicDto getClinicById(Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + id));
        return ClinicMapper.toDto(clinic);
    }

    public List<DoctorDto> getDoctorsByClinicId(Long clinicId) {
        if (!clinicRepository.existsById(clinicId)) {
            throw new ResourceNotFoundException("Clinic not found with id: " + clinicId);
        }
        List<Doctor> doctors = doctorRepository.findByClinicId(clinicId);
        return doctors.stream()
                .map(DoctorMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClinicDto> searchByPhone(String phone) {
        return clinicRepository.findByPhone(phone).stream()
                .map(ClinicMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClinicDto> searchByAddress(String address) {
        return clinicRepository.findByAddressContainingIgnoreCase(address).stream()
                .map(ClinicMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ClinicDto> searchClinics(String phone, String address) {
        List<Clinic> clinics;
        if (phone != null && !phone.isEmpty() && address != null && !address.isEmpty()) {
            clinics = clinicRepository.findByPhoneAndAddressContainingIgnoreCase(phone, address);
        } else if (phone != null && !phone.isEmpty()) {
            clinics = clinicRepository.findByPhone(phone);
        } else if (address != null && !address.isEmpty()) {
            clinics = clinicRepository.findByAddressContainingIgnoreCase(address);
        } else {
            clinics = new ArrayList<>();
        }
        return clinics.stream().map(ClinicMapper::toDto).collect(Collectors.toList());
    }

    public List<ClinicDto> getAllClinics() {
        return clinicRepository.findAll().stream()
                .map(ClinicMapper::toDto)
                .collect(Collectors.toList());
    }
}
