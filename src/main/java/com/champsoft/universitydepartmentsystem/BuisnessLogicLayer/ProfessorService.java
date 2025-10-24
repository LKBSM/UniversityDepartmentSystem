package com.champsoft.universitydepartmentsystem.BuisnessLogicLayer;

import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import com.champsoft.universitydepartmentsystem.DataLayer.DepartmentRepository;
import com.champsoft.universitydepartmentsystem.DataLayer.Professor;
import com.champsoft.universitydepartmentsystem.DataLayer.ProfessorRepository;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorRequestModel;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorResponseModel;
import com.champsoft.universitydepartmentsystem.MapperLayer.ProfessorMapper;
import com.champsoft.universitydepartmentsystem.utilities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// R1, R4: Service layer implementation for Professor business logic, using Lombok style.
@Service
@RequiredArgsConstructor
public class ProfessorService {

    // Dependencies injected via Lombok's @RequiredArgsConstructor
    private final ProfessorRepository professorRepository;
    private final DepartmentRepository departmentRepository; // Needed to link Professor to Department
    private final ProfessorMapper professorMapper;

    // --- CRUD OPERATIONS ---

    /**
     * R6: Retrieves all professors and maps them to a list of Response DTOs.
     */
    public List<ProfessorResponseModel> findAll() {
        return professorRepository.findAll().stream()
                .map(professorMapper::toResponseModel)
                .collect(Collectors.toList());
    }

    /**
     * R6, R11: Retrieves a single professor by ID. Throws NotFoundException if missing.
     */
    public ProfessorResponseModel findById(Long id) {
        return professorRepository.findById(id)
                .map(professorMapper::toResponseModel)
                .orElseThrow(() -> new NotFoundException("Professor", id));
    }

    /**
     * R6: Creates a new professor, requiring a valid Department ID.
     */
    @Transactional
    public ProfessorResponseModel create(ProfessorRequestModel requestModel) {
        // 1. Check if the associated Department exists (Business Logic/Relationship handling)
        Department department = departmentRepository.findById(requestModel.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department", requestModel.getDepartmentId()));

        // 2. Map DTO to Entity
        Professor newProfessor = professorMapper.toEntity(requestModel);

        // 3. Set the relationship (connecting the Professor to the fetched Department)
        newProfessor.setDepartment(department);

        // 4. Save and return mapped Response DTO
        Professor savedProfessor = professorRepository.save(newProfessor);
        return professorMapper.toResponseModel(savedProfessor);
    }

    /**
     * R6, R11: Updates an existing professor. Throws NotFoundException if missing.
     */
    @Transactional
    public ProfessorResponseModel update(Long id, ProfessorRequestModel requestModel) {
        // Find existing professor or throw 404
        Professor professorToUpdate = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor", id));

        // 1. Check if the associated Department exists (in case the FK was changed)
        Department newDepartment = departmentRepository.findById(requestModel.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department", requestModel.getDepartmentId()));

        // 2. Update core fields
        professorToUpdate.setFirstName(requestModel.getFirstName());
        professorToUpdate.setLastName(requestModel.getLastName());
        professorToUpdate.setEmail(requestModel.getEmail());
        professorToUpdate.setTitle(requestModel.getTitle());

        // 3. Update relationship
        professorToUpdate.setDepartment(newDepartment);

        // Save is implicit due to @Transactional
        return professorMapper.toResponseModel(professorToUpdate);
    }

    /**
     * R6, R11: Deletes a professor by ID. Throws NotFoundException if missing.
     */
    public void delete(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new NotFoundException("Professor", id);
        }
        professorRepository.deleteById(id);
    }
}