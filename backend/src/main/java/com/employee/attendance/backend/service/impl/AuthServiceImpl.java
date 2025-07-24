package com.employee.attendance.backend.service.impl;

import com.employee.attendance.backend.model.dto.AuthReqDto;
import com.employee.attendance.backend.model.dto.LoginResDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.model.mapper.EmployeeMapper;
import com.employee.attendance.backend.model.repository.EmployeeRepository;
import com.employee.attendance.backend.service.AuthService;
import com.employee.attendance.backend.service.EmployeeService;
import com.employee.attendance.backend.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;

    @Override
    public EmployeeDto register(EmployeeDto request) {
        // Encode the password
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Employee employee = EmployeeMapper.mapToEmployee(request);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);

    }

    @Override
    public Employee authenticate(AuthReqDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Employee user = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }
}
