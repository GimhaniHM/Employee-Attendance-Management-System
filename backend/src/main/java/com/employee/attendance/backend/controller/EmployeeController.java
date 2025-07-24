package com.employee.attendance.backend.controller;

import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    // Get employee REST API by id
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Employee currentEmployee = (Employee) authentication.getPrincipal();

        boolean isAdmin = currentEmployee.getRole().name().equals("ADMIN");

        if (!isAdmin && !currentEmployee.getEmpId().equals(employeeId)) {
            // If not admin and trying to access others' data, forbid
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    // Get all employees
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDtos);
    }

    // Update the Employee by id
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody EmployeeDto updatedEmployee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) authentication.getPrincipal();

        boolean isAdmin = currentEmployee.getRole().name().equals("ADMIN");

        if (!isAdmin && !currentEmployee.getEmpId().equals(employeeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }

    // Delete the employee by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee has been deleted", HttpStatus.OK);

    }

}
