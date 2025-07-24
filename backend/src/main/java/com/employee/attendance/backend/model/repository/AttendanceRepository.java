package com.employee.attendance.backend.model.repository;

import com.employee.attendance.backend.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
