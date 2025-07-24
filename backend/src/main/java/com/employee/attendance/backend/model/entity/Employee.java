package com.employee.attendance.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.employee.attendance.backend.model.enums.Role;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First Name is required")
    private String firstName;

    @Column(name = "last_name",  nullable = false)
    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Column(nullable = false,  unique = true)
    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
//            message = "Password must contain at least one alphabetical character, one digit, one special character, and be at least 8 characters long.")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Role is required")
    private Role role;

}
