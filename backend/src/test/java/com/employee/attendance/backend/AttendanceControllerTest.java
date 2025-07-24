package com.employee.attendance.backend;

import com.employee.attendance.backend.controller.AttendanceController;
import com.employee.attendance.backend.model.dto.AttendanceDto;
import com.employee.attendance.backend.model.entity.Employee;
import com.employee.attendance.backend.service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttendanceController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
    private AttendanceService attendanceService;

    // Helper method to set authenticated user in SecurityContext
    private void setAuthentication(Long empId, String role) {
        Employee emp = new Employee();
        emp.setEmpId(empId);
//        emp.setRole(Employee.Role.valueOf(role));
        TestingAuthenticationToken auth = new TestingAuthenticationToken(emp, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void testCheckIn() throws Exception {
        setAuthentication(1L, "EMPLOYEE");

        AttendanceDto dto = new AttendanceDto(1L, 1L, LocalDateTime.now(), null, LocalDate.now());
        when(attendanceService.checkIn(anyLong())).thenReturn(dto);

        mockMvc.perform(post("/api/attendance/check-in"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCheckOut() throws Exception {
        setAuthentication(1L, "EMPLOYEE");

        AttendanceDto dto = new AttendanceDto(1L, 1L, LocalDateTime.now().minusHours(8), LocalDateTime.now(), LocalDate.now());
        when(attendanceService.checkOut(anyLong())).thenReturn(dto);

        mockMvc.perform(post("/api/attendance/check-out"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkOutTime").exists());
    }

    @Test
    void testGetMyLogs_authorized() throws Exception {
        setAuthentication(1L, "EMPLOYEE");

        AttendanceDto dto = new AttendanceDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now());
        when(attendanceService.getAttendanceByEmployeeId(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/attendance/my-logs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetMyLogs_forbidden() throws Exception {
        setAuthentication(2L, "EMPLOYEE"); // Different user

        mockMvc.perform(get("/api/attendance/my-logs/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAllLogs_admin() throws Exception {
        setAuthentication(1L, "ADMIN");

        AttendanceDto dto1 = new AttendanceDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now());
        AttendanceDto dto2 = new AttendanceDto(2L, 2L, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now());
        when(attendanceService.getAllAttendance()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/attendance/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}


