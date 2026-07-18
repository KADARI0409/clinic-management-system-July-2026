package com.example.clinic_backend.service;

import java.util.List;
import org.slf4j.*;

import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.clinic_backend.dto.DoctorSignupRequest;
import com.example.clinic_backend.dto.DoctorUpdateRequest;
import com.example.clinic_backend.dto.LoginRequest;
import com.example.clinic_backend.dto.LoginResponse;
import com.example.clinic_backend.entity.Doctor;
import com.example.clinic_backend.repository.DoctorRepository;
import com.example.clinic_backend.security.JwtService;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);

    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Doctor createDoctor(DoctorSignupRequest request) {
        if (doctorRepository.existsByUserId(request.getUserId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User ID already exists");
        }
        Doctor doctor = new Doctor();
        doctor.setUserId(request.getUserId());
        // doctor.setPassword(request.getPassword());
        doctor.setPassword((passwordEncoder.encode(request.getPassword())));
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorByUserId(String userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor User Id not found"));
    }

    public LoginResponse login(LoginRequest request) {
        Doctor doctor = doctorRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid User ID"));

        if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Password");
        }
        String token = jwtService.generateToken(doctor.getUserId());
        return new LoginResponse("Login Successfully", token);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not Found"));
        doctorRepository.delete(doctor);
    }

    public Doctor updateDoctor(Long id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        doctor.setUserId(request.getUserId());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        return doctorRepository.save(doctor);
    }

}
