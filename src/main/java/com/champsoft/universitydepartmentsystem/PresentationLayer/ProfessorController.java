package com.champsoft.universitydepartmentsystem.PresentationLayer;

import com.champsoft.universitydepartmentsystem.BuisnessLogicLayer.ProfessorService;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorRequestModel;
import com.champsoft.universitydepartmentsystem.DTO.ProfessorResponseModel;
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
@RequestMapping("/api/professors") // Base path: /api/professors
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    // R6, R10: GET all -> 200 OK
    @GetMapping
    public List<ProfessorResponseModel> getAll() {
        return professorService.findAll();
    }

    // R6, R10: GET one by id -> 200 OK (or 404 Not Found handled by @ControllerAdvice)
    @GetMapping("/{id}")
    public ProfessorResponseModel getById(@PathVariable Long id) {
        return professorService.findById(id);
    }

    // R6, R10: POST -> 201 Created with Location header. R57: Validation errors -> 400 Bad Request.
    @PostMapping
    public ResponseEntity<ProfessorResponseModel> create(@Valid @RequestBody ProfessorRequestModel req) {
        // @Valid triggers DTO validation, including checking @NotNull/@Positive for departmentId.
        var created = professorService.create(req);

        // R10: 201 Created with Location header pointing to the new resource.
        return ResponseEntity
                .created(URI.create("/api/professors/" + created.getId()))
                .body(created);
    }

    // R6, R10: PUT -> 200 OK (or 404/400)
    @PutMapping("/{id}")
    public ProfessorResponseModel update(@PathVariable Long id, @Valid @RequestBody ProfessorRequestModel req) {
        // PUT should be idempotent (R98)
        return professorService.update(id, req);
    }

    // R6, R10: DELETE -> 204 No Content (or 404)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // R10: 204 No Content on successful DELETE.
    public void delete(@PathVariable Long id) {
        professorService.delete(id);
    }

    // NOTE: Aggregated endpoint GET /api/professors/{id}/department was removed per user request.
}
