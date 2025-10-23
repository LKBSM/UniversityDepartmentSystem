package com.champsoft.universitydepartmentsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DepartmentResponseModel DTO (R6, R8)
// Purpose: Used for output on GET (one and all) and successful POST/PUT operations.
// Includes server-generated ID and calculated fields.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseModel {
    private Long id;
    private String name;
    private String code;
    private Integer yearEstablished;
    private Integer professorCount; // Calculated field to show number of associated professors
}