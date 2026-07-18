package com.example.clinic_backend.security;

import java.util.Collections;

import com.example.clinic_backend.entity.Doctor;
import com.example.clinic_backend.repository.DoctorRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DoctorUserDetailsService implements UserDetailsService {

    private final DoctorRepository doctorRepository;

    public DoctorUserDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Doctor doctor = doctorRepository.findByUserId(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Doctor not found with userId: " + username));

        return new User(
                doctor.getUserId(),
                doctor.getPassword(),
                Collections.emptyList()
        );
    }
}
