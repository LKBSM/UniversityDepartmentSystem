package com.champsoft.universitydepartmentsystem.PresentationLayer;

import com.champsoft.universitydepartmentsystem.BuisnessLogicLayer.DepartmentService;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentRequestModel;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentResponseModel;
import com.champsoft.universitydepartmentsystem.DTO.DepartmentWithProfessorsResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// R1: 3-Layer architecture: Controller layer
// R4: Follow Java/Spring naming conventions (suffix with Controller)
// R52: Follow REST naming conventions, plural nouns, and resource-oriented paths.

@RestController
@RequestMapping("/api/departments") // Base path: /api/departments (Example Endpoint Design)
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // R6, R10: GET all -> 200 OK
    @GetMapping
    public List<DepartmentResponseModel> getAll() {
        return departmentService.getAllDepartments();
    }

    // R6, R10: GET one by id -> 200 OK (or 404 Not Found handled by @ControllerAdvice)
    @GetMapping("/{id}")
    public DepartmentResponseModel getById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    // R6, R10: POST -> 201 Created with Location header. R57: Validation errors -> 400 Bad Request.
    @PostMapping
    public ResponseEntity<DepartmentResponseModel> create(@Valid @RequestBody DepartmentRequestModel req) {
        // @Valid triggers DTO validation before service call.
        var created = departmentService.create(req);

        // R10: 201 Created with Location header pointing to the new resource.
        return ResponseEntity
                .created(URI.create("/api/departments/" + created.getId()))
                .body(created);
    }

    // R6, R10: PUT -> 200 OK (or 404/400)
    @PutMapping("/{id}")
    public DepartmentResponseModel update(@PathVariable Long id, @Valid @RequestBody DepartmentRequestModel req) {
        // PUT should be idempotent (R98)
        return departmentService.update(id, req);
    }

    // R6, R10: DELETE -> 204 No Content (or 404)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // R10: 204 No Content on successful DELETE.
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }

    // R54, R6, R7: Aggregated Endpoint: GET /api/departments/{id}/professors -> 200 OK
    @GetMapping("/{id}/professors")
    public DepartmentWithProfessorsResponseDTO getProfessorsByDepartment(@PathVariable Long id) {
        return departmentService.getDepartmentWithProfessors(id);
    }
}
