# University Department System - Project Report

**Course:** 420-N34_LA Java Web Programming
**Institution:** Champlain College Saint-Lambert
**Professor:** Haikel Hichri
**Term:** Fall 2025
**Project:** Milestone 1 - Backend in Spring Boot

---

## Table of Contents
1. [Introduction](#1-introduction)
2. [Design Section](#2-design-section)
3. [Non-Functional Considerations](#3-non-functional-considerations)
4. [Implementation](#4-implementation)
5. [API Documentation](#5-api-documentation)
6. [Testing](#6-testing)
7. [Conclusion](#7-conclusion)

---

## 1. Introduction

### System Purpose
The University Department System is a web-based application designed to manage university departments and their associated professors. The system enables administrators to efficiently organize academic departments, assign professors to departments, and maintain comprehensive records of both entities.

### Primary Users
- **University Administrators**: Manage departments and professor assignments
- **Department Heads**: View and manage professors within their departments
- **System Administrators**: Maintain data integrity and system operations

### High-Level Features
- Complete CRUD operations for departments and professors
- Department-professor relationship management (one-to-many)
- Aggregated views showing departments with their professors
- Data validation and error handling
- RESTful API following industry best practices
- Responsive web interface for easy access

---

## 2. Design Section

### 2.1 Resources

#### Resource 1: Department
The Department entity represents an academic department within the university.

**Fields:**
- `id` (Long): Auto-generated primary key
- `name` (String): Department name (required, unique)
- `code` (String): Department code (required, unique, e.g., "CS", "MATH")
- `yearEstablished` (Integer): Year the department was founded (optional)
- `professors` (Set<Professor>): Collection of professors in the department

**Constraints:**
- Name and Code must be unique
- Name and Code cannot be blank
- Year established must be between 1800 and 2100

#### Resource 2: Professor
The Professor entity represents a faculty member assigned to a department.

**Fields:**
- `id` (Long): Auto-generated primary key
- `firstName` (String): Professor's first name (required)
- `lastName` (String): Professor's last name (required)
- `email` (String): Email address (required, unique)
- `title` (String): Academic title (optional, e.g., "Full Professor")
- `department` (Department): Associated department (required)

**Constraints:**
- Email must be unique and well-formed
- First name and last name cannot be blank
- Department ID must be valid and positive

### 2.2 DTOs (Data Transfer Objects)

#### Department DTOs
1. **DepartmentRequestModel** (Input)
   - name (String, required)
   - code (String, required)
   - yearEstablished (Integer, optional)

2. **DepartmentResponseModel** (Output)
   - id (Long)
   - name (String)
   - code (String)
   - yearEstablished (Integer)

3. **DepartmentSummary** (Nested)
   - id (Long)
   - name (String)
   - code (String)

4. **DepartmentWithProfessorsResponseDTO** (Aggregated)
   - id (Long)
   - name (String)
   - code (String)
   - yearEstablished (Integer)
   - professors (List<ProfessorSummary>)

#### Professor DTOs
1. **ProfessorRequestModel** (Input)
   - firstName (String, required)
   - lastName (String, required)
   - email (String, required, valid email)
   - title (String, optional)
   - departmentId (Long, required, positive)

2. **ProfessorResponseModel** (Output)
   - id (Long)
   - firstName (String)
   - lastName (String)
   - email (String)
   - title (String)
   - departmentId (Long)
   - department (DepartmentSummary)

3. **ProfessorSummary** (Nested)
   - id (Long)
   - firstName (String)
   - lastName (String)
   - title (String)

### 2.3 Relationships

**Relationship Type:** One-to-Many (Department → Professor)

**Description:**
- One department can have many professors
- Each professor belongs to exactly one department
- Relationship is mandatory on the professor side

**JPA Mapping:**
```java
// In Department entity
@OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
private Set<Professor> professors = new HashSet<>();

// In Professor entity
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "department_id", nullable = false)
private Department department;
```

**Cascade Strategy:**
- `CascadeType.PERSIST`: When a new department with professors is saved, professors are saved automatically
- No cascade delete to prevent accidental data loss

**Fetch Strategy:**
- Department → Professors: LAZY (loaded only when accessed)
- Professor → Department: EAGER (always loaded with professor)

**Database Schema:**
```
DEPARTMENT Table:
- id (PK, BIGINT, AUTO_INCREMENT)
- name (VARCHAR, UNIQUE, NOT NULL)
- code (VARCHAR, UNIQUE, NOT NULL)
- year_established (INTEGER)

PROFESSOR Table:
- id (PK, BIGINT, AUTO_INCREMENT)
- first_name (VARCHAR, NOT NULL)
- last_name (VARCHAR, NOT NULL)
- email (VARCHAR, UNIQUE, NOT NULL)
- title (VARCHAR)
- department_id (FK → DEPARTMENT.id, NOT NULL)
```

### 2.4 API Endpoints

#### Department Endpoints

| Method | Endpoint | Description | Request Body | Response | Status Codes |
|--------|----------|-------------|--------------|----------|--------------|
| GET | `/api/departments` | Get all departments | - | List<DepartmentResponseModel> | 200 OK |
| GET | `/api/departments/{id}` | Get department by ID | - | DepartmentResponseModel | 200 OK, 404 Not Found |
| GET | `/api/departments/{id}/professors` | Get department with professors | - | DepartmentWithProfessorsResponseDTO | 200 OK, 404 Not Found |
| POST | `/api/departments` | Create new department | DepartmentRequestModel | DepartmentResponseModel | 201 Created, 400 Bad Request |
| PUT | `/api/departments/{id}` | Update department | DepartmentRequestModel | DepartmentResponseModel | 200 OK, 404 Not Found, 400 Bad Request |
| DELETE | `/api/departments/{id}` | Delete department | - | - | 204 No Content, 404 Not Found |

#### Professor Endpoints

| Method | Endpoint | Description | Request Body | Response | Status Codes |
|--------|----------|-------------|--------------|----------|--------------|
| GET | `/api/professors` | Get all professors | - | List<ProfessorResponseModel> | 200 OK |
| GET | `/api/professors/{id}` | Get professor by ID | - | ProfessorResponseModel | 200 OK, 404 Not Found |
| POST | `/api/professors` | Create new professor | ProfessorRequestModel | ProfessorResponseModel | 201 Created, 400 Bad Request, 409 Conflict |
| PUT | `/api/professors/{id}` | Update professor | ProfessorRequestModel | ProfessorResponseModel | 200 OK, 404 Not Found, 400 Bad Request, 409 Conflict |
| DELETE | `/api/professors/{id}` | Delete professor | - | - | 204 No Content, 404 Not Found |

#### Example Requests and Responses

**POST /api/departments**
```json
Request:
{
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}

Response (201 Created):
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995
}
Headers:
Location: /api/departments/1
```

**POST /api/professors**
```json
Request:
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@uni.ca",
  "title": "Full Professor",
  "departmentId": 1
}

Response (201 Created):
{
  "id": 1,
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@uni.ca",
  "title": "Full Professor",
  "departmentId": 1,
  "department": {
    "id": 1,
    "name": "Computer Science",
    "code": "CS"
  }
}
```

**GET /api/departments/1/professors**
```json
Response (200 OK):
{
  "id": 1,
  "name": "Computer Science",
  "code": "CS",
  "yearEstablished": 1995,
  "professors": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Smith",
      "title": "Full Professor"
    },
    {
      "id": 2,
      "firstName": "Jane",
      "lastName": "Doe",
      "title": "Associate Professor"
    }
  ]
}
```

**Error Response (404 Not Found)**
```json
{
  "timestamp": "2025-10-25T14:30:55.175+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Department with ID 999 not found.",
  "path": "/api/departments/999"
}
```

**Validation Error Response (400 Bad Request)**
```json
{
  "timestamp": "2025-10-25T14:30:55.175+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields",
  "path": "/api/professors",
  "validationErrors": {
    "email": "Email must be a well-formed email address.",
    "firstName": "First name is required."
  }
}
```

### 2.5 Validation Rules

#### Department Validation
- **name**: Required, not blank
- **code**: Required, not blank, must be unique
- **yearEstablished**: Optional, if provided must be between 1800 and 2100

#### Professor Validation
- **firstName**: Required, not blank
- **lastName**: Required, not blank
- **email**: Required, not blank, must be well-formed email, must be unique
- **title**: Optional
- **departmentId**: Required, must be positive number, must reference existing department

**Expected Error Responses:**
- 400 Bad Request: When validation fails (invalid email, missing required fields, etc.)
- 404 Not Found: When referenced department doesn't exist
- 409 Conflict: When attempting to create duplicate email

---

## 3. Non-Functional Considerations

### 3.1 Error Handling Approach

**Global Exception Handler (@RestControllerAdvice)**
The application implements centralized error handling using Spring's `@RestControllerAdvice`:

1. **NotFoundException** → 404 Not Found
   - Thrown when a resource (department or professor) is not found
   - Returns consistent error response with message

2. **MethodArgumentNotValidException** → 400 Bad Request
   - Thrown when request DTO validation fails
   - Returns field-level validation errors

3. **DataIntegrityViolationException** → 409 Conflict
   - Thrown when unique constraints are violated (duplicate email/code)
   - Returns descriptive error message

4. **Generic Exception** → 500 Internal Server Error
   - Catches unexpected errors
   - Logs error details for debugging

**Error Response Format:**
All errors follow a consistent JSON structure:
```json
{
  "timestamp": "ISO-8601 timestamp",
  "status": "HTTP status code",
  "error": "HTTP status text",
  "message": "Human-readable error message",
  "path": "Request URI",
  "validationErrors": {} // Only for validation errors
}
```

### 3.2 Naming and Packaging Conventions

**Package Structure (Layered Approach):**
```
com.champsoft.universitydepartmentsystem
├── BuisnessLogicLayer/       # Service classes
├── DataLayer/                 # Entities and Repositories
├── PresentationLayer/         # Controllers
├── DTO/                       # Data Transfer Objects
├── MapperLayer/              # Entity-DTO mappers
├── utilities/                # Exceptions, helpers
└── config/                   # Configuration classes
```

**Naming Conventions:**
- **Entities**: PascalCase, singular (Department, Professor)
- **DTOs**: Descriptive suffixes (DepartmentRequestModel, ProfessorResponseModel)
- **Controllers**: PascalCase with "Controller" suffix (DepartmentController)
- **Services**: PascalCase with "Service" suffix (DepartmentService)
- **Repositories**: PascalCase with "Repository" suffix (DepartmentRepository)
- **Fields**: camelCase (firstName, yearEstablished)
- **Constants**: UPPER_SNAKE_CASE
- **Methods**: camelCase, verb-based (findById, create, update)

### 3.3 Architecture Quality

**3-Layer Architecture:**
1. **Presentation Layer (Controllers)**
   - Handle HTTP requests/responses
   - Input validation using `@Valid`
   - Return appropriate HTTP status codes
   - Use ResponseEntity for explicit response control

2. **Business Logic Layer (Services)**
   - Implement business rules
   - Handle transactions using `@Transactional`
   - Throw custom exceptions for error cases
   - Coordinate between repositories and mappers

3. **Data Access Layer (Repositories)**
   - Extend Spring Data JPA interfaces
   - Provide database access
   - No business logic

**Benefits:**
- Separation of concerns
- Testability
- Maintainability
- Scalability

---

## 4. Implementation

### 4.1 Technology Stack

**Backend:**
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 In-Memory Database
- Hibernate Validator
- Lombok

**Frontend:**
- React 18
- React Router DOM v6
- Axios
- CSS3

**Development Tools:**
- Gradle
- Git

### 4.2 Database Configuration

**H2 Database Settings (application.properties):**
```properties
spring.datasource.url=jdbc:h2:mem:universitydb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.defer-datasource-initialization=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

**H2 Console Access:**
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:universitydb`
- Username: `sa`
- Password: (empty)

### 4.3 Data Seeding

The application uses a `@Bean CommandLineRunner` in the main application class to seed the database on startup:

**Seeded Data:**
- **10 Departments:**
  1. Computer Science (CS) - 1995
  2. History and Politics (HIST) - 1980
  3. Physics and Mathematics (PHYS) - 2000
  4. Biology (BIO) - 1975
  5. Chemistry (CHEM) - 1990
  6. English Literature (ENG) - 1965
  7. Fine Arts (ART) - 2005
  8. Economics (ECON) - 1985
  9. Law and Justice (LAW) - 2010
  10. Philosophy (PHIL) - 1970

- **12 Professors** distributed across departments with:
  - Unique emails (@uni.ca domain)
  - Various academic titles (Full Professor, Associate Professor, Assistant Professor, Lecturer)
  - Proper department assignments

**Seeding Code Location:**
`UniversityDepartmentSystemApplication.java` - CommandLineRunner bean

### 4.4 Key Implementation Details

**Mapper Pattern:**
- Manual mapping between entities and DTOs
- Prevents exposing internal structure
- Allows field transformations
- Located in MapperLayer package

**Transaction Management:**
- `@Transactional` on service methods that modify data
- Ensures data consistency
- Automatic rollback on exceptions

**CORS Configuration:**
- Enabled for frontend communication
- Allows origins: localhost:3000, localhost:3001
- Supports all HTTP methods
- Located in config package

---

## 5. API Documentation

### 5.1 Base URL
- **Local Development**: `http://localhost:8080/api`
- **Deployment**: (To be added)

### 5.2 Testing with Postman/Insomnia

A Postman collection is included in the repository with pre-configured requests for all endpoints.

**Import Instructions:**
1. Open Postman
2. Click "Import"
3. Select the collection file from the repository
4. Set base URL variable if needed

### 5.3 HTTP Status Codes Summary

| Status Code | Meaning | When Used |
|-------------|---------|-----------|
| 200 OK | Success | GET, PUT successful |
| 201 Created | Resource created | POST successful |
| 204 No Content | Success, no body | DELETE successful |
| 400 Bad Request | Invalid input | Validation failure |
| 404 Not Found | Resource not found | Invalid ID |
| 409 Conflict | Duplicate resource | Unique constraint violation |
| 500 Internal Server Error | Server error | Unexpected errors |

---

## 6. Testing

### 6.1 Manual Testing Performed

**Department CRUD Operations:**
- ✅ Create department with valid data
- ✅ Create department with invalid data (validation)
- ✅ Get all departments
- ✅ Get department by ID
- ✅ Get department by invalid ID (404)
- ✅ Update department
- ✅ Delete department
- ✅ Get department with professors (aggregated)

**Professor CRUD Operations:**
- ✅ Create professor with valid data
- ✅ Create professor with invalid email (validation)
- ✅ Create professor with duplicate email (409)
- ✅ Create professor with invalid department ID (404)
- ✅ Get all professors
- ✅ Get professor by ID
- ✅ Update professor
- ✅ Delete professor

**Relationship Testing:**
- ✅ Assign professor to department
- ✅ View professors in a department
- ✅ Update professor's department
- ✅ Verify department summary in professor response

**Frontend Testing:**
- ✅ All pages load correctly
- ✅ Navigation works
- ✅ CRUD operations through UI
- ✅ Form validation works
- ✅ Error messages display
- ✅ Department-professor relationship displays

### 6.2 Test Data Verification

**H2 Console Queries:**
```sql
-- Verify departments
SELECT * FROM DEPARTMENT;

-- Verify professors
SELECT * FROM PROFESSOR;

-- Verify relationship
SELECT d.name, d.code, COUNT(p.id) as professor_count
FROM DEPARTMENT d
LEFT JOIN PROFESSOR p ON d.id = p.department_id
GROUP BY d.id, d.name, d.code;
```

---

## 7. Conclusion

### 7.1 Achievements

This project successfully implements a complete RESTful web service for managing university departments and professors with the following accomplishments:

1. **Architecture (R1)**: Clean 3-layer architecture with proper separation of concerns
2. **Resources (R2, R3)**: Two entities with one-to-many relationship
3. **CRUD Operations (R6)**: Complete CRUD for both resources
4. **DTOs (R8)**: Request and response DTOs for all operations
5. **Aggregated DTOs (R7)**: Department with professors view
6. **Validation (R57)**: Comprehensive input validation with proper error responses
7. **Error Handling (R11)**: Custom exceptions with global handler
8. **Database (R5)**: H2 in-memory database properly configured
9. **Data Seeding (R13)**: 10+ records per table on startup
10. **Status Codes (R10)**: Proper HTTP status codes for all operations
11. **Frontend**: Complete React application with all CRUD operations

### 7.2 Technical Highlights

- **RESTful Design**: Follows REST principles with resource-oriented URLs
- **Data Integrity**: Proper constraints and validation at multiple layers
- **User Experience**: Clean, responsive frontend with intuitive navigation
- **Error Handling**: Consistent error responses with helpful messages
- **Code Quality**: Well-organized, documented, and follows naming conventions

### 7.3 Future Enhancements (Optional)

- Deploy to cloud platform (Render, Railway, or Heroku)
- Add unit and integration tests
- Implement Swagger/OpenAPI documentation
- Add pagination for large datasets
- Implement search and filtering capabilities
- Add authentication and authorization
- Implement audit logging

### 7.4 Repository Information

**Git Repository**: [Your GitHub URL]
**Branch**: main
**Commits**: Multiple commits with clear messages showing progression

---

## Appendix: How to Run the Application

### Prerequisites
- Java 17 or higher
- Node.js 14+ and npm
- Git

### Backend Setup
```bash
# Clone repository
git clone [your-repo-url]
cd UniversityDepartmentSystem

# Run Spring Boot application
./gradlew bootRun

# Or on Windows
gradlew.bat bootRun

# Backend will start on http://localhost:8080
```

### Frontend Setup
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start React development server
npm start

# Frontend will start on http://localhost:3000
```

### Access Points
- **Frontend Application**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **H2 Console**: http://localhost:8080/h2-console
- **API Example**: http://localhost:8080/api/departments

---

**Project Completion Date**: October 25, 2025
**Team Members**: [Your Names]
**Total Development Time**: [X hours]

---

**END OF REPORT**
