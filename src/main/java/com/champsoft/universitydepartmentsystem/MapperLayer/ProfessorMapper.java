package com.champsoft.universitydepartmentsystem.MapperLayer;

import com.champsoft.universitydepartmentsystem.DTO.DepartmentSummary;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorRequestModel;
import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import com.champsoft.universitydepartmentsystem.DataLayer.Professor;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorResponseModel;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    // Converts Entity -> Response DTO
    public ProfessorResponseModel toResponseModel(Professor professor) {
        DepartmentSummary departmentSummary = null;
        Long departmentId = null;

        if (professor.getDepartment() != null) {
            Department dept = professor.getDepartment();
            departmentId = dept.getId();
            departmentSummary = new DepartmentSummary(
                    dept.getId(),
                    dept.getName(),
                    dept.getCode()
            );
        }

        return new ProfessorResponseModel(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getTitle(),
                departmentId,
                departmentSummary
        );
    }

    // Converts Request DTO -> Entity
    public Professor toEntity(ProfessorRequestModel requestModel, Department department) {
        return new Professor(
                requestModel.getFirstName(),
                requestModel.getLastName(),
                requestModel.getEmail(),
                requestModel.getTitle(),
                department // ✅ use actual Department object, not an ID
        );
    }
}
