package com.champsoft.universitydepartmentsystem.DataLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring Data JPA Repository for the Professor entity.
// R4: Follow Java/Spring naming conventions

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {


}
