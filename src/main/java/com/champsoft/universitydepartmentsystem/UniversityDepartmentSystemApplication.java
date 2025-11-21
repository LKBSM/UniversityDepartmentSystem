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
     * R13: Seeds at least 10 parent records (Departments) and 50+ child records (Professors) using CommandLineRunner.
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

            // 2. Seed Professor data (52 records - exceeds 50 requirement)
            // Computer Science Department (8 professors)
            Professor prof1 = new Professor("Alice", "Smith", "a.smith@uni.ca", "Full Professor", deptCS);
            Professor prof2 = new Professor("Bob", "Johnson", "b.johnson@uni.ca", "Assistant Professor", deptCS);
            Professor prof3 = new Professor("Carol", "Davis", "c.davis@uni.ca", "Lecturer", deptCS);
            Professor prof4 = new Professor("Michael", "Chen", "m.chen@uni.ca", "Associate Professor", deptCS);
            Professor prof5 = new Professor("Sarah", "Williams", "s.williams@uni.ca", "Full Professor", deptCS);
            Professor prof6 = new Professor("James", "Martinez", "j.martinez@uni.ca", "Assistant Professor", deptCS);
            Professor prof7 = new Professor("Emily", "Garcia", "e.garcia@uni.ca", "Lecturer", deptCS);
            Professor prof8 = new Professor("Daniel", "Rodriguez", "d.rodriguez@uni.ca", "Associate Professor", deptCS);

            // History and Politics Department (6 professors)
            Professor prof9 = new Professor("David", "Brown", "d.brown@uni.ca", "Full Professor", deptHistory);
            Professor prof10 = new Professor("Eve", "Miller", "e.miller@uni.ca", "Associate Professor", deptHistory);
            Professor prof11 = new Professor("Robert", "Wilson", "r.wilson@uni.ca", "Assistant Professor", deptHistory);
            Professor prof12 = new Professor("Jennifer", "Moore", "j.moore@uni.ca", "Full Professor", deptHistory);
            Professor prof13 = new Professor("Thomas", "Taylor", "t.taylor@uni.ca", "Lecturer", deptHistory);
            Professor prof14 = new Professor("Patricia", "Anderson", "p.anderson@uni.ca", "Associate Professor", deptHistory);

            // Physics and Mathematics Department (7 professors)
            Professor prof15 = new Professor("Frank", "Wilson", "f.wilson@uni.ca", "Assistant Professor", deptPhysics);
            Professor prof16 = new Professor("Grace", "Moore", "g.moore@uni.ca", "Lecturer", deptPhysics);
            Professor prof17 = new Professor("Richard", "Thomas", "r.thomas@uni.ca", "Full Professor", deptPhysics);
            Professor prof18 = new Professor("Linda", "Jackson", "l.jackson@uni.ca", "Associate Professor", deptPhysics);
            Professor prof19 = new Professor("Charles", "White", "c.white@uni.ca", "Assistant Professor", deptPhysics);
            Professor prof20 = new Professor("Barbara", "Harris", "b.harris@uni.ca", "Full Professor", deptPhysics);
            Professor prof21 = new Professor("Joseph", "Martin", "j.martin@uni.ca", "Lecturer", deptPhysics);

            // Biology Department (6 professors)
            Professor prof22 = new Professor("Henry", "Taylor", "h.taylor@uni.ca", "Full Professor", deptBiology);
            Professor prof23 = new Professor("Susan", "Lee", "s.lee@uni.ca", "Associate Professor", deptBiology);
            Professor prof24 = new Professor("Christopher", "Walker", "c.walker@uni.ca", "Assistant Professor", deptBiology);
            Professor prof25 = new Professor("Jessica", "Hall", "j.hall@uni.ca", "Full Professor", deptBiology);
            Professor prof26 = new Professor("Matthew", "Allen", "m.allen@uni.ca", "Lecturer", deptBiology);
            Professor prof27 = new Professor("Ashley", "Young", "a.young@uni.ca", "Associate Professor", deptBiology);

            // Chemistry Department (5 professors)
            Professor prof28 = new Professor("Ivy", "Anderson", "i.anderson@uni.ca", "Assistant Professor", deptChemistry);
            Professor prof29 = new Professor("Andrew", "King", "a.king@uni.ca", "Full Professor", deptChemistry);
            Professor prof30 = new Professor("Michelle", "Wright", "m.wright@uni.ca", "Associate Professor", deptChemistry);
            Professor prof31 = new Professor("Joshua", "Lopez", "j.lopez@uni.ca", "Lecturer", deptChemistry);
            Professor prof32 = new Professor("Amanda", "Hill", "a.hill@uni.ca", "Assistant Professor", deptChemistry);

            // English Literature Department (5 professors)
            Professor prof33 = new Professor("Jack", "Thomas", "j.thomas@uni.ca", "Associate Professor", deptEnglish);
            Professor prof34 = new Professor("Melissa", "Scott", "m.scott@uni.ca", "Full Professor", deptEnglish);
            Professor prof35 = new Professor("Kevin", "Green", "k.green@uni.ca", "Assistant Professor", deptEnglish);
            Professor prof36 = new Professor("Laura", "Adams", "l.adams@uni.ca", "Lecturer", deptEnglish);
            Professor prof37 = new Professor("Brian", "Baker", "b.baker@uni.ca", "Associate Professor", deptEnglish);

            // Fine Arts Department (5 professors)
            Professor prof38 = new Professor("Kate", "Jackson", "k.jackson@uni.ca", "Lecturer", deptArt);
            Professor prof39 = new Professor("Steven", "Nelson", "s.nelson@uni.ca", "Full Professor", deptArt);
            Professor prof40 = new Professor("Nicole", "Carter", "n.carter@uni.ca", "Associate Professor", deptArt);
            Professor prof41 = new Professor("Ryan", "Mitchell", "r.mitchell@uni.ca", "Assistant Professor", deptArt);
            Professor prof42 = new Professor("Heather", "Perez", "h.perez@uni.ca", "Lecturer", deptArt);

            // Economics Department (5 professors)
            Professor prof43 = new Professor("Leo", "White", "l.white@uni.ca", "Full Professor", deptEconomics);
            Professor prof44 = new Professor("Kimberly", "Roberts", "k.roberts@uni.ca", "Associate Professor", deptEconomics);
            Professor prof45 = new Professor("Jonathan", "Turner", "j.turner@uni.ca", "Assistant Professor", deptEconomics);
            Professor prof46 = new Professor("Rebecca", "Phillips", "r.phillips@uni.ca", "Full Professor", deptEconomics);
            Professor prof47 = new Professor("Eric", "Campbell", "e.campbell@uni.ca", "Lecturer", deptEconomics);

            // Law and Justice Department (3 professors)
            Professor prof48 = new Professor("Angela", "Parker", "a.parker@uni.ca", "Full Professor", deptLaw);
            Professor prof49 = new Professor("Timothy", "Evans", "t.evans@uni.ca", "Associate Professor", deptLaw);
            Professor prof50 = new Professor("Rachel", "Edwards", "r.edwards@uni.ca", "Assistant Professor", deptLaw);

            // Philosophy Department (2 professors)
            Professor prof51 = new Professor("Gregory", "Collins", "g.collins@uni.ca", "Full Professor", deptPhilosophy);
            Professor prof52 = new Professor("Stephanie", "Stewart", "s.stewart@uni.ca", "Associate Professor", deptPhilosophy);

            professorRepository.saveAll(Arrays.asList(
                    prof1, prof2, prof3, prof4, prof5, prof6, prof7, prof8, prof9, prof10,
                    prof11, prof12, prof13, prof14, prof15, prof16, prof17, prof18, prof19, prof20,
                    prof21, prof22, prof23, prof24, prof25, prof26, prof27, prof28, prof29, prof30,
                    prof31, prof32, prof33, prof34, prof35, prof36, prof37, prof38, prof39, prof40,
                    prof41, prof42, prof43, prof44, prof45, prof46, prof47, prof48, prof49, prof50,
                    prof51, prof52
            ));

            logger.info("Data seeding complete. {} departments and {} professors saved.",
                    departmentRepository.count(), professorRepository.count());
        };
    }
}