package com.champsoft.universitydepartmentsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWithProfessorsResponseDTO {

    private Long id;
    private String name;
    private String code;
    private Integer yearEstablished;

    private List<ProfessorSummary> professors;
}
