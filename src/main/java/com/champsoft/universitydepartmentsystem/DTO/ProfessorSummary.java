package com.champsoft.universitydepartmentsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ProfessorSummary DTO (R7)
// Purpose: Used for concise, nested representation within Department-related aggregated DTOs (DepartmentWithProfessorsResponseDTO).
// Includes only essential identifying fields to keep the aggregated list concise.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorSummary {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
}

