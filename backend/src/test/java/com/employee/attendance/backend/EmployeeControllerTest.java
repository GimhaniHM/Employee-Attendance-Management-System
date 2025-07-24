//package com.employee.attendance.backend;
//
//import com.employee.attendance.backend.model.dto.EmployeeDto;
//import com.employee.attendance.backend.service.EmployeeService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.MediaType;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static java.nio.file.Paths.get;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class EmployeeControllerTest {
//
//    private MockMvc mockMvc;
//
//    private EmployeeService employeeService;
//
//    @Test
//    void getEmployeeById_shouldReturnEmployee() throws Exception {
//        EmployeeDto employeeDto = new EmployeeDto(1L, "John", "john@example.com");
//
//        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto);
//
//        mockMvc.perform(get("/api/employees/1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("john@example.com"));
//    }
//}
