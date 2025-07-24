package com.employee.attendance.backend.model.mapper;

import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.entity.Attendance;
import com.employee.attendance.backend.model.entity.Employee;

public class AttendanceMapper {
    public static AttendanceDto mapToAttendanceDto(Attendance attendance) {
        return new AttendanceDto(
                attendance.getId(),
                attendance.getEmployee().getEmpId(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getDate()
        );
    }

    public static Attendance mapToAttendance(AttendanceDto attendanceDto) {
        Employee employee = new Employee();
        employee.setEmpId(attendanceDto.getEmployeeId());

        return new Attendance(
                attendanceDto.getId(),
                employee,
                attendanceDto.getCheckInTime(),
                attendanceDto.getCheckOutTime(),
                attendanceDto.getDate()

        );
    }
}
