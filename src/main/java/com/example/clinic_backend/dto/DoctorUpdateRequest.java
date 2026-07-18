package com.example.clinic_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class DoctorUpdateRequest {

    @NotBlank(message = "User Id is required")
    private String userId;

    @NotBlank(message = "Password Id is required")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
