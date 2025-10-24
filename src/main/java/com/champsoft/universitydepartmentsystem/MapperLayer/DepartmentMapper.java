package com.champsoft.universitydepartmentsystem.MapperLayer;

import com.champsoft.universitydepartmentsystem.DTO.DepartmentRequestModel;
import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentResponseModel;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentWithProfessorsResponseDTO;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorSummary;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

// R4, R8: Mapper component for converting Department entities to DTOs.
@Component
public class DepartmentMapper {

    private final ProfessorMapper professorMapper;

    // Inject the ProfessorMapper to handle nested ProfessorSummary mapping
    public DepartmentMapper(ProfessorMapper professorMapper) {
        this.professorMapper = professorMapper;
    }

    /**
     * Converts a Department Entity to the basic DepartmentResponseModel (used for GET all/one).
     * Calculates the professorCount for the DTO.
     */
    public DepartmentResponseModel toResponseModel(Department department) {

        int professorCount = department.getProfessors() != null ? department.getProfessors().size() : 0;

        return new DepartmentResponseModel(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getYearEstablished(),
                professorCount // Include calculated count
        );
    }

    /**
     * Converts a Department Entity to the Aggregated DepartmentWithProfessorsResponseDTO.
     * This includes a nested list of ProfessorSummary DTOs. (R7)
     */
    public DepartmentWithProfessorsResponseDTO toDepartmentWithProfessorsResponseDTO(Department department) {
        var professorSummaries = department.getProfessors().stream()
                .map(professor -> new ProfessorSummary(
                        professor.getId(),
                        professor.getFirstName(),
                        professor.getLastName(),
                        professor.getTitle() // Include title in ProfessorSummary as per design for clarity
                ))
                .collect(Collectors.toList());

        return new DepartmentWithProfessorsResponseDTO(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getYearEstablished(),
                professorSummaries // Include the list of summaries
        );
    }

    public Department toEntity(DepartmentRequestModel requestModel) {
        // Use the custom entity constructor to set core fields.
        // ID is left null (server-generated), and the professors collection is initialized as an empty HashSet.
        return new Department(
                requestModel.getName(),
                requestModel.getCode(),
                requestModel.getYearEstablished()
                // The Department entity's custom constructor handles the initialization of the 'professors' Set.
        );
    }
}
