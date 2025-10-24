package com.champsoft.universitydepartmentsystem.MapperLayer;

import com.champsoft.universitydepartmentsystem.DTO.ProfessorRequestModel;
import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import com.champsoft.universitydepartmentsystem.DataLayer.Professor;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentSummary;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorResponseModel;
import org.springframework.stereotype.Component;

// R4, R8: Mapper component for converting Professor entities to DTOs.
@Component
public class ProfessorMapper {

    /**
     * Converts a Professor Entity to the basic ProfessorResponseModel (used for GET all/one).
     */
    public ProfessorResponseModel ResponseModel(Professor professor) {
        return new ProfessorResponseModel(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getTitle()
        );
    }

    public ProfessorResponseModel toResponseModel(Professor professor) {
        return new ProfessorResponseModel(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getTitle()
        );
    }

    public Professor toEntity(ProfessorRequestModel requestModel) {

        return new Professor(
                requestModel.getFirstName(),
                requestModel.getLastName(),
                requestModel.getEmail(),
                requestModel.getTitle(),
                requestModel.getDepartmentId(),
                null
        );
    }
}