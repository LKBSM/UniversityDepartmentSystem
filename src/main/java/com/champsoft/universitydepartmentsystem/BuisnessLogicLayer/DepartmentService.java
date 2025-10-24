package com.champsoft.universitydepartmentsystem.BuisnessLogicLayer;

import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import com.champsoft.universitydepartmentsystem.DataLayer.DepartmentRepository;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentRequestModel;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentResponseModel;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentWithProfessorsResponseDTO;
import com.champsoft.universitydepartmentsystem.MapperLayer.DepartmentMapper;
import com.champsoft.universitydepartmentsystem.utilities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// R1, R4: Service layer implementation for Department business logic, using Lombok style.
@Service
@RequiredArgsConstructor
public class DepartmentService {

    // Dependencies injected via Lombok's @RequiredArgsConstructor
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    // --- CRUD OPERATIONS ---

    /**
     * R6: Retrieves all departments and maps them to a list of Response DTOs.
     */
    public List<DepartmentResponseModel> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponseModel)
                .collect(Collectors.toList());
    }

    /**
     * R6, R11: Retrieves a single department by ID. Throws NotFoundException if missing.
     */
    public DepartmentResponseModel findById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toResponseModel)
                .orElseThrow(() -> new NotFoundException("Department", id));
    }

    /**
     * R6: Creates a new department from a Request DTO.
     */
    public DepartmentResponseModel create(DepartmentRequestModel requestModel) {
        // Map DTO to Entity (excluding ID/collection)
        Department department = departmentMapper.toEntity(requestModel);

        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toResponseModel(savedDepartment);
    }

    /**
     * R6, R11: Updates an existing department. Throws NotFoundException if missing.
     */
    @Transactional
    public DepartmentResponseModel update(Long id, DepartmentRequestModel requestModel) {
        // Find existing department or throw 404
        Department departmentToUpdate = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department", id));

        // Update fields based on request DTO
        departmentToUpdate.setName(requestModel.getName());
        departmentToUpdate.setCode(requestModel.getCode());
        departmentToUpdate.setYearEstablished(requestModel.getYearEstablished());

        // Save is implicit due to @Transactional if using the same persistent instance
        return departmentMapper.toResponseModel(departmentToUpdate);
    }

    /**
     * R6, R11: Deletes a department by ID. Throws NotFoundException if missing.
     */
    public void delete(Long id) {
        // Using existsById for deletion is common, but findById and then delete gives better logging/error info
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department", id);
        }
        departmentRepository.deleteById(id);
    }

    // --- AGGREGATED ENDPOINT LOGIC ---

    /**
     * R6, R7, R11: Retrieves a department and its associated professors.
     * Uses the aggregated DTO (DepartmentWithProfessorsResponseDTO).
     */
    @Transactional(readOnly = true)
    public DepartmentWithProfessorsResponseDTO getDepartmentWithProfessors(Long id) {
        // Fetch the department. Since we are in a transaction, we can access the lazy collection.
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department", id));
        return departmentMapper.toDepartmentWithProfessorsResponseDTO(department);
    }


}

