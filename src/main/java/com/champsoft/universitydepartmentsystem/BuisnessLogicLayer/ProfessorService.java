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

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final DepartmentRepository departmentRepository;
    private final ProfessorMapper professorMapper;

    // Get all professors
    public List<ProfessorResponseModel> findAll() {
        return professorRepository.findAll().stream()
                .map(professorMapper::toResponseModel)
                .collect(Collectors.toList());
    }

    // Get professor by ID
    public ProfessorResponseModel findById(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor", id));
        return professorMapper.toResponseModel(professor);
    }

    // Create a new professor
    @Transactional
    public ProfessorResponseModel create(ProfessorRequestModel requestModel) {
        // 1️⃣ Fetch the Department entity using the ID from the request model
        Department department = departmentRepository.findById(requestModel.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department", requestModel.getDepartmentId()));

        // 2️⃣ Convert DTO → Entity using the updated mapper
        Professor professor = professorMapper.toEntity(requestModel, department);

        // 3️⃣ Save the professor and return a response DTO
        Professor savedProfessor = professorRepository.save(professor);
        return professorMapper.toResponseModel(savedProfessor);
    }

    // Update an existing professor
    @Transactional
    public ProfessorResponseModel update(Long id, ProfessorRequestModel requestModel) {
        // 1️⃣ Check if professor exists
        Professor existingProfessor = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor", id));

        // 2️⃣ Fetch the Department entity
        Department department = departmentRepository.findById(requestModel.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department", requestModel.getDepartmentId()));

        // 3️⃣ Update the professor fields
        existingProfessor.setFirstName(requestModel.getFirstName());
        existingProfessor.setLastName(requestModel.getLastName());
        existingProfessor.setEmail(requestModel.getEmail());
        existingProfessor.setTitle(requestModel.getTitle());
        existingProfessor.setDepartment(department);

        // 4️⃣ Save and return
        Professor updatedProfessor = professorRepository.save(existingProfessor);
        return professorMapper.toResponseModel(updatedProfessor);
    }

    // Delete a professor
    @Transactional
    public void delete(Long id) {
        // Check if professor exists before deleting
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professor", id));
        professorRepository.delete(professor);
    }
}
