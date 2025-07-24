package com.employee.attendance.backend.config;

import com.employee.attendance.backend.exception.ResourceNotFoundException;
import com.employee.attendance.backend.model.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
public class ApplicationConfiguration {
    private final EmployeeRepository employeeRepository;

//    public ApplicationConfiguration(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> employeeRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + username));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
