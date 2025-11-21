# University Department System

A full-stack web application for managing university departments and professors, built with Spring Boot and React.

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Database Access](#database-access)
- [Project Structure](#project-structure)
- [Team](#team)

## Project Description

This application implements a RESTful web service following a clean 3-layer architecture (Controller → Service → Repository) with DTOs and an in-memory H2 database. It manages two core resources with a one-to-many relationship:

- **Departments**: Academic departments within the university
- **Professors**: Faculty members assigned to departments

The system provides complete CRUD operations, aggregated views, comprehensive validation, and a user-friendly React frontend.

## Features

### Backend Features
- RESTful API with proper HTTP status codes
- 3-layer architecture (Presentation, Business Logic, Data Access)
- Complete CRUD operations for departments and professors
- One-to-many relationship (Department → Professors)
- Request and Response DTOs
- Aggregated endpoint (Department with Professors)
- Bean validation with detailed error messages
- Global exception handling (@RestControllerAdvice)
- H2 in-memory database
- Automatic data seeding (10 departments, 12 professors)
- CORS configuration for frontend

### Frontend Features
- Modern React UI with React Router
- Complete CRUD operations for both resources
- Department-Professor relationship management
- Form validation and error handling
- Responsive design
- Intuitive navigation

## Technology Stack

### Backend
- **Java**: 17
- **Spring Boot**: 3.x
- **Spring Data JPA**: Database access
- **H2 Database**: In-memory database
- **Hibernate Validator**: Bean validation
- **Lombok**: Boilerplate reduction
- **Gradle**: Build tool

### Frontend
- **React**: 18
- **React Router DOM**: v6
- **Axios**: HTTP client
- **CSS3**: Styling

## Architecture

### 3-Layer Architecture

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (Controllers - REST Endpoints)       │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│       Business Logic Layer              │
│    (Services - Business Rules)          │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         Data Access Layer               │
│    (Repositories - Database)            │
└─────────────────────────────────────────┘
```

### Package Structure
```
com.champsoft.universitydepartmentsystem
├── BuisnessLogicLayer/       # Services
├── DataLayer/                 # Entities & Repositories
├── PresentationLayer/         # REST Controllers
├── DTO/                       # Data Transfer Objects
├── MapperLayer/              # Entity-DTO Mappers
├── utilities/                # Exceptions & Utilities
└── config/                   # Configuration Classes
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 14+ and npm
- Git (optional)

### Running the Backend

#### Option 1: Using Gradle (Command Line)
```bash
# Navigate to project directory
cd UniversityDepartmentSystem

# Run the application
./gradlew bootRun

# On Windows
gradlew.bat bootRun
```

#### Option 2: Using IDE (IntelliJ IDEA)
1. Open project in IntelliJ IDEA
2. Navigate to `src/main/java/.../UniversityDepartmentSystemApplication.java`
3. Right-click → Run

The backend will start on **http://localhost:8080**

### Running the Frontend

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies (first time only)
npm install

# Start development server
npm start
```

The frontend will start on **http://localhost:3000**

### Verify Installation

1. **Backend**: Open http://localhost:8080/api/departments in your browser
2. **Frontend**: Open http://localhost:3000 in your browser
3. **H2 Console**: Open http://localhost:8080/h2-console

## API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Department Endpoints

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/departments` | Get all departments | 200 OK |
| GET | `/departments/{id}` | Get department by ID | 200 OK, 404 Not Found |
| GET | `/departments/{id}/professors` | Get department with professors (aggregated) | 200 OK, 404 Not Found |
| POST | `/departments` | Create new department | 201 Created, 400 Bad Request |
| PUT | `/departments/{id}` | Update department | 200 OK, 404 Not Found, 400 Bad Request |
| DELETE | `/departments/{id}` | Delete department | 204 No Content, 404 Not Found |

### Professor Endpoints

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/professors` | Get all professors | 200 OK |
| GET | `/professors/{id}` | Get professor by ID | 200 OK, 404 Not Found |
| POST | `/professors` | Create new professor | 201 Created, 400 Bad Request, 409 Conflict |
| PUT | `/professors/{id}` | Update professor | 200 OK, 404 Not Found, 400 Bad Request, 409 Conflict |
| DELETE | `/professors/{id}` | Delete professor | 204 No Content, 404 Not Found |

## Sample Requests

### Create Department
```bash
curl -X POST http://localhost:8080/api/departments \
    -H "Content-Type: application/json" \
    -d '{
        "name": "Computer Science",
        "code": "CS",
        "yearEstablished": 1995
    }'
```

### Create Professor
```bash
curl -X POST http://localhost:8080/api/professors \
    -H "Content-Type: application/json" \
    -d '{
        "firstName": "John",
        "lastName": "Smith",
        "email": "john.smith@uni.ca",
        "title": "Full Professor",
        "departmentId": 1
    }'
```

### Get Department with Professors
```bash
curl http://localhost:8080/api/departments/1/professors
```

## Database Access

### H2 Console
Access the H2 database console for data inspection:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:universitydb`
- **Username**: `sa`
- **Password**: (leave empty)
- **Driver Class**: `org.h2.Driver`

### Seeded Data

The application automatically seeds the database on startup with:

**10 Departments:**
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

**12 Professors** distributed across various departments

## Project Structure

```
UniversityDepartmentSystem/
├── src/
│   └── main/
│       ├── java/com/champsoft/universitydepartmentsystem/
│       │   ├── BuisnessLogicLayer/
│       │   │   ├── DepartmentService.java
│       │   │   └── ProfessorService.java
│       │   ├── DataLayer/
│       │   │   ├── Department.java
│       │   │   ├── DepartmentRepository.java
│       │   │   ├── Professor.java
│       │   │   └── ProfessorRepository.java
│       │   ├── PresentationLayer/
│       │   │   ├── DepartmentController.java
│       │   │   └── ProfessorController.java
│       │   ├── DTO/
│       │   │   ├── DepartmentRequestModel.java
│       │   │   ├── DepartmentResponseModel.java
│       │   │   ├── DepartmentSummary.java
│       │   │   ├── DepartmentWithProfessorsResponseDTO.java
│       │   │   ├── ProfessorRequestModel.java
│       │   │   ├── ProfessorResponseModel.java
│       │   │   └── ProfessorSummary.java
│       │   ├── MapperLayer/
│       │   │   ├── DepartmentMapper.java
│       │   │   └── ProfessorMapper.java
│       │   ├── utilities/
│       │   │   ├── ErrorResponse.java
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── NotFoundException.java
│       │   ├── config/
│       │   │   └── CorsConfig.java
│       │   └── UniversityDepartmentSystemApplication.java
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── components/
│   │   │   ├── department/
│   │   │   │   ├── DepartmentList.js
│   │   │   │   ├── DepartmentDetail.js
│   │   │   │   └── DepartmentForm.js
│   │   │   ├── professor/
│   │   │   │   ├── ProfessorList.js
│   │   │   │   ├── ProfessorDetail.js
│   │   │   │   └── ProfessorForm.js
│   │   │   └── Home.js
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── App.js
│   │   ├── App.css
│   │   ├── index.js
│   │   └── index.css
│   ├── package.json
│   └── README.md
├── build.gradle
├── settings.gradle
└── README.md (this file)
```

## Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (Windows)
taskkill /PID <process_id> /F
```

**H2 Console not accessible:**
- Ensure backend is running
- Check `spring.h2.console.enabled=true` in application.properties
- Use correct JDBC URL: `jdbc:h2:mem:universitydb`

### Frontend Issues

**Cannot connect to backend:**
- Verify backend is running on port 8080
- Check CORS configuration
- Clear browser cache

**npm install fails:**
```bash
# Clear cache and reinstall
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

## Team

- **Team Members**: Luca Kissimba, Mikael Bruno
- **Course**: 420-N34_LA Java Web Programming
- **Institution**: Champlain College Saint-Lambert
- **Professor**: Haikel Hichri
- **Term**: Fall 2025

## License

This project is created for academic purposes as part of the Java Web Programming course.

## Acknowledgments

- Professor Haikel Hichri for project guidance
- Champlain College Saint-Lambert Computer Science Department
- Spring Boot and React communities for excellent documentation

---

**Last Updated**: November 2025

**Status**: Milestone 1 Complete
