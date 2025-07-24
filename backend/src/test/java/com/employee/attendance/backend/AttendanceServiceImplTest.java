package com.employee.attendance.backend;

import com.employee.attendance.backend.exception.ResourceNotFoundException;
import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.entity.Attendance;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.model.mapper.AttendanceMapper;
import com.employee.attendance.backend.model.repository.AttendanceRepository;
import com.employee.attendance.backend.model.repository.EmployeeRepository;
import com.employee.attendance.backend.service.impl.AttendanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceImplTest {

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setEmpId(1L);
//        employee.setRole(Employee.Role.EMPLOYEE);
    }

    @Test
    void checkIn_success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())).thenReturn(Optional.empty());

        Attendance savedAttendance = new Attendance();
        savedAttendance.setId(10L);
        savedAttendance.setEmployee(employee);
        savedAttendance.setDate(LocalDate.now());
        savedAttendance.setCheckInTime(LocalDateTime.now());
        savedAttendance.setCheckOutTime(null);

        when(attendanceRepository.save(any(Attendance.class))).thenReturn(savedAttendance);

        AttendanceDto result = attendanceService.checkIn(1L);
        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void checkIn_alreadyCheckedIn_throwsIllegalStateException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now()))
                .thenReturn(Optional.of(new Attendance()));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            attendanceService.checkIn(1L);
        });
        assertEquals("Already checked in today.", exception.getMessage());
    }

    @Test
    void checkOut_success() {
        Attendance existingAttendance = new Attendance();
        existingAttendance.setId(20L);
        existingAttendance.setEmployee(employee);
        existingAttendance.setDate(LocalDate.now());
        existingAttendance.setCheckInTime(LocalDateTime.now().minusHours(8));
        existingAttendance.setCheckOutTime(null);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now()))
                .thenReturn(Optional.of(existingAttendance));
        when(attendanceRepository.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AttendanceDto result = attendanceService.checkOut(1L);
        assertNotNull(result);
        assertEquals(20L, result.getId());
        assertNotNull(result.getCheckOutTime());
    }

    @Test
    void checkOut_noCheckIn_throwsResourceNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            attendanceService.checkOut(1L);
        });
        assertEquals("No check-in found for today", ex.getMessage());
    }

    @Test
    void checkOut_alreadyCheckedOut_throwsIllegalStateException() {
        Attendance attendance = new Attendance();
        attendance.setCheckOutTime(LocalDateTime.now());

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())).thenReturn(Optional.of(attendance));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            attendanceService.checkOut(1L);
        });
        assertEquals("Already checked out today.", ex.getMessage());
    }

    @Test
    void getAttendanceByEmployeeId_success() {
        List<Attendance> attendances = List.of(new Attendance(), new Attendance());
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployee(employee)).thenReturn(attendances);

        List<AttendanceDto> results = attendanceService.getAttendanceByEmployeeId(1L);
        assertEquals(2, results.size());
    }

    @Test
    void getAllAttendance_success() {
        List<Attendance> allAttendances = List.of(new Attendance(), new Attendance(), new Attendance());
        when(attendanceRepository.findAll()).thenReturn(allAttendances);

        List<AttendanceDto> results = attendanceService.getAllAttendance();
        assertEquals(3, results.size());
    }
}
