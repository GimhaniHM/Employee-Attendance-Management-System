package com.employee.attendance.backend.model.repository;

import com.employee.attendance.backend.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
