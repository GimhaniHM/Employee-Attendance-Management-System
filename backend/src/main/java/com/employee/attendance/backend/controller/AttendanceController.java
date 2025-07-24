package com.employee.attendance.backend.controller;

import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.entity.Attendance;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.service.AttendanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    // Employee - Check-in
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceDto> checkIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) authentication.getPrincipal();

        AttendanceDto attendanceDto = attendanceService.checkIn(currentEmployee.getEmpId());
        return new ResponseEntity<>(attendanceDto, HttpStatus.CREATED);
    }

    // Employee - Check-out
    @PutMapping("/check-out")
    public ResponseEntity<AttendanceDto> checkOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) authentication.getPrincipal();

        AttendanceDto updatedAttendanceDto = attendanceService.checkOut(currentEmployee.getEmpId());
        return ResponseEntity.ok(updatedAttendanceDto);
    }

    // Employee or Admin - View own logs
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/my-logs/{id}")
    public ResponseEntity<List<AttendanceDto>> getMyLogs(@PathVariable("id") Long employeeId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) authentication.getPrincipal();

        boolean isAdmin = currentEmployee.getRole().name().equals("ADMIN");

        if (!isAdmin && !currentEmployee.getEmpId().equals(employeeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<AttendanceDto> logs = attendanceService.getAttendanceByEmployeeId(currentEmployee.getEmpId());
        return ResponseEntity.ok(logs);
    }

    // Admin only - View all attendance logs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDto>> getAllLogs() {
        List<AttendanceDto> allLogs = attendanceService.getAllAttendance();
        return ResponseEntity.ok(allLogs);
    }

}
