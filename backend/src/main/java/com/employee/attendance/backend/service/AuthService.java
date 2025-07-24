package com.employee.attendance.backend.service;

import com.employee.attendance.backend.model.dto.AuthReqDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.dto.LoginResDto;
import com.employee.attendance.backend.model.entity.Employee;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    EmployeeDto register(EmployeeDto request);
    Employee authenticate(AuthReqDto request);
}
