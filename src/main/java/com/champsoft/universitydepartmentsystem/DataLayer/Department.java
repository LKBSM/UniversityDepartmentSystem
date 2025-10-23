package com.champsoft.universitydepartmentsystem.DataLayer;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Default constructor for JPA
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fields from UML and Validation
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code; // e.g., CS, HIST

    private Integer yearEstablished;

    // One-to-Many relationship with Professor (R3)
    // FetchType.LAZY is used for collections (best practice)
    // CascadeType.PERSIST ensures if we save a new Department with new Professors, they are saved too.
    // Mapped by the 'department' field in the Professor entity.
    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Professor> professors = new HashSet<>();

    /**
     * Custom constructor for creating new Department objects in the application,
     * specifically used for data seeding (like your Car constructor).
     */
    public Department(String name, String code, Integer yearEstablished) {
        this.name = name;
        this.code = code;
        this.yearEstablished = yearEstablished;
        this.professors = new HashSet<>(); // Initialize collection
    }
}

