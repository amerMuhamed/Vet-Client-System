package  com.vetclinicsystem.service;
import com.vetclinicsystem.dto.DoctorDto;
import com.vetclinicsystem.exception.ResourceNotFoundException;
import com.vetclinicsystem.mapper.DoctorMapper;
import com.vetclinicsystem.model.Clinic;
import com.vetclinicsystem.model.Doctor;
import com.vetclinicsystem.model.ProfileImage;
import com.vetclinicsystem.repository.ClinicRepository;
import com.vetclinicsystem.repository.DoctorRepository;
import com.vetclinicsystem.repository.ProfileImageRepository;
import com.vetclinicsystem.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final ProfileImageService profileImageService;
    private final ProfileImageRepository profileImageRepository;

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    public DoctorDto getDoctorDtoById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return DoctorMapper.toDto(doctor);
    }
    public Doctor assignDoctorToClinic(Long doctorId, Long clinicId) {
        Doctor doctor = getDoctorById(doctorId);
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));

        doctor.setClinic(clinic);
        clinic.getDoctors().add(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor deAssignDoctorFromClinic(Long doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        Clinic clinic = doctor.getClinic();

        if (clinic != null) {
            clinic.getDoctors().remove(doctor);
            doctor.setClinic(null);
        }
        return doctorRepository.save(doctor);
    }

    public Doctor assignProfileImage(Long doctorId, MultipartFile file) throws IOException {
        Doctor doctor = getDoctorById(doctorId);

        Map<String, String> uploadResult = profileImageService.uploadImage(file.getBytes());

        ProfileImage profileImage = ProfileImage.builder()
                .profileImageUrl(uploadResult.get("imageUrl"))
                .publicId(uploadResult.get("publicId"))
                .build();

        profileImageRepository.save(profileImage);
        doctor.setProfileImage(profileImage);

        return doctorRepository.save(doctor);
    }

    public void deleteProfileImage(Long doctorId) throws IOException {
        Doctor doctor = getDoctorById(doctorId);

        ProfileImage image = doctor.getProfileImage();
        if (image != null) {
            profileImageService.deleteImage(image.getPublicId());
            doctor.setProfileImage(null);
            doctorRepository.save(doctor);
        }
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

}
