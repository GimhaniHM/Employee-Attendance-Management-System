package com.employee.attendance.backend.service.impl;


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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final EmployeeRepository employeeRepository;
    private AttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public AttendanceDto createAttendance(AttendanceDto attendanceDto) {

        Employee employee = employeeRepository.findById(attendanceDto.getEmployeeId())
            .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCheckInTime(attendanceDto.getCheckInTime());
        attendance.setCheckOutTime(attendanceDto.getCheckOutTime());
        attendance.setDate(attendanceDto.getDate());

        Attendance saved = attendanceRepository.save(attendance);
        return AttendanceMapper.mapToAttendanceDto(saved);
    }
}
