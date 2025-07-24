package com.employee.attendance.backend.service;

import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;

public interface AttendanceService {
    AttendanceDto createAttendance(AttendanceDto attendanceDto);
}
