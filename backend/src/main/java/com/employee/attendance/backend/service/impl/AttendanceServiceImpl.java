package com.employee.attendance.backend.service.impl;


import com.employee.attendance.backend.exception.ResourceNotFoundException;
import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.entity.Attendance;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.model.mapper.AttendanceMapper;
import com.employee.attendance.backend.model.repository.AttendanceRepository;
import com.employee.attendance.backend.model.repository.EmployeeRepository;
import com.employee.attendance.backend.service.AttendanceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public AttendanceDto checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Check if already checked-in today
        Optional<Attendance> existing = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now());
        if (existing.isPresent()) {
            throw new IllegalStateException("Already checked in today.");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setCheckOutTime(null);

        Attendance saved = attendanceRepository.save(attendance);
        return AttendanceMapper.mapToAttendanceDto(saved);
    }

    @Override
    public AttendanceDto checkOut(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException("No check-in found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new IllegalStateException("Already checked out today.");
        }

        attendance.setCheckOutTime(LocalDateTime.now());

        Attendance updated = attendanceRepository.save(attendance);

        return AttendanceMapper.mapToAttendanceDto(updated);
    }

    @Override
    public List<AttendanceDto> getAllAttendance() {
        List<Attendance> all = attendanceRepository.findAll();
        return all.stream()
                .map(AttendanceMapper::mapToAttendanceDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDto> getAttendanceByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        List<Attendance> attendances = attendanceRepository.findByEmployee(employee);
        return attendances.stream()
                .map(AttendanceMapper::mapToAttendanceDto)
                .collect(Collectors.toList());
    }


}
