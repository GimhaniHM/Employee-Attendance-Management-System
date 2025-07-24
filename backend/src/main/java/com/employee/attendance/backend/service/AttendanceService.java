package com.employee.attendance.backend.service;

import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.entity.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    AttendanceDto checkIn(Long employeeId);
    AttendanceDto checkOut(Long employeeId);
    List<AttendanceDto> getAllAttendance();
    List<AttendanceDto> getAttendanceByEmployeeId(Long employeeId);
}
