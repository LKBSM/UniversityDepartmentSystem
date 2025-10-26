# Complete Code Study Guide - University Department System

**A Detailed Breakdown of Every Component, Keyword, and Concept**

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Architecture Deep Dive](#2-architecture-deep-dive)
3. [Packages and Their Purpose](#3-packages-and-their-purpose)
4. [Data Layer - Entities](#4-data-layer---entities)
5. [Data Layer - Repositories](#5-data-layer---repositories)
6. [DTO Layer - Data Transfer Objects](#6-dto-layer---data-transfer-objects)
7. [Mapper Layer](#7-mapper-layer)
8. [Business Logic Layer - Services](#8-business-logic-layer---services)
9. [Presentation Layer - Controllers](#9-presentation-layer---controllers)
10. [Utilities - Exception Handling](#10-utilities---exception-handling)
11. [Configuration Layer](#11-configuration-layer)
12. [Application Entry Point](#12-application-entry-point)
13. [Frontend - React Application](#13-frontend---react-application)
14. [Key Java & Spring Boot Concepts](#14-key-java--spring-boot-concepts)
15. [HTTP and REST Concepts](#15-http-and-rest-concepts)
16. [Study Questions & Answers](#16-study-questions--answers)

---

# 1. Project Overview

## What is This Application?

This is a **full-stack web application** that manages university departments and professors.

**Components:**
- **Backend**: Spring Boot (Java) - Handles data and business rules
- **Frontend**: React (JavaScript) - User interface
- **Database**: H2 (In-memory) - Stores data temporarily

**What It Does:**
- Create, read, update, and delete departments
- Create, read, update, and delete professors
- Assign professors to departments
- View departments with all their professors

---

# 2. Architecture Deep Dive

## The 3-Layer Architecture

```
┌─────────────────────────────────────────┐
│     PRESENTATION LAYER                  │  ← User Interface
│     (Controllers)                       │  ← Handles HTTP Requests
│  - Receives HTTP requests               │
│  - Validates input                      │
│  - Returns HTTP responses               │
└─────────────────────────────────────────┘
              ↓ calls
┌─────────────────────────────────────────┐
│     BUSINESS LOGIC LAYER                │  ← Brain of Application
│     (Services)                          │  ← Business Rules
│  - Implements business rules            │
│  - Orchestrates data flow               │
│  - Uses mappers                         │
└─────────────────────────────────────────┘
              ↓ calls
┌─────────────────────────────────────────┐
│     DATA ACCESS LAYER                   │  ← Database Communication
│     (Repositories & Entities)           │  ← Stores Data
│  - Talks to database                    │
│  - Retrieves/saves data                 │
└─────────────────────────────────────────┘
```

## Why 3 Layers?

**1. Separation of Concerns**
- Each layer has ONE job
- Easy to find and fix bugs
- Easy to test each layer separately

**2. Maintainability**
- Change UI without changing business logic
- Change database without changing UI
- Each layer can be worked on independently

**3. Reusability**
- Business logic can be used by web, mobile, or desktop
- Different front-ends can use same backend

---

# 3. Packages and Their Purpose

## Package Structure Explained

```
com.champsoft.universitydepartmentsystem
│
├── PresentationLayer/          WHY: Handles HTTP communication
│   ├── DepartmentController    WHAT: REST endpoints for departments
│   └── ProfessorController     WHAT: REST endpoints for professors
│
├── BuisnessLogicLayer/         WHY: Business rules and logic
│   ├── DepartmentService       WHAT: Department business logic
│   └── ProfessorService        WHAT: Professor business logic
│
├── DataLayer/                  WHY: Database access
│   ├── Department              WHAT: Department table definition
│   ├── DepartmentRepository    WHAT: Database queries for departments
│   ├── Professor               WHAT: Professor table definition
│   └── ProfessorRepository     WHAT: Database queries for professors
│
├── DTO/                        WHY: Data transfer between layers
│   ├── DepartmentRequestModel  WHAT: Data coming IN for departments
│   ├── DepartmentResponseModel WHAT: Data going OUT for departments
│   ├── ProfessorRequestModel   WHAT: Data coming IN for professors
│   └── ProfessorResponseModel  WHAT: Data going OUT for professors
│
├── MapperLayer/                WHY: Convert between Entities and DTOs
│   ├── DepartmentMapper        WHAT: Convert Department ↔ DTOs
│   └── ProfessorMapper         WHAT: Convert Professor ↔ DTOs
│
├── utilities/                  WHY: Helper classes
│   ├── NotFoundException       WHAT: Custom error for missing data
│   ├── ErrorResponse           WHAT: Standard error format
│   └── GlobalExceptionHandler  WHAT: Centralized error handling
│
└── config/                     WHY: Application configuration
    └── CorsConfig              WHAT: Allow frontend to talk to backend
```

## Why This Organization?

**Feature-Based vs Layer-Based:**
- This uses **layer-based** organization
- All controllers together, all services together
- Alternative: feature-based (all department files together)
- Layer-based is better for learning and small projects

---

# 4. Data Layer - Entities

## What is an Entity?

An **Entity** is a Java class that represents a **table in the database**.

**Key Concepts:**
- One Entity = One Table
- One Object = One Row in Table
- Each Field = One Column in Table

---

## Department Entity - DETAILED BREAKDOWN

**File:** `DataLayer/Department.java`

```java
package com.champsoft.universitydepartmentsystem.DataLayer;
```
**EXPLANATION:**
- `package` = Organizes code into folders
- This class is in the DataLayer folder
- Full name: `com.champsoft.universitydepartmentsystem.DataLayer.Department`

---

```java
import jakarta.persistence.*;
```
**EXPLANATION:**
- `import` = Brings in code from other places
- `jakarta.persistence.*` = JPA (Java Persistence API) annotations
- `*` = Import everything from jakarta.persistence
- **JPA** = Standard way to work with databases in Java

**Why JPA?**
- Don't write SQL manually
- JPA converts Java objects to database tables
- Works with any database (H2, MySQL, PostgreSQL)

---

```java
import lombok.*;
```
**EXPLANATION:**
- `lombok` = Library that generates repetitive code
- Saves writing getters, setters, constructors
- Makes code cleaner and shorter

---

```java
@Entity
```
**KEYWORD:** `@Entity`
**TYPE:** JPA Annotation
**PURPOSE:** Tells JPA "This class is a database table"
**EFFECT:**
- JPA creates a table named `DEPARTMENT`
- Each field becomes a column
- When app starts, table is created automatically

**Without @Entity:**
- Just a regular Java class
- JPA ignores it
- No table created

---

```java
@Getter
@Setter
```
**KEYWORD:** `@Getter` and `@Setter`
**TYPE:** Lombok Annotations
**PURPOSE:** Automatically generate getter and setter methods

**What it generates:**
```java
// Without Lombok, you'd write:
public String getName() { return name; }
public void setName(String name) { this.name = name; }

// With Lombok, this is automatic!
```

**Why use getters/setters?**
- **Encapsulation** = Hide internal data
- Can add validation in setters
- Can compute values in getters
- Standard Java practice (Java Beans)

---

```java
@NoArgsConstructor
```
**KEYWORD:** `@NoArgsConstructor`
**TYPE:** Lombok Annotation
**PURPOSE:** Creates empty constructor

**What it generates:**
```java
public Department() {
    // Empty constructor
}
```

**Why needed?**
- JPA requires a no-argument constructor
- Used when loading data from database
- JPA creates object first, then fills fields

---

```java
public class Department {
```
**KEYWORD:** `public class`
**EXPLANATION:**
- `public` = Can be used anywhere in the project
- `class` = Blueprint for creating objects
- `Department` = Name of the class (PascalCase)

**Object-Oriented Concept:**
- **Class** = Template/Blueprint (like a cookie cutter)
- **Object** = Actual instance (like a cookie)

```java
// Class is the blueprint
public class Department { }

// Objects are instances
Department cs = new Department();  // One object
Department math = new Department(); // Another object
```

---

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

**KEYWORD:** `@Id`
**TYPE:** JPA Annotation
**PURPOSE:** Marks this field as the Primary Key
**MEANING:**
- Each row in table needs unique identifier
- `id` is that unique identifier
- Cannot be null, must be unique

---

**KEYWORD:** `@GeneratedValue`
**TYPE:** JPA Annotation
**PURPOSE:** Auto-generate ID values

**Parameter:** `strategy = GenerationType.IDENTITY`
**MEANING:**
- Database automatically generates ID
- Each new record gets next number (1, 2, 3, ...)
- You don't set the ID, database does

**Why auto-generate?**
- Prevents duplicate IDs
- No need to track next number
- Database handles it efficiently

---

**KEYWORD:** `private`
**TYPE:** Access Modifier
**PURPOSE:** Only accessible within this class

**Why private?**
- **Encapsulation** = Hide internal details
- Access through getters/setters only
- Can change internal implementation later

---

**KEYWORD:** `Long`
**TYPE:** Data Type
**PURPOSE:** Whole number (can be very large)

**Why Long instead of int?**
- Long can hold bigger numbers (up to 9,223,372,036,854,775,807)
- int only up to 2,147,483,647
- Database IDs can get very large
- Standard for database IDs

---

```java
    @Column(nullable = false, unique = true)
    private String name;
```

**KEYWORD:** `@Column`
**TYPE:** JPA Annotation
**PURPOSE:** Configure database column

**Parameters:**
- `nullable = false` = This field CANNOT be null (required)
- `unique = true` = No two departments can have same name

**What happens if you try to save duplicate name?**
```java
Department d1 = new Department("Computer Science", "CS", 2000);
Department d2 = new Department("Computer Science", "IT", 2010);
// Second save will throw DataIntegrityViolationException
// because "Computer Science" already exists
```

---

**KEYWORD:** `String`
**TYPE:** Data Type
**PURPOSE:** Text (sequence of characters)

**String characteristics:**
- Immutable (cannot be changed once created)
- Can hold any text: "Hello", "Computer Science", "A"
- In database, becomes VARCHAR type

---

```java
    @Column(nullable = false, unique = true)
    private String code;
```

**Same as name, but for department code**

**Why unique?**
- Code is often used as identifier
- Example: "CS" for Computer Science
- Cannot have two departments with code "CS"

---

```java
    private Integer yearEstablished;
```

**KEYWORD:** `Integer`
**TYPE:** Data Type (Wrapper class)
**PURPOSE:** Whole number that can be null

**Why Integer instead of int?**
```java
int year = 2000;     // Primitive, cannot be null
Integer year = 2000; // Object, can be null

Integer year = null; // OK - optional field
int year = null;     // ERROR - primitives can't be null
```

**When to use Integer vs int:**
- Use `Integer` when field is optional
- Use `int` when field is required
- In this case: yearEstablished is optional, so Integer

---

```java
    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Professor> professors = new HashSet<>();
```

**KEYWORD:** `@OneToMany`
**TYPE:** JPA Annotation
**PURPOSE:** Define relationship between tables

**What it means:**
- **One** Department has **Many** Professors
- This is a collection of professors
- Represents the relationship in database

---

**Parameter:** `mappedBy = "department"`
**MEANING:**
- The relationship is defined on the other side
- Look at Professor class, field named "department"
- That field controls the relationship
- This side is "inverse side" (doesn't control FK)

**Why mappedBy?**
- Tells JPA: "Don't create a join table"
- Professor table has the foreign key
- This is just the "view" from Department side

---

**Parameter:** `cascade = CascadeType.PERSIST`
**MEANING:**
- When you save a Department, save its Professors too
- **Cascade** = Operations "cascade" to related entities

**Example:**
```java
Department dept = new Department("CS", "CS", 2000);
Professor prof = new Professor("John", "Smith", "j@uni.ca", "Full", dept);
dept.getProfessors().add(prof);

// Save department - professor is saved automatically!
departmentRepository.save(dept);
```

**Cascade Types:**
- `PERSIST` = Cascade save operations
- `MERGE` = Cascade update operations
- `REMOVE` = Cascade delete (we DON'T use this)
- `ALL` = Cascade everything (dangerous!)

**Why not CASCADE delete?**
- If department is deleted, professors shouldn't be deleted
- Professors can exist without department
- Prevents accidental data loss

---

**Parameter:** `fetch = FetchType.LAZY`
**MEANING:**
- Don't load professors immediately
- Load them only when accessed
- **Lazy** = Load on demand

**Lazy vs Eager:**
```java
// LAZY (default for collections)
Department dept = repo.findById(1); // SQL: SELECT * FROM department WHERE id = 1
// No professors loaded yet
dept.getProfessors().size(); // NOW professors loaded

// EAGER (load immediately)
Department dept = repo.findById(1);
// SQL: SELECT * FROM department ... JOIN professor
// Professors already loaded
```

**Why LAZY for collections?**
- Better performance
- Department might have 100 professors
- Only load if needed
- Prevents loading too much data

**When to use EAGER?**
- Use for single objects (ManyToOne)
- When you ALWAYS need the data
- Small amounts of related data

---

**KEYWORD:** `Set<Professor>`
**TYPE:** Collection Type
**PURPOSE:** Collection with no duplicates

**Set vs List:**
```java
Set<Professor> set = new HashSet<>();
set.add(prof1);
set.add(prof1); // Ignored - already exists
// Set has 1 element

List<Professor> list = new ArrayList<>();
list.add(prof1);
list.add(prof1); // Added again
// List has 2 elements (duplicates allowed)
```

**Why Set?**
- A professor can't be in same department twice
- Prevents duplicates
- No particular order needed

---

**KEYWORD:** `new HashSet<>()`
**PURPOSE:** Initialize the collection

**Why initialize?**
- Prevents NullPointerException
- Can add professors immediately
- Empty set, not null

```java
// Without initialization
private Set<Professor> professors; // null

// Trying to use it:
dept.getProfessors().add(prof); // NullPointerException!

// With initialization
private Set<Professor> professors = new HashSet<>(); // empty set
dept.getProfessors().add(prof); // Works!
```

---

```java
    public Department(String name, String code, Integer yearEstablished) {
        this.name = name;
        this.code = code;
        this.yearEstablished = yearEstablished;
        this.professors = new HashSet<>();
    }
```

**KEYWORD:** `public Department(...)`
**TYPE:** Constructor
**PURPOSE:** Create new Department objects

**What is a Constructor?**
- Special method that creates objects
- Same name as class
- No return type
- Called with `new` keyword

**Example usage:**
```java
Department dept = new Department("Computer Science", "CS", 1995);
// Creates new department with these values
```

---

**KEYWORD:** `this`
**PURPOSE:** Refers to current object

**Why use this?**
```java
public Department(String name, String code, Integer yearEstablished) {
    // WITHOUT this - ambiguous
    name = name; // Which name? Parameter or field?

    // WITH this - clear
    this.name = name; // this.name = field, name = parameter
}
```

**When to use this:**
- When parameter name same as field name
- To make code clearer
- To call other constructors

---

## Professor Entity - DETAILED BREAKDOWN

**File:** `DataLayer/Professor.java`

Most annotations same as Department, but let's focus on differences:

```java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "department_id", nullable = false)
private Department department;
```

**KEYWORD:** `@ManyToOne`
**TYPE:** JPA Annotation
**PURPOSE:** Define many-to-one relationship

**What it means:**
- **Many** Professors belong to **One** Department
- This is the "owning side" of relationship
- Professor table has the foreign key

**Owning Side vs Inverse Side:**
```
OWNING SIDE (@ManyToOne)
- Has @JoinColumn
- Controls the foreign key
- Professor.department

INVERSE SIDE (@OneToMany)
- Has mappedBy
- Just a "view" of relationship
- Department.professors
```

---

**Parameter:** `fetch = FetchType.EAGER`
**MEANING:**
- Load department immediately with professor
- **Eager** = Load right away

**Why EAGER here?**
- Professor almost always needs department info
- Only loading ONE department (not collection)
- Better to load it immediately

**When you load a professor:**
```java
// With EAGER
Professor prof = repo.findById(1);
String deptName = prof.getDepartment().getName(); // Already loaded!

// With LAZY
Professor prof = repo.findById(1);
String deptName = prof.getDepartment().getName(); // Would trigger another SQL query
```

---

**KEYWORD:** `@JoinColumn`
**TYPE:** JPA Annotation
**PURPOSE:** Define foreign key column

**Parameters:**
- `name = "department_id"` = Column name in database
- `nullable = false` = Professor MUST have a department

**What it creates:**
```sql
CREATE TABLE professor (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    title VARCHAR(255),
    department_id BIGINT NOT NULL,  ← This column!
    FOREIGN KEY (department_id) REFERENCES department(id)
);
```

**Why foreign key?**
- Ensures department exists before adding professor
- Cannot delete department if professors exist
- Maintains data integrity

---

## Key Entity Concepts Summary

**Entity Lifecycle:**
```
1. Transient    - Object created, not in database
2. Persistent   - Object saved, in database, managed by JPA
3. Detached     - Object was in database, no longer managed
4. Removed      - Object marked for deletion
```

**Example:**
```java
// 1. TRANSIENT
Department dept = new Department("CS", "CS", 2000);

// 2. PERSISTENT
departmentRepository.save(dept); // Now in database

// 3. DETACHED
// After transaction ends, object is detached

// 4. REMOVED
departmentRepository.delete(dept); // Marked for deletion
```

---

# 5. Data Layer - Repositories

## What is a Repository?

A **Repository** is an interface that provides database operations.

**Key Concepts:**
- Interface (not a class!)
- Spring Data JPA implements it automatically
- No need to write SQL
- CRUD operations provided by default

---

## DepartmentRepository - DETAILED BREAKDOWN

**File:** `DataLayer/DepartmentRepository.java`

```java
package com.champsoft.universitydepartmentsystem.DataLayer;

import com.champsoft.universitydepartmentsystem.DataLayer.Department;
import org.springframework.data.jpa.repository.JpaRepository;
```

**Import breakdown:**
- `Department` = The entity we're working with
- `JpaRepository` = Spring Data JPA interface

---

```java
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
```

**KEYWORD:** `interface`
**TYPE:** Java construct
**PURPOSE:** Contract that defines methods

**Interface vs Class:**
```java
// Interface - just signatures
public interface DepartmentRepository {
    Department save(Department dept);
    Department findById(Long id);
}

// Class - has implementation
public class DepartmentRepositoryImpl implements DepartmentRepository {
    public Department save(Department dept) {
        // Actual code to save
    }
}
```

**Why interface?**
- Defines WHAT to do, not HOW
- Spring provides implementation automatically
- Can swap implementations easily

---

**KEYWORD:** `extends JpaRepository<Department, Long>`
**PURPOSE:** Inherit methods from JpaRepository

**Generic Parameters:**
- `<Department, Long>` = Entity type, ID type
- Department = Work with Department entities
- Long = ID is of type Long

**What methods do you get?**
```java
// From JpaRepository (automatically available):
save(Department dept)              // Create or update
findById(Long id)                  // Find by ID
findAll()                          // Get all departments
deleteById(Long id)                // Delete by ID
existsById(Long id)                // Check if exists
count()                            // Count all departments

// And many more!
```

**Magic of Spring Data JPA:**
- You just declare the interface
- Spring creates implementation at runtime
- No SQL needed!
- Works with any database

---

**Why is the interface empty?**

```java
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Empty! But still works!
}
```

**Because:**
1. JpaRepository already has all basic methods
2. If you need custom queries, add them here
3. Spring generates implementation automatically

**Example custom methods:**
```java
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Spring understands method name and generates query!
    List<Department> findByName(String name);
    List<Department> findByCodeStartingWith(String prefix);
    Department findByCode(String code);

    // Or write custom SQL:
    @Query("SELECT d FROM Department d WHERE d.yearEstablished > :year")
    List<Department> findDepartmentsEstablishedAfter(@Param("year") Integer year);
}
```

---

## Repository Method Naming Convention

**Spring Data JPA understands method names:**

```java
// Find methods
findBy<FieldName>                    // Find by exact match
findBy<Field>Containing              // Find by partial match
findBy<Field>StartingWith            // Find by prefix
findBy<Field>GreaterThan             // Find where field > value

// Example:
findByName("Computer Science")       → WHERE name = 'Computer Science'
findByCodeStartingWith("C")          → WHERE code LIKE 'C%'
findByYearEstablishedGreaterThan(2000) → WHERE year_established > 2000

// Multiple conditions
findByNameAndCode(String name, String code) → WHERE name = ? AND code = ?
findByNameOrCode(String name, String code)  → WHERE name = ? OR code = ?

// Ordering
findByNameOrderByYearEstablishedDesc(String name) → WHERE name = ? ORDER BY year_established DESC

// Existence
existsByCode(String code)            → SELECT COUNT(*) > 0 WHERE code = ?

// Counting
countByYearEstablishedGreaterThan(Integer year) → SELECT COUNT(*) WHERE year_established > ?
```

**Spring automatically generates SQL!**

---

# 6. DTO Layer - Data Transfer Objects

## What is a DTO?

**DTO** = Data Transfer Object

**Purpose:**
- Transfer data between layers
- Separate internal structure from external API
- Hide sensitive fields
- Validate input

**Why DTOs?**
```
WITHOUT DTOs:
Client → Entity (Department) → Database
- Client sees database structure
- Can't hide fields
- Hard to change database without breaking API

WITH DTOs:
Client → RequestDTO → Entity → Database
Database → Entity → ResponseDTO → Client
- Client sees only what we want
- Can change database freely
- Better security
- Validation on DTOs
```

---

## DepartmentRequestModel - DETAILED BREAKDOWN

**File:** `DTO/DepartmentRequestModel.java`

**Purpose:** Data coming IN (from client to server)

```java
package com.champsoft.universitydepartmentsystem.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
```

**Import breakdown:**
- These are validation annotations
- From **Bean Validation API** (JSR 380)
- Automatically validate data

---

```java
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
```

**Lombok annotations:**
- `@AllArgsConstructor` = Constructor with all fields
- `@NoArgsConstructor` = Empty constructor
- `@Getter` = Generate getters
- `@Setter` = Generate setters

**Why both constructors?**
```java
// NoArgsConstructor - for frameworks
DepartmentRequestModel dto = new DepartmentRequestModel();
dto.setName("CS");
dto.setCode("CS");

// AllArgsConstructor - for convenience
DepartmentRequestModel dto = new DepartmentRequestModel("CS", "CS", 2000);
```

---

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestModel {
```

**Why these annotations?**
- **@Getter/@Setter**: Access fields (Spring needs this)
- **@NoArgsConstructor**: JSON deserialization needs this
- **@AllArgsConstructor**: Easy object creation in tests

**JSON Deserialization:**
```java
// When client sends:
{
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}

// Spring does:
DepartmentRequestModel dto = new DepartmentRequestModel(); // NoArgs constructor
dto.setName("Computer Science"); // Setter
dto.setCode("CS"); // Setter
dto.setYearEstablished(1995); // Setter
```

---

```java
    @NotBlank(message = "Department name is required.")
    private String name;
```

**KEYWORD:** `@NotBlank`
**TYPE:** Validation Annotation
**PURPOSE:** Ensure field is not empty

**What @NotBlank checks:**
```java
// VALID
"Computer Science" ✓
"CS" ✓

// INVALID - triggers error
null ✗
"" (empty string) ✗
"   " (only spaces) ✗
```

**Validation happens when:**
```java
@PostMapping
public ResponseEntity<...> create(@Valid @RequestBody DepartmentRequestModel req) {
    // @Valid triggers validation
    // If validation fails, Spring returns 400 Bad Request
}
```

**Error message:**
```json
{
  "validationErrors": {
    "name": "Department name is required."
  }
}
```

---

**@NotBlank vs @NotNull vs @NotEmpty:**
```java
@NotNull     // Must not be null (but can be empty)
String s = ""; // Valid

@NotEmpty    // Must not be null or empty (but can be spaces)
String s = "   "; // Valid

@NotBlank    // Must not be null, empty, or only spaces
String s = "CS"; // Only this is valid
```

---

```java
    @Min(value = 1800, message = "Year established must be 1800 or later.")
    @Max(value = 2100, message = "Year established cannot be in the future.")
    private Integer yearEstablished;
```

**KEYWORD:** `@Min` and `@Max`
**TYPE:** Validation Annotations
**PURPOSE:** Validate number range

**What they check:**
```java
// VALID
1800 ✓
1995 ✓
2025 ✓
2100 ✓

// INVALID
1799 ✗ (< 1800)
2101 ✗ (> 2100)
```

**Why these limits?**
- 1800: Universities didn't exist before 1800
- 2100: Can't establish department in far future

**No @NotNull on yearEstablished:**
- This field is **optional**
- Can be `null`
- If provided, must be in range

---

## DepartmentResponseModel - DETAILED BREAKDOWN

**File:** `DTO/DepartmentResponseModel.java`

**Purpose:** Data going OUT (from server to client)

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseModel {
    private Long id;
    private String name;
    private String code;
    private Integer yearEstablished;
}
```

**Key differences from RequestModel:**
1. **Has `id` field** - generated by database
2. **No validation annotations** - already validated
3. **No sensitive fields** - safe to send to client

**Usage:**
```java
// Entity → ResponseDTO
Department entity = repo.findById(1);
DepartmentResponseModel dto = new DepartmentResponseModel(
    entity.getId(),
    entity.getName(),
    entity.getCode(),
    entity.getYearEstablished()
);

// Send to client as JSON:
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}
```

---

## ProfessorRequestModel - DETAILED BREAKDOWN

**File:** `DTO/ProfessorRequestModel.java`

```java
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a well-formed email address.")
    private String email;

    private String title;

    @NotNull(message = "Department ID is required to assign a Professor.")
    @Positive(message = "Department ID must be a positive number.")
    private Long departmentId;
```

**New annotations:**

**KEYWORD:** `@Email`
**PURPOSE:** Validate email format

**What it checks:**
```java
// VALID
"john@uni.ca" ✓
"j.smith@example.com" ✓

// INVALID
"notanemail" ✗
"@uni.ca" ✗
"john@" ✗
```

**Uses regex pattern to validate email structure**

---

**KEYWORD:** `@NotNull`
**PURPOSE:** Field cannot be null (but can be empty)

**Why @NotNull instead of @NotBlank?**
- `departmentId` is a `Long` (number), not `String`
- `@NotBlank` only works with strings
- `@NotNull` works with any object

---

**KEYWORD:** `@Positive`
**PURPOSE:** Number must be greater than 0

**What it checks:**
```java
// VALID
1 ✓
2 ✓
100 ✓

// INVALID
0 ✗
-1 ✗
```

**Why positive?**
- Database IDs start at 1
- ID cannot be 0 or negative
- Ensures valid department reference

---

**Why include departmentId?**
```java
// Without departmentId - ambiguous
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john@uni.ca",
  "title": "Professor"
  // Which department???
}

// With departmentId - clear
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john@uni.ca",
  "title": "Professor",
  "departmentId": 1  ← Assign to department with ID 1
}
```

---

## ProfessorResponseModel - DETAILED BREAKDOWN

**File:** `DTO/ProfessorResponseModel.java`

```java
public class ProfessorResponseModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private Long departmentId;
    private DepartmentSummary department;
}
```

**Two ways to show department:**

1. **departmentId** - Just the ID number
   ```json
   {
     "id": 1,
     "firstName": "John",
     "departmentId": 1
   }
   ```
   - Minimal data
   - Client needs another request to get department details

2. **department (DepartmentSummary)** - Nested object with key info
   ```json
   {
     "id": 1,
     "firstName": "John",
     "departmentId": 1,
     "department": {
       "id": 1,
       "name": "Computer Science",
       "code": "CS"
     }
   }
   ```
   - More convenient for client
   - One request gets all needed info
   - But more data transferred

**Why include both?**
- **departmentId**: Easy to update (send just the ID)
- **department**: Display purposes (show name, not just ID)

---

## Summary DTOs

### DepartmentSummary

```java
public class DepartmentSummary {
    private Long id;
    private String name;
    private String code;
}
```

**Purpose:**
- Used in nested responses
- Minimal info (not all fields)
- Prevents circular references

**Without Summary DTO - Problem:**
```java
// Circular reference - infinite loop!
Professor → Department → List<Professor> → Department → ...
```

**With Summary DTO - Fixed:**
```java
Professor → DepartmentSummary (just id, name, code - no professors list)
```

---

### ProfessorSummary

```java
public class ProfessorSummary {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
}
```

**Purpose:**
- Used in department aggregated DTO
- Shows professor list without full details
- No department field (would be circular)

---

## Aggregated DTOs

### DepartmentWithProfessorsResponseDTO

```java
public class DepartmentWithProfessorsResponseDTO {
    private Long id;
    private String name;
    private String code;
    private Integer yearEstablished;
    private List<ProfessorSummary> professors;
}
```

**Purpose:**
- Show department WITH all its professors
- One request gets everything
- Used by aggregated endpoint

**Example response:**
```json
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995,
  "professors": [
    {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Smith",
      "title": "Full Professor"
    },
    {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Johnson",
      "title": "Assistant Professor"
    }
  ]
}
```

**When to use:**
- Displaying department details page
- Showing department with its staff
- Reports

---

# 7. Mapper Layer

## What is a Mapper?

A **Mapper** converts between Entities and DTOs.

**Why needed?**
```
Entity (Database) ←→ DTO (API)

Department entity has:
- id, name, code, yearEstablished, Set<Professor>

DepartmentResponseModel has:
- id, name, code, yearEstablished (no professors)

Mapper converts one to the other
```

---

## DepartmentMapper - DETAILED BREAKDOWN

**File:** `MapperLayer/DepartmentMapper.java`

```java
@Component
public class DepartmentMapper {
```

**KEYWORD:** `@Component`
**TYPE:** Spring Annotation
**PURPOSE:** Make this a Spring-managed bean

**What does @Component do?**
- Spring creates ONE instance of this class
- Available for dependency injection
- Managed by Spring container

**Spring Container:**
```
┌─────────────────────────────┐
│   Spring Container          │
│                             │
│  DepartmentMapper instance  │ ← @Component creates this
│  DepartmentService instance │
│  DepartmentController instance │
│                             │
└─────────────────────────────┘
```

**Without @Component:**
```java
// Manual creation - BAD
DepartmentMapper mapper = new DepartmentMapper();
// Each class creates own instance
// Hard to manage
```

**With @Component:**
```java
// Spring manages - GOOD
@Autowired
private DepartmentMapper mapper; // Spring injects it
```

---

```java
    public DepartmentResponseModel toResponseModel(Department department) {
        return new DepartmentResponseModel(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getYearEstablished()
        );
    }
```

**Method signature breakdown:**

**KEYWORD:** `public`
- Accessible from anywhere
- Other classes can call this method

**KEYWORD:** `DepartmentResponseModel` (return type)
- This method returns a DepartmentResponseModel object
- Must match with `return` statement

**Method name:** `toResponseModel`
- Converts TO response model
- Clear, descriptive name
- Verb-based (toXxx, getXxx, setXxx)

**Parameter:** `Department department`
- Input: an Entity
- Convert from Entity to DTO

**Logic:**
1. Create new ResponseModel object
2. Copy fields from entity
3. Return the DTO

**Usage:**
```java
Department entity = repo.findById(1);
DepartmentResponseModel dto = mapper.toResponseModel(entity);
// Now dto can be sent to client
```

---

```java
    public Department toEntity(DepartmentRequestModel requestModel) {
        return new Department(
                requestModel.getName(),
                requestModel.getCode(),
                requestModel.getYearEstablished()
        );
    }
```

**Purpose:** Convert RequestDTO to Entity

**Key points:**
- No ID in constructor (database generates it)
- Takes data from DTO
- Creates new entity ready to save

**Usage:**
```java
DepartmentRequestModel dto = /* from client */;
Department entity = mapper.toEntity(dto);
repo.save(entity); // Save to database
```

---

```java
    public DepartmentWithProfessorsResponseDTO toDepartmentWithProfessorsResponseDTO(Department department) {
        List<ProfessorSummary> professorSummaries = department.getProfessors().stream()
                .map(prof -> new ProfessorSummary(
                        prof.getId(),
                        prof.getFirstName(),
                        prof.getLastName(),
                        prof.getTitle()
                ))
                .collect(Collectors.toList());

        return new DepartmentWithProfessorsResponseDTO(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getYearEstablished(),
                professorSummaries
        );
    }
```

**Advanced mapping with Stream API:**

**KEYWORD:** `stream()`
**PURPOSE:** Process collections functionally

**What is a Stream?**
- Sequence of elements
- Supports operations like map, filter, collect
- Functional programming style

**Step-by-step breakdown:**

```java
// 1. Get professors from department
Set<Professor> professors = department.getProfessors();

// 2. Convert to stream
Stream<Professor> stream = professors.stream();

// 3. Map each professor to ProfessorSummary
Stream<ProfessorSummary> summaryStream = stream.map(prof ->
    new ProfessorSummary(
        prof.getId(),
        prof.getFirstName(),
        prof.getLastName(),
        prof.getTitle()
    )
);

// 4. Collect back to list
List<ProfessorSummary> professorSummaries = summaryStream.collect(Collectors.toList());
```

**Lambda expression explained:**
```java
.map(prof -> new ProfessorSummary(...))
     ↑          ↑
     |          └─ What to do with it
     └─ Each professor
```

**Why use streams?**
- Concise code
- Readable
- Functional style
- Can be parallelized

**Alternative without streams:**
```java
// Old way - more verbose
List<ProfessorSummary> summaries = new ArrayList<>();
for (Professor prof : department.getProfessors()) {
    ProfessorSummary summary = new ProfessorSummary(
        prof.getId(),
        prof.getFirstName(),
        prof.getLastName(),
        prof.getTitle()
    );
    summaries.add(summary);
}
```

---

## ProfessorMapper - DETAILED BREAKDOWN

```java
    public ProfessorResponseModel toResponseModel(Professor professor) {
        DepartmentSummary departmentSummary = null;
        Long departmentId = null;

        if (professor.getDepartment() != null) {
            Department dept = professor.getDepartment();
            departmentId = dept.getId();
            departmentSummary = new DepartmentSummary(
                    dept.getId(),
                    dept.getName(),
                    dept.getCode()
            );
        }

        return new ProfessorResponseModel(
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getTitle(),
                departmentId,
                departmentSummary
        );
    }
```

**Why the null check?**

```java
if (professor.getDepartment() != null) {
```

**Safety first!**
- Prevents NullPointerException
- Department might not be loaded (if LAZY)
- Professor might be detached from session

**What happens if null?**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Smith",
  "email": "john@uni.ca",
  "title": "Professor",
  "departmentId": null,
  "department": null
}
```

**Better to have null than crash the application!**

---

```java
    public Professor toEntity(ProfessorRequestModel requestModel, Department department) {
        return new Professor(
                requestModel.getFirstName(),
                requestModel.getLastName(),
                requestModel.getEmail(),
                requestModel.getTitle(),
                department
        );
    }
```

**Why pass Department object?**

**Client sends:**
```json
{
  "firstName": "John",
  "departmentId": 1
}
```

**Mapper needs actual Department object:**
```java
// Service layer does:
Department dept = departmentRepo.findById(requestModel.getDepartmentId())
    .orElseThrow(() -> new NotFoundException("Department", id));

// Then pass to mapper:
Professor prof = mapper.toEntity(requestModel, dept);
```

**Why not look up department in mapper?**
- Mapper should be simple (no database access)
- Service layer handles business logic
- Separation of concerns

---

# 8. Business Logic Layer - Services

## What is a Service?

A **Service** contains business logic.

**Responsibilities:**
- Implement business rules
- Coordinate between layers
- Handle transactions
- Throw business exceptions
- Use repositories and mappers

**NOT responsible for:**
- HTTP concerns (that's controller's job)
- Database details (that's repository's job)
- DTO conversions (that's mapper's job)

---

## DepartmentService - DETAILED BREAKDOWN

**File:** `BuisnessLogicLayer/DepartmentService.java`

```java
@Service
@RequiredArgsConstructor
public class DepartmentService {
```

**KEYWORD:** `@Service`
**TYPE:** Spring Annotation
**PURPOSE:** Mark as service bean

**What @Service does:**
- Same as @Component
- But semantically means "business logic"
- Spring manages lifecycle
- Available for dependency injection

**@Service vs @Component:**
```java
@Component   // Generic Spring bean
@Service     // Business logic layer
@Repository  // Data access layer
@Controller  // Presentation layer

// All do same thing, but convey different meaning
```

---

**KEYWORD:** `@RequiredArgsConstructor`
**TYPE:** Lombok Annotation
**PURPOSE:** Generate constructor for final fields

**What it generates:**
```java
// Without Lombok - you write:
private final DepartmentRepository repo;
private final DepartmentMapper mapper;

public DepartmentService(DepartmentRepository repo, DepartmentMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
}

// With Lombok - automatic!
@RequiredArgsConstructor
private final DepartmentRepository repo;
private final DepartmentMapper mapper;
// Constructor generated automatically
```

**Why final fields?**
- Immutable after construction
- Must be initialized
- Clear dependencies
- Thread-safe

---

```java
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
```

**Dependency Injection:**

**What Spring does:**
```java
// 1. Spring creates beans
DepartmentRepository repo = new DepartmentRepositoryImpl();
DepartmentMapper mapper = new DepartmentMapper();

// 2. Spring creates service with dependencies
DepartmentService service = new DepartmentService(repo, mapper);

// 3. Service is ready to use!
```

**Benefits:**
- Don't create dependencies manually
- Easy to swap implementations
- Easy to test (mock dependencies)
- Loose coupling

---

```java
    public List<DepartmentResponseModel> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponseModel)
                .collect(Collectors.toList());
    }
```

**Method Reference explained:**

```java
.map(departmentMapper::toResponseModel)
     ↑                ↑
     |                └─ Method to call
     └─ Object that has the method
```

**Equivalent to:**
```java
.map(dept -> departmentMapper.toResponseModel(dept))
```

**Step-by-step execution:**
```java
// 1. Get all departments from database
List<Department> entities = departmentRepository.findAll();

// 2. Convert each to DTO
Stream<DepartmentResponseModel> dtoStream = entities.stream()
    .map(mapper::toResponseModel);

// 3. Collect to list
List<DepartmentResponseModel> dtos = dtoStream.collect(Collectors.toList());

// 4. Return to controller
return dtos;
```

---

```java
    public DepartmentResponseModel findById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toResponseModel)
                .orElseThrow(() -> new NotFoundException("Department", id));
    }
```

**Optional API explained:**

**What is Optional?**
- Container that may or may not contain a value
- Prevents NullPointerException
- Forces you to handle "not found" case

**findById returns Optional:**
```java
Optional<Department> optional = departmentRepository.findById(1);

// Old way - dangerous
Department dept = repo.findById(1);
dept.getName(); // NullPointerException if not found!

// With Optional - safe
Optional<Department> opt = repo.findById(1);
if (opt.isPresent()) {
    Department dept = opt.get();
    dept.getName(); // Safe!
}
```

**Optional methods:**

```java
// map - transform if present
optional.map(dept -> mapper.toResponseModel(dept))

// orElse - provide default value
optional.orElse(defaultDepartment)

// orElseThrow - throw exception if empty
optional.orElseThrow(() -> new NotFoundException(...))

// isPresent - check if value exists
if (optional.isPresent()) { ... }
```

**In our code:**
```java
departmentRepository.findById(id)           // Optional<Department>
    .map(departmentMapper::toResponseModel) // Optional<DepartmentResponseModel>
    .orElseThrow(() -> new NotFoundException("Department", id)); // Throw if empty
```

**Flow:**
1. Find department by ID → Optional<Department>
2. If found, convert to DTO → Optional<DTO>
3. If empty, throw NotFoundException
4. Return DTO

---

```java
    public DepartmentResponseModel create(DepartmentRequestModel requestModel) {
        Department department = departmentMapper.toEntity(requestModel);
        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toResponseModel(savedDepartment);
    }
```

**Create flow:**

```java
// 1. Client sends JSON
{
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}

// 2. Controller converts to RequestModel
DepartmentRequestModel requestModel = /* from JSON */;

// 3. Service converts to Entity
Department entity = mapper.toEntity(requestModel);
// entity.id = null (not saved yet)

// 4. Save to database
Department savedEntity = repo.save(entity);
// savedEntity.id = 1 (database generated ID)

// 5. Convert to ResponseModel
DepartmentResponseModel response = mapper.toResponseModel(savedEntity);

// 6. Return to controller → client gets JSON
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}
```

---

```java
    @Transactional
    public DepartmentResponseModel update(Long id, DepartmentRequestModel requestModel) {
        Department departmentToUpdate = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department", id));

        departmentToUpdate.setName(requestModel.getName());
        departmentToUpdate.setCode(requestModel.getCode());
        departmentToUpdate.setYearEstablished(requestModel.getYearEstablished());

        return departmentMapper.toResponseModel(departmentToUpdate);
    }
```

**KEYWORD:** `@Transactional`
**TYPE:** Spring Annotation
**PURPOSE:** Database transaction management

**What is a Transaction?**
- Group of database operations
- All succeed or all fail
- ACID properties (Atomicity, Consistency, Isolation, Durability)

**Without @Transactional:**
```java
// Update 1 - succeeds
dept.setName("New Name");

// Update 2 - fails
// Database inconsistent state!
dept.setCode("INVALID CODE THAT CAUSES ERROR");
```

**With @Transactional:**
```java
// Both updates succeed, OR both fail
// Database always in consistent state
```

**How @Transactional works:**
```java
// Spring does:
1. Begin transaction
2. Execute method
3. If no exception: commit (save changes)
4. If exception: rollback (undo changes)
5. Close transaction
```

**Transaction lifecycle:**
```java
@Transactional
public void updateDepartment() {
    // BEGIN TRANSACTION

    dept.setName("New Name");
    dept.setCode("NEW");

    // If exception thrown here, rollback everything

    // COMMIT TRANSACTION (if no exception)
}
```

---

**Update logic explained:**

```java
// 1. Find existing department
Department dept = repo.findById(id).orElseThrow(...);
// Throws NotFoundException if not found

// 2. Update fields from request
dept.setName(requestModel.getName());
dept.setCode(requestModel.getCode());
dept.setYearEstablished(requestModel.getYearEstablished());

// 3. Return updated DTO
return mapper.toResponseModel(dept);
```

**Why no explicit save()?**
- Department is **managed** by JPA
- Inside @Transactional
- Changes tracked automatically
- Saved at end of transaction

**Managed vs Detached:**
```java
@Transactional
public void update() {
    Department dept = repo.findById(1); // MANAGED
    dept.setName("New");                // Change tracked
    // Auto-saved at method end
}

public void update() {
    Department dept = repo.findById(1); // DETACHED (no transaction)
    dept.setName("New");                // Change NOT tracked
    repo.save(dept);                    // Must save explicitly
}
```

---

```java
    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department", id);
        }
        departmentRepository.deleteById(id);
    }
```

**Why check existence first?**

**Option 1: Check first (our approach)**
```java
if (!repo.existsById(id)) {
    throw new NotFoundException("Department", id);
}
repo.deleteById(id);
```
**Pros:** Clear error message
**Cons:** Two database queries

**Option 2: Try delete, catch exception**
```java
try {
    repo.deleteById(id);
} catch (EmptyResultDataAccessException e) {
    throw new NotFoundException("Department", id);
}
```
**Pros:** One database query
**Cons:** Relies on exception handling

**Why our approach is better:**
- Clearer intent
- Explicit error handling
- Consistent with other methods

---

```java
    @Transactional(readOnly = true)
    public DepartmentWithProfessorsResponseDTO getDepartmentWithProfessors(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department", id));
        return departmentMapper.toDepartmentWithProfessorsResponseDTO(department);
    }
```

**KEYWORD:** `@Transactional(readOnly = true)`
**PURPOSE:** Read-only transaction

**Why readOnly?**
- Optimization hint to database
- No writes allowed
- Can use database replicas
- Lighter transaction overhead

**When to use readOnly:**
```java
@Transactional(readOnly = true)  // Query methods
public Department find() { }

@Transactional  // Update methods (readOnly = false by default)
public void update() { }
```

**Why transaction for read?**
- Access lazy-loaded collections
- Department has professors (LAZY)
- Without transaction, LazyInitializationException

**LazyInitializationException:**
```java
// WITHOUT transaction
Department dept = repo.findById(1);
// Session closed
dept.getProfessors().size(); // ERROR! Lazy collection not loaded

// WITH transaction
@Transactional(readOnly = true)
Department dept = repo.findById(1);
dept.getProfessors().size(); // OK! Session open
```

---

## ProfessorService - Similar to DepartmentService

Key differences:

```java
    @Transactional
    public ProfessorResponseModel create(ProfessorRequestModel requestModel) {
        // 1. Validate department exists
        Department department = departmentRepository.findById(requestModel.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department", requestModel.getDepartmentId()));

        // 2. Create professor with department
        Professor professor = professorMapper.toEntity(requestModel, department);

        // 3. Save professor
        Professor savedProfessor = professorRepository.save(professor);

        // 4. Return response
        return professorMapper.toResponseModel(savedProfessor);
    }
```

**Why validate department first?**
- Professor must have department
- Foreign key constraint in database
- Better error message if department doesn't exist
- Fail fast principle

**What happens if department doesn't exist:**
```java
// Client sends:
{
  "firstName": "John",
  "departmentId": 999  // Doesn't exist
}

// Without validation:
// Database error: FK constraint violation

// With validation:
// 404 Not Found: Department with ID 999 not found
// Much clearer!
```

---

# 9. Presentation Layer - Controllers

## What is a Controller?

A **Controller** handles HTTP requests and responses.

**Responsibilities:**
- Define REST endpoints
- Parse request data
- Validate input
- Call service methods
- Return HTTP responses
- Set appropriate status codes

**NOT responsible for:**
- Business logic (that's service's job)
- Database access (that's repository's job)
- Complex data transformations (that's mapper's job)

---

## DepartmentController - DETAILED BREAKDOWN

**File:** `PresentationLayer/DepartmentController.java`

```java
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
```

**KEYWORD:** `@RestController`
**TYPE:** Spring Annotation
**PURPOSE:** Mark as REST API controller

**What @RestController does:**
- Combination of @Controller + @ResponseBody
- All methods return data (not views)
- Automatic JSON conversion
- Handled by Spring MVC

**@RestController vs @Controller:**
```java
@Controller
public class WebController {
    @GetMapping("/page")
    public String showPage() {
        return "page.html"; // Returns HTML view
    }
}

@RestController
public class ApiController {
    @GetMapping("/data")
    public Data getData() {
        return new Data(); // Returns JSON data
    }
}
```

---

**KEYWORD:** `@RequestMapping("/api/departments")`
**TYPE:** Spring Annotation
**PURPOSE:** Base path for all endpoints

**How it works:**
```java
@RequestMapping("/api/departments")
public class DepartmentController {

    @GetMapping  // Full path: /api/departments
    public List<Department> getAll() { }

    @GetMapping("/{id}")  // Full path: /api/departments/{id}
    public Department getById() { }

    @PostMapping  // Full path: /api/departments
    public Department create() { }
}
```

**Path combining:**
```
@RequestMapping + @GetMapping = Full Path
/api/departments + / = /api/departments
/api/departments + /{id} = /api/departments/{id}
/api/departments + /{id}/professors = /api/departments/{id}/professors
```

---

```java
    private final DepartmentService departmentService;
```

**Dependency Injection in controller:**
- Constructor injection (via @RequiredArgsConstructor)
- Service is injected by Spring
- Controller delegates to service

**Flow:**
```
HTTP Request → Controller → Service → Repository → Database
HTTP Response ← Controller ← Service ← Repository ← Database
```

---

```java
    @GetMapping
    public List<DepartmentResponseModel> getAll() {
        return departmentService.getAllDepartments();
    }
```

**KEYWORD:** `@GetMapping`
**TYPE:** Spring Annotation
**PURPOSE:** Handle HTTP GET requests

**What happens:**
```
1. Client sends: GET /api/departments
2. Spring routes to this method
3. Method calls service
4. Service returns List<DepartmentResponseModel>
5. Spring converts to JSON
6. Client receives:
[
  { "id": 1, "name": "Computer Science", ... },
  { "id": 2, "name": "Mathematics", ... }
]
```

**Why no @ResponseBody?**
- @RestController includes it automatically
- Return value automatically converted to JSON

---

```java
    @GetMapping("/{id}")
    public DepartmentResponseModel getById(@PathVariable Long id) {
        return departmentService.findById(id);
    }
```

**KEYWORD:** `@PathVariable`
**TYPE:** Spring Annotation
**PURPOSE:** Extract value from URL path

**How it works:**
```
Request: GET /api/departments/5
                               ↑
                               |
                    @GetMapping("/{id}")
                                  ↑
                                  |
                    @PathVariable Long id = 5
```

**Naming:**
```java
// Method parameter name matches path variable
@GetMapping("/{id}")
public void method(@PathVariable Long id) { }

// Or specify name explicitly
@GetMapping("/{departmentId}")
public void method(@PathVariable("departmentId") Long id) { }
```

**Multiple path variables:**
```java
@GetMapping("/{deptId}/professors/{profId}")
public Professor get(
    @PathVariable Long deptId,
    @PathVariable Long profId
) { }

// Request: GET /api/departments/1/professors/5
// deptId = 1, profId = 5
```

---

```java
    @PostMapping
    public ResponseEntity<DepartmentResponseModel> create(
        @Valid @RequestBody DepartmentRequestModel req
    ) {
        var created = departmentService.create(req);

        return ResponseEntity
                .created(URI.create("/api/departments/" + created.getId()))
                .body(created);
    }
```

**KEYWORD:** `@PostMapping`
**TYPE:** Spring Annotation
**PURPOSE:** Handle HTTP POST requests (create)

---

**KEYWORD:** `ResponseEntity<DepartmentResponseModel>`
**TYPE:** Spring class
**PURPOSE:** Full control over HTTP response

**What is ResponseEntity?**
- Wrapper for HTTP response
- Contains: status code, headers, body
- Explicit control over response

**ResponseEntity vs plain return:**
```java
// Plain return - less control
@GetMapping
public Department get() {
    return dept; // 200 OK automatically
}

// ResponseEntity - full control
@GetMapping
public ResponseEntity<Department> get() {
    return ResponseEntity.ok(dept); // Explicit 200 OK
}
```

**ResponseEntity methods:**
```java
// 200 OK
ResponseEntity.ok(body)

// 201 Created
ResponseEntity.created(location).body(body)

// 204 No Content
ResponseEntity.noContent().build()

// 404 Not Found
ResponseEntity.notFound().build()

// Custom status
ResponseEntity.status(HttpStatus.ACCEPTED).body(body)
```

---

**KEYWORD:** `@Valid`
**TYPE:** Bean Validation Annotation
**PURPOSE:** Trigger validation

**What @Valid does:**
```java
@PostMapping
public ResponseEntity<...> create(@Valid @RequestBody DepartmentRequestModel req) {
    // Before this line executes, Spring validates req
    // Checks @NotBlank, @Min, @Max, etc.
    // If validation fails, returns 400 Bad Request
    // If validation passes, method executes
}
```

**Validation flow:**
```
1. Client sends JSON
2. Spring converts JSON to DepartmentRequestModel
3. Spring sees @Valid
4. Spring runs validation (@NotBlank, @Min, @Max)
5a. Validation fails → Return 400 with errors
5b. Validation passes → Execute method
```

---

**KEYWORD:** `@RequestBody`
**TYPE:** Spring Annotation
**PURPOSE:** Parse HTTP request body to object

**How it works:**
```
Client sends:
POST /api/departments
Content-Type: application/json
{
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}

Spring does:
1. Read request body (JSON)
2. Convert to DepartmentRequestModel object
3. Pass to method parameter
```

**Without @RequestBody:**
```java
// Would receive raw string, not object
public void create(String jsonString) {
    // Need to manually parse JSON
}
```

**With @RequestBody:**
```java
// Spring automatically converts JSON to object
public void create(@RequestBody DepartmentRequestModel req) {
    // req is ready to use
    String name = req.getName(); // Works!
}
```

---

**Creating URI for Location header:**

```java
URI.create("/api/departments/" + created.getId())
```

**What is URI?**
- Uniform Resource Identifier
- Location of created resource
- Part of REST best practices

**Why include Location header?**
```
HTTP/1.1 201 Created
Location: /api/departments/1
Content-Type: application/json

{
  "id": 1,
  "name": "Computer Science",
  ...
}
```

**Client can:**
- Know where to find the created resource
- Make GET request to Location URL
- Standard REST practice for POST

---

**ResponseEntity builder pattern:**

```java
return ResponseEntity
    .created(URI.create("/api/departments/" + created.getId()))
    .body(created);
```

**Step by step:**
```java
// 1. Start with status
ResponseEntity.created(...)

// 2. Set location header
.created(URI.create("/api/departments/1"))

// 3. Set response body
.body(created)

// 4. Build and return
```

**Alternative ways to build:**
```java
// Method chaining
ResponseEntity.ok()
    .header("Custom-Header", "value")
    .body(data);

// From builder
ResponseEntity.status(HttpStatus.CREATED)
    .location(uri)
    .body(data);
```

---

```java
    @PutMapping("/{id}")
    public DepartmentResponseModel update(
        @PathVariable Long id,
        @Valid @RequestBody DepartmentRequestModel req
    ) {
        return departmentService.update(id, req);
    }
```

**KEYWORD:** `@PutMapping`
**TYPE:** Spring Annotation
**PURPOSE:** Handle HTTP PUT requests (update)

**PUT characteristics:**
- **Idempotent**: Same request multiple times = same result
- **Full update**: Replaces entire resource
- Requires ID in path

**Idempotent example:**
```java
// First request
PUT /api/departments/1
{ "name": "Computer Science", "code": "CS", "yearEstablished": 1995 }
// Result: Department updated

// Same request again
PUT /api/departments/1
{ "name": "Computer Science", "code": "CS", "yearEstablished": 1995 }
// Result: Same state (idempotent)
```

**PUT vs PATCH:**
```java
// PUT - full update (replace everything)
PUT /api/departments/1
{ "name": "CS", "code": "CS", "yearEstablished": 1995 }

// PATCH - partial update (change some fields)
PATCH /api/departments/1
{ "name": "CS" }  // Only update name
```

---

```java
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }
```

**KEYWORD:** `@DeleteMapping`
**TYPE:** Spring Annotation
**PURPOSE:** Handle HTTP DELETE requests

**KEYWORD:** `@ResponseStatus(HttpStatus.NO_CONTENT)`
**TYPE:** Spring Annotation
**PURPOSE:** Set response status to 204

**Why 204 No Content?**
- Successful deletion
- No data to return
- REST best practice for DELETE

**HTTP response:**
```
HTTP/1.1 204 No Content
(empty body)
```

**Why void return type?**
- Nothing to return
- Status code says everything
- Cleaner than returning null

---

```java
    @GetMapping("/{id}/professors")
    public DepartmentWithProfessorsResponseDTO getProfessorsByDepartment(@PathVariable Long id) {
        return departmentService.getDepartmentWithProfessors(id);
    }
```

**Aggregated endpoint:**

**Purpose:**
- Get department WITH all professors in one request
- Convenience for client
- Reduces number of requests

**Request:**
```
GET /api/departments/1/professors
```

**Response:**
```json
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995,
  "professors": [
    {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Smith",
      "title": "Full Professor"
    },
    {
      "id": 2,
      "firstName": "Bob",
      "lastName": "Johnson",
      "title": "Assistant Professor"
    }
  ]
}
```

**Why this endpoint?**
- **Alternative**: Make 2 requests
  1. GET /api/departments/1 (get department)
  2. GET /api/professors?departmentId=1 (get professors)
- **This way**: One request, all data
- Better user experience
- Less network overhead

---

## ProfessorController - Similar Structure

Key differences:

```java
    @PostMapping
    public ResponseEntity<ProfessorResponseModel> create(
        @Valid @RequestBody ProfessorRequestModel req
    ) {
        var created = professorService.create(req);

        return ResponseEntity
                .created(URI.create("/api/professors/" + created.getId()))
                .body(created);
    }
```

**Same pattern as DepartmentController:**
1. Validate input (@Valid)
2. Call service
3. Return 201 Created with Location header

---

# 10. Utilities - Exception Handling

## NotFoundException - DETAILED BREAKDOWN

**File:** `utilities/NotFoundException.java`

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
```

**KEYWORD:** `extends RuntimeException`
**PURPOSE:** Make this a runtime exception

**Exception hierarchy:**
```
Throwable
  ├─ Error (JVM errors, don't catch)
  └─ Exception
      ├─ RuntimeException (unchecked)
      │   ├─ NullPointerException
      │   ├─ IllegalArgumentException
      │   └─ NotFoundException ← Our exception
      └─ IOException (checked)
```

**Checked vs Unchecked:**
```java
// Checked - must handle
public void readFile() throws IOException { }
// Must catch or declare throws

// Unchecked - optional handling
public void divide() {
    throw new RuntimeException();
}
// Don't need to declare or catch
```

**Why RuntimeException?**
- Don't need try/catch everywhere
- Spring handles it globally
- Cleaner code

---

**KEYWORD:** `@ResponseStatus(HttpStatus.NOT_FOUND)`
**PURPOSE:** Default HTTP status when thrown

**What it does:**
```java
// When this exception is thrown
throw new NotFoundException("Department", 1);

// Spring returns:
HTTP/1.1 404 Not Found
{
  "timestamp": "...",
  "status": 404,
  "error": "Not Found",
  "message": "Department with ID 1 not found."
}
```

---

```java
    public NotFoundException(String resourceName, Long id) {
        super(String.format("%s with ID %d not found.", resourceName, id));
    }

    public NotFoundException(String message) {
        super(message);
    }
```

**Constructor overloading:**

**Use case 1: Resource + ID**
```java
throw new NotFoundException("Department", 1);
// Message: "Department with ID 1 not found."
```

**Use case 2: Custom message**
```java
throw new NotFoundException("Custom error message");
// Message: "Custom error message"
```

**String.format explained:**
```java
String.format("%s with ID %d not found.", "Department", 1)
               ↑           ↑
               |           └─ %d = integer (1)
               └─ %s = string ("Department")

// Result: "Department with ID 1 not found."
```

**Format specifiers:**
```java
%s = String
%d = decimal (integer)
%f = float
%b = boolean

String.format("Name: %s, Age: %d", "John", 25)
// "Name: John, Age: 25"
```

---

## ErrorResponse - DETAILED BREAKDOWN

**File:** `utilities/ErrorResponse.java`

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> validationErrors;
```

**Purpose:**
- Standard error format
- Consistent across all errors
- Easy for clients to parse

**Example error response:**
```json
{
  "timestamp": "2025-10-25T14:30:55.175",
  "status": 404,
  "error": "Not Found",
  "message": "Department with ID 999 not found.",
  "path": "/api/departments/999",
  "validationErrors": null
}
```

**Validation error example:**
```json
{
  "timestamp": "2025-10-25T14:30:55.175",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields",
  "path": "/api/departments",
  "validationErrors": {
    "name": "Department name is required.",
    "code": "Department code is required."
  }
}
```

---

**KEYWORD:** `LocalDateTime`
**TYPE:** Java 8 Date/Time API
**PURPOSE:** Represent timestamp

**Why LocalDateTime?**
```java
// Old way - Date class (deprecated)
Date date = new Date();

// New way - LocalDateTime
LocalDateTime now = LocalDateTime.now();

// Better because:
// - Immutable (thread-safe)
// - Clear API
// - ISO-8601 format
```

---

**KEYWORD:** `Map<String, String>`
**TYPE:** Collection
**PURPOSE:** Store validation errors

**What is a Map?**
- Key-value pairs
- Like a dictionary
- Fast lookup by key

**Example:**
```java
Map<String, String> errors = new HashMap<>();
errors.put("name", "Name is required");
errors.put("email", "Email is invalid");

// Converts to JSON:
{
  "name": "Name is required",
  "email": "Email is invalid"
}
```

---

## GlobalExceptionHandler - DETAILED BREAKDOWN

**File:** `utilities/GlobalExceptionHandler.java`

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
```

**KEYWORD:** `@RestControllerAdvice`
**TYPE:** Spring Annotation
**PURPOSE:** Global exception handling

**What it does:**
- Catches exceptions from ALL controllers
- Converts exceptions to HTTP responses
- One place for error handling

**Without @ControllerAdvice:**
```java
// Each controller handles errors
@RestController
public class DepartmentController {
    @GetMapping("/{id}")
    public Department get(@PathVariable Long id) {
        try {
            return service.findById(id);
        } catch (NotFoundException e) {
            // Handle error
        }
    }
}
// Repeated in every controller!
```

**With @ControllerAdvice:**
```java
// Handle once, applies everywhere
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException ex) {
        // Handle all NotFoundExceptions from all controllers
    }
}
```

---

```java
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
```

**KEYWORD:** `@ExceptionHandler(NotFoundException.class)`
**TYPE:** Spring Annotation
**PURPOSE:** Handle specific exception type

**How it works:**
```
1. Controller throws NotFoundException
2. Spring catches it
3. Spring finds @ExceptionHandler for NotFoundException
4. Calls this method
5. Returns error response to client
```

---

**KEYWORD:** `HttpServletRequest request`
**TYPE:** Parameter injection
**PURPOSE:** Access request information

**What you can get:**
```java
request.getRequestURI()    // /api/departments/999
request.getMethod()        // GET, POST, PUT, DELETE
request.getHeader("...")   // Get header value
request.getRemoteAddr()    // Client IP address
```

**Why include in error response?**
- Helps client debug
- Shows which endpoint failed
- Better error messages

---

```java
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Validation failed for one or more fields",
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
```

**What is MethodArgumentNotValidException?**
- Thrown when @Valid fails
- Contains all validation errors
- Need to extract and format them

**Extracting validation errors:**

```java
// Get all errors
ex.getBindingResult().getAllErrors()

// For each error
.forEach((error) -> {
    // Get field name (e.g., "name", "email")
    String fieldName = ((FieldError) error).getField();

    // Get error message (e.g., "Name is required")
    String errorMessage = error.getDefaultMessage();

    // Store in map
    validationErrors.put(fieldName, errorMessage);
});
```

**Result:**
```json
{
  "timestamp": "2025-10-25T14:30:55.175",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields",
  "path": "/api/departments",
  "validationErrors": {
    "name": "Department name is required.",
    "code": "Department code is required.",
    "yearEstablished": "Year established must be 1800 or later."
  }
}
```

---

```java
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String message = "Data integrity violation";

        if (ex.getMessage().contains("Unique") || ex.getMessage().contains("unique") ||
            ex.getMessage().contains("constraint")) {
            message = "A resource with this unique value already exists (e.g., duplicate email)";
        }

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
```

**What is DataIntegrityViolationException?**
- Database constraint violation
- Unique constraint (duplicate)
- Foreign key constraint
- Not null constraint

**Examples:**
```java
// Unique constraint
// Trying to save professor with existing email
// Exception: Unique index or primary key violation

// Foreign key constraint
// Trying to delete department with professors
// Exception: Referential integrity constraint violation

// Not null constraint
// Trying to save professor without department
// Exception: NOT NULL check constraint violation
```

**Why check message content?**
- Different databases use different messages
- Try to provide meaningful error
- "unique" in message → duplicate data
- Return user-friendly message

---

```java
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred: " + ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
```

**Catch-all handler:**

**Purpose:**
- Handle any unexpected exception
- Prevent app from crashing
- Return 500 Internal Server Error
- Last resort

**Exception handler order:**
```java
1. Most specific first
   @ExceptionHandler(NotFoundException.class)

2. More general
   @ExceptionHandler(RuntimeException.class)

3. Most general last (catch-all)
   @ExceptionHandler(Exception.class)
```

**Spring matches most specific handler first**

---

# 11. Configuration Layer

## CorsConfig - DETAILED BREAKDOWN

**File:** `config/CorsConfig.java`

```java
@Configuration
public class CorsConfig {
```

**KEYWORD:** `@Configuration`
**TYPE:** Spring Annotation
**PURPOSE:** Configuration class

**What @Configuration does:**
- Contains @Bean methods
- Configuration for application
- Loaded at startup
- Alternative to XML configuration

---

```java
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
```

**KEYWORD:** `@Bean`
**TYPE:** Spring Annotation
**PURPOSE:** Create Spring-managed bean

**What is a Bean?**
- Object managed by Spring
- Created once, reused
- Available for dependency injection

**Bean lifecycle:**
```java
1. Spring starts
2. Finds @Bean methods
3. Calls method, gets object
4. Stores object in container
5. Injects where needed
```

---

**What is CORS?**

**CORS** = Cross-Origin Resource Sharing

**The Problem:**
```
Frontend: http://localhost:3000
Backend:  http://localhost:8080

Browser blocks requests between different origins (security)
```

**Without CORS:**
```javascript
// Frontend tries to call backend
fetch('http://localhost:8080/api/departments')
// ❌ Browser blocks: "CORS policy: No 'Access-Control-Allow-Origin' header"
```

**With CORS:**
```javascript
// Frontend calls backend
fetch('http://localhost:8080/api/departments')
// ✅ Works! CORS headers allow it
```

---

**CORS configuration explained:**

```java
registry.addMapping("/api/**")
```
**Meaning:** Apply CORS to all `/api/` endpoints
**Pattern:** `**` = match any path

```java
.allowedOrigins("http://localhost:3000", "http://localhost:3001")
```
**Meaning:** Allow requests from these origins only
**Why these?** React dev server runs on port 3000/3001

```java
.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
```
**Meaning:** Allow these HTTP methods
**OPTIONS:** Browser sends this before actual request (preflight)

```java
.allowedHeaders("*")
```
**Meaning:** Allow any headers in request
**Alternative:** Specify headers: `"Content-Type", "Authorization"`

```java
.allowCredentials(true)
```
**Meaning:** Allow cookies/auth credentials
**Use case:** Sessions, authentication tokens

---

**Preflight request explained:**

**When browser makes cross-origin request:**
```
1. Browser sends OPTIONS request (preflight)
   OPTIONS /api/departments
   Origin: http://localhost:3000

2. Backend responds with CORS headers
   Access-Control-Allow-Origin: http://localhost:3000
   Access-Control-Allow-Methods: GET, POST, PUT, DELETE

3. If allowed, browser sends actual request
   GET /api/departments
```

**Why preflight?**
- Security check
- Verify server allows request
- Prevent unauthorized cross-origin requests

---

# 12. Application Entry Point

## UniversityDepartmentSystemApplication - DETAILED BREAKDOWN

**File:** `UniversityDepartmentSystemApplication.java`

```java
@SpringBootApplication
public class UniversityDepartmentSystemApplication {
```

**KEYWORD:** `@SpringBootApplication`
**TYPE:** Spring Boot Annotation
**PURPOSE:** Main application configuration

**What @SpringBootApplication includes:**
```java
@SpringBootApplication =
    @Configuration +        // This is a configuration class
    @EnableAutoConfiguration + // Auto-configure Spring Boot
    @ComponentScan          // Scan for @Component, @Service, etc.
```

**Auto-configuration magic:**
- Detects H2 on classpath → Configure H2 database
- Detects Spring MVC → Configure web server
- Detects JPA → Configure Hibernate
- No XML needed!

---

```java
    public static void main(String[] args) {
        SpringApplication.run(UniversityDepartmentSystemApplication.class, args);
    }
```

**Entry point:**
- Java application starts here
- `main` method is required
- Starts Spring Boot

**What SpringApplication.run does:**
```
1. Create Spring context (container)
2. Scan for components (@Component, @Service, etc.)
3. Create beans
4. Configure auto-configuration
5. Start embedded Tomcat server
6. Application ready!
```

---

```java
    @Bean
    CommandLineRunner runner(DepartmentRepository departmentRepository,
                             ProfessorRepository professorRepository) {
        return args -> {
            // Data seeding code
        };
    }
```

**KEYWORD:** `CommandLineRunner`
**TYPE:** Spring Interface
**PURPOSE:** Run code after application starts

**When it runs:**
```
1. Spring Boot starts
2. All beans created
3. Database initialized
4. CommandLineRunner executes
5. Application ready for requests
```

**Why CommandLineRunner?**
- Run initialization code
- Seed database
- One-time setup tasks
- Happens automatically

---

**Lambda expression:**
```java
return args -> {
    // Code here
};
```

**This is shorthand for:**
```java
return new CommandLineRunner() {
    @Override
    public void run(String... args) {
        // Code here
    }
};
```

**Lambda syntax:**
```java
(parameters) -> { body }

// Examples:
() -> System.out.println("Hello")       // No parameters
x -> x * 2                              // One parameter
(x, y) -> x + y                         // Multiple parameters
```

---

**Data seeding:**

```java
Department deptCS = new Department("Computer Science", "CS", 1995);
Department deptHistory = new Department("History and Politics", "HIST", 1980);
// ... more departments

departmentRepository.saveAll(Arrays.asList(deptCS, deptHistory, ...));
```

**Why Arrays.asList?**
```java
// Instead of:
repo.save(dept1);
repo.save(dept2);
repo.save(dept3);
// Many database calls

// Use:
repo.saveAll(Arrays.asList(dept1, dept2, dept3));
// One database call (batch insert)
// Much faster!
```

---

**Why seed data?**

**Without seeding:**
- Empty database on startup
- Manual data entry
- Inconsistent test data

**With seeding:**
- Consistent starting data
- Easy to demo
- Quick development
- Automated testing

---

# 13. Frontend - React Application

## React Basics

**What is React?**
- JavaScript library for UI
- Component-based
- Declarative (describe what you want)
- Virtual DOM for performance

**Why React?**
- Reusable components
- Fast updates
- Large ecosystem
- Popular and well-supported

---

## Project Structure

```
frontend/
├── public/
│   └── index.html           ← HTML template
├── src/
│   ├── components/
│   │   ├── department/      ← Department components
│   │   │   ├── DepartmentList.js
│   │   │   ├── DepartmentDetail.js
│   │   │   └── DepartmentForm.js
│   │   ├── professor/       ← Professor components
│   │   │   ├── ProfessorList.js
│   │   │   ├── ProfessorDetail.js
│   │   │   └── ProfessorForm.js
│   │   └── Home.js
│   ├── services/
│   │   └── api.js           ← API calls
│   ├── App.js               ← Main app
│   ├── App.css              ← Styles
│   └── index.js             ← Entry point
└── package.json             ← Dependencies
```

---

## Key React Concepts

### 1. Components

```javascript
// Component = Reusable piece of UI
function DepartmentList() {
    return (
        <div>
            <h1>Departments</h1>
            {/* Component content */}
        </div>
    );
}
```

### 2. Props (Properties)

```javascript
// Pass data to components
function Department({ name, code }) {
    return <div>{code}: {name}</div>;
}

// Usage:
<Department name="Computer Science" code="CS" />
```

### 3. State (useState)

```javascript
// State = data that changes
const [departments, setDepartments] = useState([]);

// Update state
setDepartments([...newDepartments]);
```

### 4. Effects (useEffect)

```javascript
// Run code after component renders
useEffect(() => {
    fetchDepartments();
}, []); // Empty array = run once on mount
```

### 5. Routing (React Router)

```javascript
<Routes>
    <Route path="/departments" element={<DepartmentList />} />
    <Route path="/departments/:id" element={<DepartmentDetail />} />
</Routes>
```

---

## API Service (api.js)

```javascript
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const departmentApi = {
  getAll: () => axios.get(`${API_BASE_URL}/departments`),
  getById: (id) => axios.get(`${API_BASE_URL}/departments/${id}`),
  create: (data) => axios.post(`${API_BASE_URL}/departments`, data),
  update: (id, data) => axios.put(`${API_BASE_URL}/departments/${id}`, data),
  delete: (id) => axios.delete(`${API_BASE_URL}/departments/${id}`),
};
```

**What is axios?**
- HTTP client library
- Easier than fetch
- Automatic JSON parsing
- Interceptors support

**axios methods:**
```javascript
// GET
axios.get('/url') → Promise<response>

// POST
axios.post('/url', data) → Promise<response>

// Response structure:
{
  data: { }, // Response body
  status: 200,
  statusText: 'OK',
  headers: { }
}
```

---

## Component Example: DepartmentList

```javascript
function DepartmentList() {
  // State
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch on mount
  useEffect(() => {
    fetchDepartments();
  }, []);

  // API call
  const fetchDepartments = async () => {
    try {
      setLoading(true);
      const response = await departmentApi.getAll();
      setDepartments(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch departments');
    } finally {
      setLoading(false);
    }
  };

  // Render
  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Departments</h1>
      <table>
        {departments.map((dept) => (
          <tr key={dept.id}>
            <td>{dept.code}</td>
            <td>{dept.name}</td>
          </tr>
        ))}
      </table>
    </div>
  );
}
```

**Key parts:**

1. **State management:**
```javascript
const [departments, setDepartments] = useState([]);
// departments = current value
// setDepartments = function to update
```

2. **Effect hook:**
```javascript
useEffect(() => {
  fetchDepartments();
}, []); // [] = run once on mount
```

3. **Async/await:**
```javascript
const fetchDepartments = async () => {
  const response = await departmentApi.getAll();
  // Wait for API response
};
```

4. **Conditional rendering:**
```javascript
if (loading) return <div>Loading...</div>;
if (error) return <div>{error}</div>;
```

5. **Map array to elements:**
```javascript
{departments.map((dept) => (
  <tr key={dept.id}>
    <td>{dept.code}</td>
  </tr>
))}
```

---

# 14. Key Java & Spring Boot Concepts

## Annotations Summary

| Annotation | Layer | Purpose |
|------------|-------|---------|
| `@Entity` | Data | Database table |
| `@Id` | Data | Primary key |
| `@GeneratedValue` | Data | Auto-generate ID |
| `@Column` | Data | Column configuration |
| `@OneToMany` | Data | Relationship (one-to-many) |
| `@ManyToOne` | Data | Relationship (many-to-one) |
| `@JoinColumn` | Data | Foreign key |
| `@Repository` | Data | Repository bean |
| `@Service` | Business | Service bean |
| `@Transactional` | Business | Transaction management |
| `@RestController` | Presentation | REST controller |
| `@RequestMapping` | Presentation | Base URL path |
| `@GetMapping` | Presentation | Handle GET |
| `@PostMapping` | Presentation | Handle POST |
| `@PutMapping` | Presentation | Handle PUT |
| `@DeleteMapping` | Presentation | Handle DELETE |
| `@PathVariable` | Presentation | Extract from URL |
| `@RequestBody` | Presentation | Parse request body |
| `@Valid` | Presentation | Trigger validation |
| `@RestControllerAdvice` | Utilities | Global exception handler |
| `@ExceptionHandler` | Utilities | Handle specific exception |
| `@Configuration` | Config | Configuration class |
| `@Bean` | Config | Create Spring bean |
| `@Component` | Any | Generic Spring bean |

---

## Lombok Annotations

| Annotation | Purpose | Generates |
|------------|---------|-----------|
| `@Getter` | Generate getters | `getField()` for all fields |
| `@Setter` | Generate setters | `setField(value)` for all fields |
| `@NoArgsConstructor` | Empty constructor | `new Class()` |
| `@AllArgsConstructor` | Full constructor | `new Class(field1, field2, ...)` |
| `@RequiredArgsConstructor` | Constructor for final fields | Constructor for `final` fields |

---

## Validation Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@NotNull` | Cannot be null | `@NotNull Long id` |
| `@NotBlank` | Cannot be null/empty/spaces | `@NotBlank String name` |
| `@NotEmpty` | Cannot be null/empty | `@NotEmpty List list` |
| `@Email` | Valid email format | `@Email String email` |
| `@Min(value)` | Minimum value | `@Min(1800) Integer year` |
| `@Max(value)` | Maximum value | `@Max(2100) Integer year` |
| `@Positive` | Must be positive | `@Positive Long id` |
| `@Size(min, max)` | String/collection size | `@Size(min=2, max=50) String name` |

---

## JPA Cascade Types

| Type | Effect |
|------|--------|
| `PERSIST` | Save child when parent is saved |
| `MERGE` | Update child when parent is updated |
| `REMOVE` | Delete child when parent is deleted |
| `REFRESH` | Reload child when parent is reloaded |
| `DETACH` | Detach child when parent is detached |
| `ALL` | All of the above |

---

## JPA Fetch Types

| Type | When Loads | Use For |
|------|------------|---------|
| `EAGER` | Immediately | Single objects (@ManyToOne) |
| `LAZY` | On demand | Collections (@OneToMany) |

---

# 15. HTTP and REST Concepts

## HTTP Methods

| Method | Purpose | Idempotent | Safe |
|--------|---------|------------|------|
| GET | Retrieve data | Yes | Yes |
| POST | Create new resource | No | No |
| PUT | Update/replace resource | Yes | No |
| DELETE | Delete resource | Yes | No |
| PATCH | Partial update | No | No |

**Idempotent** = Same request multiple times = same result
**Safe** = Doesn't modify data

---

## HTTP Status Codes

| Code | Meaning | When Used |
|------|---------|-----------|
| 200 OK | Success | GET, PUT successful |
| 201 Created | Resource created | POST successful |
| 204 No Content | Success, no data | DELETE successful |
| 400 Bad Request | Invalid input | Validation failed |
| 404 Not Found | Resource not found | Invalid ID |
| 409 Conflict | Duplicate resource | Unique constraint |
| 500 Internal Server Error | Server error | Unexpected error |

---

## REST Endpoint Patterns

```
Collection operations:
GET    /api/departments              (Get all)
POST   /api/departments              (Create)

Resource operations:
GET    /api/departments/{id}         (Get one)
PUT    /api/departments/{id}         (Update)
DELETE /api/departments/{id}         (Delete)

Relationship operations:
GET    /api/departments/{id}/professors  (Get related)
```

---

## JSON Format

**Request (POST/PUT):**
```json
{
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}
```

**Error Response:**
```json
{
  "timestamp": "2025-10-25T14:30:55.175",
  "status": 404,
  "error": "Not Found",
  "message": "Department with ID 999 not found.",
  "path": "/api/departments/999"
}
```

---

# 16. Study Questions & Answers

## Architecture Questions

**Q: What is 3-layer architecture?**
A: Presentation (Controllers) → Business Logic (Services) → Data Access (Repositories)

**Q: Why use layers?**
A: Separation of concerns, easier testing, easier maintenance, reusability

**Q: What layer handles HTTP?**
A: Presentation layer (Controllers)

**Q: What layer has business rules?**
A: Business Logic layer (Services)

**Q: What layer talks to database?**
A: Data Access layer (Repositories)

---

## Entity Questions

**Q: What is an Entity?**
A: Java class that represents a database table

**Q: What does @Entity do?**
A: Tells JPA to create a database table for this class

**Q: What is @Id for?**
A: Marks the primary key field

**Q: What does @GeneratedValue do?**
A: Database automatically generates the ID

**Q: What is @OneToMany?**
A: One entity has many of another (e.g., one department has many professors)

**Q: What is @ManyToOne?**
A: Many entities belong to one (e.g., many professors belong to one department)

**Q: When to use LAZY vs EAGER?**
A: LAZY for collections, EAGER for single objects

---

## DTO Questions

**Q: What is a DTO?**
A: Data Transfer Object - carries data between layers

**Q: Why use DTOs instead of entities?**
A: Hide internal structure, add validation, security, flexibility

**Q: What's the difference between RequestDTO and ResponseDTO?**
A: RequestDTO = input (no ID), ResponseDTO = output (has ID)

**Q: What is a Summary DTO?**
A: Minimal data for nested objects (prevents circular references)

---

## Validation Questions

**Q: What does @Valid do?**
A: Triggers validation on the object

**Q: What is @NotBlank?**
A: Field cannot be null, empty, or only spaces

**Q: When does validation happen?**
A: When @Valid is on controller parameter, before method executes

**Q: What error code for validation failure?**
A: 400 Bad Request

---

## Service Questions

**Q: What does @Service do?**
A: Marks class as a Spring-managed service bean

**Q: What does @Transactional do?**
A: Manages database transactions (all succeed or all fail)

**Q: Why use Optional?**
A: Prevents NullPointerException, forces handling of "not found" case

**Q: What does orElseThrow do?**
A: If Optional is empty, throw exception; otherwise return value

---

## Controller Questions

**Q: What does @RestController do?**
A: Marks class as REST API controller, returns data (not views)

**Q: What does @GetMapping do?**
A: Handles HTTP GET requests

**Q: What does @PostMapping do?**
A: Handles HTTP POST requests (create)

**Q: What does @PathVariable do?**
A: Extracts value from URL path

**Q: What does @RequestBody do?**
A: Parses HTTP request body to object

**Q: What is ResponseEntity?**
A: Full control over HTTP response (status, headers, body)

---

## Exception Handling Questions

**Q: What does @RestControllerAdvice do?**
A: Global exception handling for all controllers

**Q: What does @ExceptionHandler do?**
A: Handles specific exception type

**Q: What error code for not found?**
A: 404 Not Found

**Q: What error code for validation error?**
A: 400 Bad Request

**Q: What error code for duplicate?**
A: 409 Conflict

---

## Database Questions

**Q: What is H2?**
A: In-memory database, good for development

**Q: What does create-drop do?**
A: Drop and recreate database on each startup

**Q: What is CommandLineRunner?**
A: Runs code after application starts (for seeding data)

**Q: Why seed data?**
A: Consistent starting data, easy demo, quick development

---

## React Questions

**Q: What is a component?**
A: Reusable piece of UI

**Q: What is state?**
A: Data that changes over time

**Q: What is useState?**
A: Hook to add state to component

**Q: What is useEffect?**
A: Hook to run side effects (API calls, etc.)

**Q: What is axios?**
A: HTTP client library for making API calls

---

## REST Questions

**Q: What is REST?**
A: Architectural style for web services

**Q: What is idempotent?**
A: Same request multiple times = same result

**Q: Which methods are idempotent?**
A: GET, PUT, DELETE (not POST)

**Q: What is CORS?**
A: Cross-Origin Resource Sharing - allows different origins to communicate

**Q: Why return 201 for POST?**
A: Indicates resource was created successfully

**Q: Why return 204 for DELETE?**
A: Indicates success with no content to return

---

## Spring Boot Questions

**Q: What does @SpringBootApplication do?**
A: Main configuration: @Configuration + @EnableAutoConfiguration + @ComponentScan

**Q: What is dependency injection?**
A: Spring creates and injects dependencies automatically

**Q: What is a bean?**
A: Object managed by Spring container

**Q: What does @Autowired do?**
A: Inject dependency (often not needed with constructor injection)

**Q: What is component scanning?**
A: Spring automatically finds and creates beans with @Component, @Service, etc.

---

## How Data Flows

### Creating a Department

```
1. User fills form in React
   ↓
2. Frontend sends POST /api/departments
   {
     "name": "Computer Science",
     "code": "CS",
     "yearEstablished": 1995
   }
   ↓
3. Controller receives request
   - @PostMapping catches it
   - @RequestBody converts JSON to DepartmentRequestModel
   - @Valid validates the data
   ↓
4. Controller calls Service
   service.create(requestModel)
   ↓
5. Service converts DTO to Entity
   mapper.toEntity(requestModel)
   ↓
6. Service saves to database
   repository.save(entity)
   ↓
7. Database generates ID
   ↓
8. Service converts Entity to Response DTO
   mapper.toResponseModel(savedEntity)
   ↓
9. Controller returns ResponseEntity
   - Status: 201 Created
   - Location header: /api/departments/1
   - Body: DepartmentResponseModel
   ↓
10. Frontend receives response
   {
     "id": 1,
     "name": "Computer Science",
     "code": "CS",
     "yearEstablished": 1995
   }
   ↓
11. React updates UI
```

---

### Getting Department with Professors

```
1. User clicks "View Department"
   ↓
2. Frontend sends GET /api/departments/1/professors
   ↓
3. Controller receives request
   - @GetMapping("/{id}/professors") catches it
   - @PathVariable extracts id = 1
   ↓
4. Controller calls Service
   service.getDepartmentWithProfessors(1)
   ↓
5. Service opens transaction (@Transactional)
   ↓
6. Service queries database
   repository.findById(1)
   ↓
7. JPA loads Department
   - Loads department fields
   - Professors collection is LAZY
   ↓
8. Service accesses professors
   department.getProfessors()
   - Still in transaction, so loads professors now
   ↓
9. Service converts to aggregated DTO
   - Maps Department fields
   - Maps each Professor to ProfessorSummary
   - Returns DepartmentWithProfessorsResponseDTO
   ↓
10. Transaction commits
    ↓
11. Controller returns response
    - Status: 200 OK
    - Body: Aggregated DTO as JSON
    ↓
12. Frontend receives response
    {
      "id": 1,
      "name": "Computer Science",
      "code": "CS",
      "yearEstablished": 1995,
      "professors": [
        {
          "id": 1,
          "firstName": "Alice",
          "lastName": "Smith",
          "title": "Full Professor"
        },
        {
          "id": 2,
          "firstName": "Bob",
          "lastName": "Johnson",
          "title": "Assistant Professor"
        }
      ]
    }
    ↓
13. React displays department with professor list
```

---

## Complete Request-Response Cycle

```
┌─────────────┐
│   Browser   │
│  (React)    │
└──────┬──────┘
       │ HTTP Request
       │ GET /api/departments/1
       ↓
┌──────────────────────────┐
│    Spring Boot App       │
│                          │
│  ┌────────────────────┐  │
│  │ Controller         │  │
│  │ @GetMapping("/{id}")│ │
│  │ getById(id)        │  │
│  └────────┬───────────┘  │
│           │              │
│           ↓              │
│  ┌────────────────────┐  │
│  │ Service            │  │
│  │ findById(id)       │  │
│  └────────┬───────────┘  │
│           │              │
│           ↓              │
│  ┌────────────────────┐  │
│  │ Repository         │  │
│  │ findById(id)       │  │
│  └────────┬───────────┘  │
│           │              │
│           ↓              │
│  ┌────────────────────┐  │
│  │ H2 Database        │  │
│  │ SELECT * FROM ...  │  │
│  └────────┬───────────┘  │
│           │              │
│           │ Department   │
│           ↓              │
│  ┌────────────────────┐  │
│  │ Mapper             │  │
│  │ toResponseModel()  │  │
│  └────────┬───────────┘  │
│           │              │
│           │ DTO          │
│           ↓              │
│  ┌────────────────────┐  │
│  │ Controller         │  │
│  │ return DTO         │  │
│  └────────┬───────────┘  │
│           │              │
└───────────┼──────────────┘
            │ HTTP Response
            │ JSON
            ↓
       ┌──────────────┐
       │   Browser    │
       │  (React)     │
       │  Updates UI  │
       └──────────────┘
```

---

## Summary: Everything Connected

**Your application is like a restaurant:**

1. **Frontend (React)** = Customer
   - Makes requests
   - Displays data

2. **Controller** = Waiter
   - Takes orders (HTTP requests)
   - Brings food (HTTP responses)

3. **Service** = Chef
   - Implements recipes (business logic)
   - Prepares food (data processing)

4. **Repository** = Pantry
   - Stores ingredients (data)
   - Retrieves what's needed

5. **Database** = Storage Room
   - Long-term storage
   - Organized shelves (tables)

6. **Mapper** = Translator
   - Converts between formats
   - Entity ↔ DTO

7. **Exception Handler** = Manager
   - Handles problems
   - Provides solutions

**Data Flow:**
```
Customer → Waiter → Chef → Pantry → Storage
                            ↓
React → Controller → Service → Repository → Database
```

---

**You now have a complete understanding of your code!** 🎓

Every annotation, every keyword, every concept is explained in detail. Use this guide to study and refer back to whenever you need clarification.

Good luck with your presentation! 🍀
