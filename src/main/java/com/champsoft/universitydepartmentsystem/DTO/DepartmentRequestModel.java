package com.champsoft.universitydepartmentsystem.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotNull(message = "Year established is required.")
    // Validation constraint check: The year must be in the past or present.
    @PastOrPresent(message = "Year established cannot be in the future.")
    private Integer yearEstablished;
}
