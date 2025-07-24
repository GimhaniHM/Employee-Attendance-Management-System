package com.employee.attendance.backend.controller;

import com.employee.attendance.backend.model.dto.AuthReqDto;
import com.employee.attendance.backend.model.dto.LoginResDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.service.AuthService;
import com.employee.attendance.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<EmployeeDto> register(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployeeDto = authService.register(employeeDto);
        return new ResponseEntity<>(savedEmployeeDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> authenticate(@RequestBody AuthReqDto authReqDto) {
        UserDetails authenticatedUser = authService.authenticate(authReqDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

//        LoginResDto loginResponse = new LoginResDto().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        LoginResDto loginResponse = new LoginResDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
