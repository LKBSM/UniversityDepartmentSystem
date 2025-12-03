package com.champsoft.universitydepartmentsystem.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSummary {
    private Long id;
    private String name;
    private String code; // Including code for quick identification as per UML
}
