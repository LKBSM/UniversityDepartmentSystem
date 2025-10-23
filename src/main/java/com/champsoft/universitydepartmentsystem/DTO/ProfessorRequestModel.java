package com.champsoft.universitydepartmentsystem.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequestModel {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a well-formed email address.")
    private String email;

    private String title; // Optional field

    // R2, R3: Foreign key to establish the ManyToOne relationship
    @NotNull(message = "Department ID is required to assign a Professor.")
    @Positive(message = "Department ID must be a positive number.")
    private Long departmentId;
}

