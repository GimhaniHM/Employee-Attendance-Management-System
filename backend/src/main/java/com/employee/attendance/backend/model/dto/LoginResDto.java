package com.employee.attendance.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {
    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public LoginResDto setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResDto setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
