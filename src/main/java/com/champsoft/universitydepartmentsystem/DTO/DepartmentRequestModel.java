package com.champsoft.universitydepartmentsystem.DTO;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

// DepartmentRequestModel DTO (R6, R8)
// Purpose: Used for input on POST (create) and PUT (update) operations.
// Excludes server-generated fields (like id) and includes validation annotations.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestModel {

    // R7: Validation rules (e.g., @NotBlank name)
    @NotBlank(message = "Department name is required.")
    private String name;

    @NotBlank(message = "Department code is required.")
    private String code;

    // Validation constraint check: The year must be between 1800 and current year.
    @Min(value = 1800, message = "Year established must be 1800 or later.")
    @Max(value = 2100, message = "Year established cannot be in the future.")
    private Integer yearEstablished;
}
