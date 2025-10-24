package com.champsoft.universitydepartmentsystem.DataLayer;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private String firstName;
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String title; // e.g., Full Professor, Lecturer

    // Many-to-One relationship with Department (R3)
    // EAGER fetch is used for ManyToOne (best practice for a single object)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false) // Defines the foreign key column
    private Department department;

    /**
     * Custom constructor for creating new Professor objects in the application,
     * specifically used for data seeding (like your Car constructor).
     */
    public Professor(String firstName, String lastName, String email, String title,Long id, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.id=id;
        this.department = department;
    }
}



