package com.employee.attendance.backend;

import com.employee.attendance.backend.exception.ResourceNotFoundException;
import com.employee.attendance.backend.model.dto.EmployeeDto;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.model.repository.EmployeeRepository;
import com.employee.attendance.backend.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        employee = new Employee(1L, "John", "john@example.com", "password", "ROLE_EMPLOYEE");
//        employeeDto = new EmployeeDto(1L, "John", "john@example.com");
//    }

    @Test
    void getEmployeeById_shouldReturnEmployeeDto() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        EmployeeDto result = employeeService.getEmployeeById(1L);
        assertEquals(employee.getEmail(), result.getEmail());
    }

    @Test
    void getEmployeeById_shouldThrowExceptionIfNotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(2L));
    }

    @Test
    void deleteEmployee_shouldDeleteSuccessfully() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
