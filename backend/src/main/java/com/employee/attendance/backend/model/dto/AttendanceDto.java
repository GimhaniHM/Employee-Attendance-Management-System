package com.employee.attendance.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private Long id;
    private Long employeeId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDate date;
}
