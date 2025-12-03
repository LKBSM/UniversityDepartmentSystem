package com.champsoft.universitydepartmentsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponseModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private Long departmentId; // Added for editing functionality
    private DepartmentSummary department; // Added for display purposes
}

