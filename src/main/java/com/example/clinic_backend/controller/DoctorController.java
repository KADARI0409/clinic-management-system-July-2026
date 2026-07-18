package com.example.clinic_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clinic_backend.dto.ApiResponse;
import com.example.clinic_backend.dto.DoctorSignupRequest;
import com.example.clinic_backend.dto.DoctorUpdateRequest;
import com.example.clinic_backend.dto.LoginRequest;
import com.example.clinic_backend.dto.LoginResponse;
import com.example.clinic_backend.entity.Doctor;
import com.example.clinic_backend.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController

@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody DoctorSignupRequest request) {
        Doctor savedDoctor = doctorService.createDoctor(request);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(doctorService.login(request));
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Doctor> getDoctorByUserId(@PathVariable String userId) {
        Doctor doctor = doctorService.getDoctorByUserId(userId);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(new ApiResponse("Doctor deleted Successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateRequest request) {
        Doctor updateDoctor = doctorService.updateDoctor(id, request);
        return ResponseEntity.ok(updateDoctor);
    }

}
