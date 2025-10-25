package com.champsoft.universitydepartmentsystem;

import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import com.champsoft.universitydepartmentsystem.DataLayer.DepartmentRepository;
import com.champsoft.universitydepartmentsystem.DataLayer.Professor;
import com.champsoft.universitydepartmentsystem.DataLayer.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

// R116: Use a CommandLineRunner bean to insert data on startup.
@SpringBootApplication
public class UniversityDepartmentSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(UniversityDepartmentSystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UniversityDepartmentSystemApplication.class, args);
        logger.info("University Department System Application started.");
    }

    /**
     * R13: Seeds at least 10 records in each table using CommandLineRunner.
     */
    @Bean
    CommandLineRunner runner(DepartmentRepository departmentRepository, ProfessorRepository professorRepository) {
        return args -> {
            logger.info("Starting data seeding...");

            // 1. Seed Department data (10 records)
            Department deptCS = new Department("Computer Science", "CS", 1995);
            Department deptHistory = new Department("History and Politics", "HIST", 1980);
            Department deptPhysics = new Department("Physics and Mathematics", "PHYS", 2000);
            Department deptBiology = new Department("Biology", "BIO", 1975);
            Department deptChemistry = new Department("Chemistry", "CHEM", 1990);
            Department deptEnglish = new Department("English Literature", "ENG", 1965);
            Department deptArt = new Department("Fine Arts", "ART", 2005);
            Department deptEconomics = new Department("Economics", "ECON", 1985);
            Department deptLaw = new Department("Law and Justice", "LAW", 2010);
            Department deptPhilosophy = new Department("Philosophy", "PHIL", 1970);
            departmentRepository.saveAll(Arrays.asList(deptCS, deptHistory, deptPhysics, deptBiology, deptChemistry, deptEnglish, deptArt, deptEconomics, deptLaw, deptPhilosophy));

            // 2. Seed Professor data (12 records)
            Professor prof1 = new Professor("Alice", "Smith", "a.smith@uni.ca", "Full Professor", deptCS);
            Professor prof2 = new Professor("Bob", "Johnson", "b.johnson@uni.ca", "Assistant Professor", deptCS);
            Professor prof3 = new Professor("Carol", "Davis", "c.davis@uni.ca", "Lecturer", deptCS);
            Professor prof4 = new Professor("David", "Brown", "d.brown@uni.ca", "Full Professor", deptHistory);
            Professor prof5 = new Professor("Eve", "Miller", "e.miller@uni.ca", "Associate Professor", deptHistory);
            Professor prof6 = new Professor("Frank", "Wilson", "f.wilson@uni.ca", "Assistant Professor", deptPhysics);
            Professor prof7 = new Professor("Grace", "Moore", "g.moore@uni.ca", "Lecturer", deptPhysics);
            Professor prof8 = new Professor("Henry", "Taylor", "h.taylor@uni.ca", "Full Professor", deptBiology);
            Professor prof9 = new Professor("Ivy", "Anderson", "i.anderson@uni.ca", "Assistant Professor", deptChemistry);
            Professor prof10 = new Professor("Jack", "Thomas", "j.thomas@uni.ca", "Associate Professor", deptEnglish);
            Professor prof11 = new Professor("Kate", "Jackson", "k.jackson@uni.ca", "Lecturer", deptArt);
            Professor prof12 = new Professor("Leo", "White", "l.white@uni.ca", "Full Professor", deptEconomics);

            professorRepository.saveAll(Arrays.asList(
                    prof1, prof2, prof3, prof4, prof5, prof6,
                    prof7, prof8, prof9, prof10, prof11, prof12
            ));

            logger.info("Data seeding complete. {} departments and {} professors saved.",
                    departmentRepository.count(), professorRepository.count());


        };
    }
}