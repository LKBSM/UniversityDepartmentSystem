# Quick Start Guide - University Department System

This guide will help you get both the backend (Spring Boot) and frontend (React) running.

## Prerequisites

- Java 17 or higher
- Node.js 14+ and npm
- Your favorite IDE (IntelliJ IDEA, VS Code, etc.)

## Step 1: Start the Backend

### Option A: Using your IDE (IntelliJ IDEA)
1. Open the project in IntelliJ IDEA
2. Navigate to `src/main/java/com/champsoft/universitydepartmentsystem/UniversityDepartmentSystemApplication.java`
3. Right-click and select "Run"
4. Wait for the application to start (it should run on http://localhost:8080)

### Option B: Using Command Line
```bash
# From the project root directory
./gradlew bootRun

# Or on Windows
gradlew.bat bootRun
```

### Verify Backend is Running
- Open http://localhost:8080/h2-console in your browser
- Use these credentials:
  - JDBC URL: `jdbc:h2:mem:universitydb`
  - Username: `sa`
  - Password: (leave empty)
- You should see tables: `DEPARTMENT` and `PROFESSOR` with seeded data

## Step 2: Start the Frontend

1. Open a new terminal/command prompt
2. Navigate to the frontend directory:
```bash
cd frontend
```

3. Install dependencies (first time only):
```bash
npm install
```

4. Start the React development server:
```bash
npm start
```

5. The browser should automatically open to http://localhost:3000

## Step 3: Use the Application

Once both servers are running:

1. **Home Page** - http://localhost:3000
   - Navigate to Departments or Professors

2. **Departments Page** - http://localhost:3000/departments
   - View all departments
   - Create new department
   - Edit or delete existing departments
   - Click "View" to see department details with associated professors

3. **Professors Page** - http://localhost:3000/professors
   - View all professors
   - Create new professor
   - Edit or delete existing professors
   - Click "View" to see professor details

## Testing the API with Postman

If you want to test the API directly:

### Department Endpoints
```
GET    http://localhost:8080/api/departments
GET    http://localhost:8080/api/departments/1
GET    http://localhost:8080/api/departments/1/professors
POST   http://localhost:8080/api/departments
PUT    http://localhost:8080/api/departments/1
DELETE http://localhost:8080/api/departments/1
```

### Professor Endpoints
```
GET    http://localhost:8080/api/professors
GET    http://localhost:8080/api/professors/1
POST   http://localhost:8080/api/professors
PUT    http://localhost:8080/api/professors/1
DELETE http://localhost:8080/api/professors/1
```

## Sample POST Request Bodies

### Create Department
```json
{
  "name": "Mathematics",
  "code": "MATH",
  "yearEstablished": 2000
}
```

### Create Professor
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@uni.ca",
  "title": "Associate Professor",
  "departmentId": 1
}
```

## Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find what's using port 8080
netstat -ano | findstr :8080

# Kill the process (Windows)
taskkill /PID <process_id> /F
```

**H2 Database not accessible:**
- Make sure `spring.h2.console.enabled=true` in `application.properties`
- Restart the Spring Boot application

### Frontend Issues

**Port 3000 already in use:**
- The React app will ask if you want to use a different port (e.g., 3001)
- Type 'Y' to accept

**Cannot connect to backend:**
- Verify Spring Boot is running on port 8080
- Check CORS configuration in backend
- Clear browser cache

**npm install fails:**
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and package-lock.json
rm -rf node_modules package-lock.json

# Reinstall
npm install
```

## Project Ports

- **Backend (Spring Boot)**: http://localhost:8080
- **Frontend (React)**: http://localhost:3000
- **H2 Console**: http://localhost:8080/h2-console

## Notes

- The H2 database is in-memory, so data will be lost when you restart the backend
- The backend automatically seeds 10 departments and 12 professors on startup
- CORS is configured to allow requests from localhost:3000 and localhost:3001
- All API endpoints follow REST conventions with proper HTTP status codes
