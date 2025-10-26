# Demo Video Script - University Department System
**Total Time: 4-6 minutes**

---

## 🎬 INTRODUCTION (30 seconds)

**What to say:**
> "Hello, this is a demonstration of the University Department System, a full-stack application built with Spring Boot and React. This system manages university departments and professors with full CRUD operations, aggregated endpoints, and proper error handling."

**What to show:**
- Your desktop with both IntelliJ and browser visible
- Project folder open

---

## 📂 PART 1: STARTING THE APPLICATION (1 minute)

### Step 1.1: Start the Backend (20 seconds)

**Where:** IntelliJ IDEA

**What to do:**
1. Show the project structure in IntelliJ (left sidebar)
2. Right-click on `UniversityDepartmentSystemApplication.java`
3. Click "Run 'UniversityDepartmentSystemApplication'"
4. Wait for console to show: `Started UniversityDepartmentSystemApplication`

**What to say:**
> "First, I'm starting the Spring Boot backend application. You can see it's running on port 8080 and the H2 database is initialized with seed data."

**What to point out in console:**
- "Started UniversityDepartmentSystemApplication in X seconds"
- "Data seeding complete. 10 departments and 12 professors saved."

---

### Step 1.2: Start the Frontend (20 seconds)

**Where:** Terminal/Command Prompt

**What to do:**
```bash
cd C:\Users\lucar\UniversityDepartmentSystem\frontend
npm start
```

**What to say:**
> "Now I'm starting the React frontend which runs on port 3000."

**What to show:**
- Browser automatically opening to `http://localhost:3000`
- Homepage with navigation bar visible

---

### Step 1.3: Show the Frontend UI (20 seconds)

**Where:** Browser at `http://localhost:3000`

**What to do:**
1. Show the home page
2. Click "Departments" in navigation
3. Click "Professors" in navigation
4. Return to home

**What to say:**
> "The frontend has a clean interface with navigation to view departments, professors, and perform all CRUD operations."

---

## 🔧 PART 2: DEMONSTRATE ENDPOINTS VIA POSTMAN (2-3 minutes)

**Where:** Postman

**What to say:**
> "Now I'll demonstrate the REST API endpoints using Postman, showing all CRUD operations and the aggregated DTOs."

---

### Step 2.1: GET All Departments (15 seconds)

**Request:**
- Method: `GET`
- URL: `http://localhost:8080/api/departments`

**What to show:**
- Click Send
- Show response with array of 10 departments
- Point out the JSON structure with `id`, `name`, `code`, `yearEstablished`

**What to say:**
> "This GET endpoint retrieves all departments. You can see we have 10 departments with proper DTOs."

---

### Step 2.2: GET Department by ID (15 seconds)

**Request:**
- Method: `GET`
- URL: `http://localhost:8080/api/departments/1`

**What to show:**
- Click Send
- Show single department response
- Point out Status: `200 OK`

**What to say:**
> "Here's a GET by ID endpoint returning a single department."

---

### Step 2.3: POST - Create New Department (20 seconds)

**Request:**
- Method: `POST`
- URL: `http://localhost:8080/api/departments`
- Body (raw JSON):
```json
{
    "name": "Music Department",
    "code": "MUSIC",
    "yearEstablished": 2015
}
```

**What to show:**
- Go to Body tab, select "raw" and "JSON"
- Paste the JSON
- Click Send
- Show response with Status: `201 Created`
- Point out the new ID assigned (should be 11)

**What to say:**
> "Creating a new department using POST. Notice the 201 Created status and the auto-generated ID."

---

### Step 2.4: PUT - Update Department (20 seconds)

**Request:**
- Method: `PUT`
- URL: `http://localhost:8080/api/departments/11`
- Body:
```json
{
    "name": "Music and Performing Arts",
    "code": "MUSIC",
    "yearEstablished": 2015
}
```

**What to show:**
- Change method to PUT
- Update the URL to `/11` (the ID you just created)
- Click Send
- Show Status: `200 OK`
- Point out the updated name in response

**What to say:**
> "Updating the department name using PUT. The response shows the updated data."

---

### Step 2.5: GET All Professors (15 seconds)

**Request:**
- Method: `GET`
- URL: `http://localhost:8080/api/professors`

**What to show:**
- Show array of 12 professors
- Point out each professor includes `department` nested object (aggregated DTO)

**What to say:**
> "This endpoint shows all professors, and notice each professor response includes their department information - this is our aggregated DTO."

---

### Step 2.6: GET Department with Professors (AGGREGATED ENDPOINT) (20 seconds)

**Request:**
- Method: `GET`
- URL: `http://localhost:8080/api/departments/1/professors`

**What to show:**
- Click Send
- Show the response with department AND all its professors
- Point out the nested structure

**What to say:**
> "This is our main aggregated endpoint - it returns a department with all its professors in a single response. This demonstrates the one-to-many relationship."

---

### Step 2.7: POST - Create New Professor (20 seconds)

**Request:**
- Method: `POST`
- URL: `http://localhost:8080/api/professors`
- Body:
```json
{
    "firstName": "Sarah",
    "lastName": "Connor",
    "email": "s.connor@uni.ca",
    "title": "Assistant Professor",
    "departmentId": 1
}
```

**What to show:**
- Click Send
- Show Status: `201 Created`
- Point out the response includes the department information

**What to say:**
> "Creating a new professor assigned to department 1. The response includes both professor data and their department."

---

### Step 2.8: DELETE - Delete Professor (15 seconds)

**Request:**
- Method: `DELETE`
- URL: `http://localhost:8080/api/professors/13`

**What to show:**
- Click Send
- Show Status: `204 No Content`
- Empty response body

**What to say:**
> "Deleting the professor we just created. Notice the 204 No Content status indicating successful deletion."

---

### Step 2.9: Validation Error (15 seconds)

**Request:**
- Method: `POST`
- URL: `http://localhost:8080/api/professors`
- Body (INVALID - missing required fields):
```json
{
    "firstName": "",
    "email": "invalid-email"
}
```

**What to show:**
- Click Send
- Show Status: `400 Bad Request`
- Show the `validationErrors` object with field-level errors

**What to say:**
> "Here's our validation in action. Invalid data returns 400 Bad Request with detailed field-level error messages."

---

### Step 2.10: 404 Not Found Error (15 seconds)

**Request:**
- Method: `GET`
- URL: `http://localhost:8080/api/departments/999`

**What to show:**
- Click Send
- Show Status: `404 Not Found`
- Show error message: "Department with id 999 not found"

**What to say:**
> "Our global exception handler catches not-found errors and returns proper 404 responses with descriptive messages."

---

## 💾 PART 3: H2 DATABASE CONSOLE (1 minute)

**Where:** Browser

**What to do:**

### Step 3.1: Access H2 Console (15 seconds)

1. Open new browser tab
2. Go to: `http://localhost:8080/h2-console`

**What to show:**
- H2 Console login page

**Login credentials:**
- JDBC URL: `jdbc:h2:mem:universitydb`
- User Name: `sa`
- Password: (leave blank)
- Click "Connect"

**What to say:**
> "The application uses H2 in-memory database. Let me show you the actual data in the database console."

---

### Step 3.2: Show Department Table (20 seconds)

**Where:** H2 Console

**What to do:**
1. In left sidebar, click on `DEPARTMENT` table
2. Run the query: `SELECT * FROM DEPARTMENT`
3. Show the results

**What to show:**
- All 11 departments (10 original + 1 you created)
- Point out the columns: ID, NAME, CODE, YEAR_ESTABLISHED

**What to say:**
> "Here's the Department table with all our data including the Music Department we just created."

---

### Step 3.3: Show Professor Table (20 seconds)

**What to do:**
1. Click on `PROFESSOR` table
2. Run: `SELECT * FROM PROFESSOR`
3. Show the results

**What to show:**
- All 12 professors
- Point out the `DEPARTMENT_ID` foreign key column

**What to say:**
> "The Professor table shows the one-to-many relationship through the department_id foreign key."

---

### Step 3.4: Show JOIN Query (Optional - 10 seconds)

**What to do:**
Run this query:
```sql
SELECT P.ID, P.FIRST_NAME, P.LAST_NAME, P.EMAIL, D.NAME AS DEPARTMENT_NAME
FROM PROFESSOR P
JOIN DEPARTMENT D ON P.DEPARTMENT_ID = D.ID
WHERE D.CODE = 'CS'
```

**What to say:**
> "Here's a SQL join showing all professors in the Computer Science department."

---

## 🌐 PART 4: FRONTEND DEMONSTRATION (1 minute)

**Where:** Browser at `http://localhost:3000`

### Step 4.1: View Departments (15 seconds)

**What to do:**
1. Click "Departments" in navbar
2. Show the list of departments
3. Click "View Details" on one department

**What to say:**
> "The React frontend provides a clean interface to interact with the API. Here's the department list."

---

### Step 4.2: View Department with Professors (15 seconds)

**What to show:**
- Department detail page showing the department info
- List of professors in that department
- "Add Professor to this Department" button

**What to say:**
> "This page demonstrates the aggregated endpoint - showing a department with all its professors."

---

### Step 4.3: Create a Professor (20 seconds)

**What to do:**
1. Click "Add Professor to this Department"
2. Fill in the form:
   - First Name: John
   - Last Name: Wick
   - Email: j.wick@uni.ca
   - Title: Full Professor
   - (Department should be pre-selected)
3. Click "Create Professor"
4. Show success and redirect to professor list

**What to say:**
> "Creating a new professor directly from the department page. Notice the department is pre-selected, and after creation we're redirected to the professor list."

---

### Step 4.4: Edit and Delete (10 seconds)

**What to do:**
1. Show "Edit" button on a professor
2. Show "Delete" button
3. (Don't actually delete, just point them out)

**What to say:**
> "All CRUD operations are available through the UI - view, create, edit, and delete."

---

## 🚀 PART 5: DEPLOYMENT (30 seconds - OPTIONAL)

**Note:** Only include this if you've actually deployed to Render/Railway

**What to show:**
1. Open the deployed URL (e.g., `https://your-app.onrender.com`)
2. Show it working live on the internet
3. Maybe do one quick operation (view departments)

**What to say:**
> "Finally, the application is deployed to [Render/Railway] and accessible at this public URL. Everything works the same as localhost."

**If NOT deployed yet:**

**What to say:**
> "The application is ready for deployment to services like Render or Railway, but for this demo I've shown it running locally."

---

## 🎬 CONCLUSION (15 seconds)

**What to say:**
> "In summary, this application demonstrates a complete Spring Boot REST API with proper 3-layer architecture, full CRUD operations, one-to-many relationships, aggregated DTOs, validation, error handling, and a React frontend. Thank you for watching."

**What to show:**
- Quick final view of the frontend homepage

---

## 📋 RECORDING CHECKLIST

Before you start recording, make sure:

- [ ] Backend is running (IntelliJ console shows "Started")
- [ ] Frontend is running (`npm start` in terminal)
- [ ] Browser is open to `http://localhost:3000`
- [ ] Postman is open with your collection ready
- [ ] H2 Console tab is open (or ready to open)
- [ ] Close unnecessary tabs/windows
- [ ] Close distracting notifications
- [ ] Have this script open on a second monitor or printed

---

## 🎥 RECORDING TIPS

1. **Use OBS Studio or similar screen recorder**
   - Download: https://obsproject.com/
   - Record at 1920x1080 resolution

2. **Speak clearly and at a moderate pace**
   - Don't rush
   - Pause between sections

3. **If you make a mistake:**
   - Just pause, restart that section
   - Edit it out later, or re-record

4. **Total time breakdown:**
   - Introduction: 30s
   - Starting app: 1 min
   - Postman demos: 2-3 min
   - H2 Console: 1 min
   - Frontend: 1 min
   - Conclusion: 15s
   - **Total: 4-6 minutes** ✅

5. **Upload to:**
   - YouTube (unlisted)
   - OneDrive/Google Drive
   - Loom
   - Any platform your professor accepts

---

## 🎯 KEY POINTS TO EMPHASIZE

✅ **3-Layer Architecture** - Controller → Service → Repository
✅ **DTOs** - Request and Response models
✅ **One-to-Many Relationship** - Department has many Professors
✅ **CRUD Operations** - All working for both entities
✅ **Aggregated Endpoint** - `/api/departments/{id}/professors`
✅ **Validation** - Bean validation with proper error responses
✅ **Exception Handling** - 404, 400, 409 status codes
✅ **H2 Database** - In-memory with seed data
✅ **React Frontend** - Complete UI with routing

---

## 📝 QUICK POSTMAN REQUESTS REFERENCE

### Departments

```
GET    http://localhost:8080/api/departments
GET    http://localhost:8080/api/departments/1
GET    http://localhost:8080/api/departments/1/professors (AGGREGATED)
POST   http://localhost:8080/api/departments
       Body: {"name": "Music Department", "code": "MUSIC", "yearEstablished": 2015}
PUT    http://localhost:8080/api/departments/11
       Body: {"name": "Music and Performing Arts", "code": "MUSIC", "yearEstablished": 2015}
DELETE http://localhost:8080/api/departments/10
```

### Professors

```
GET    http://localhost:8080/api/professors
GET    http://localhost:8080/api/professors/1
POST   http://localhost:8080/api/professors
       Body: {"firstName": "Sarah", "lastName": "Connor", "email": "s.connor@uni.ca", "title": "Assistant Professor", "departmentId": 1}
PUT    http://localhost:8080/api/professors/13
       Body: {"firstName": "Sarah", "lastName": "Connor", "email": "s.connor@uni.ca", "title": "Full Professor", "departmentId": 1}
DELETE http://localhost:8080/api/professors/13
```

### Error Demos

```
GET    http://localhost:8080/api/departments/999  (404 Not Found)
POST   http://localhost:8080/api/professors        (400 Validation Error)
       Body: {"firstName": "", "email": "invalid"}
```

---

## ✅ GOOD LUCK!

**Remember:** You don't need to be perfect. Just show that everything works and explain what you're doing. Professors want to see that YOU understand the code and that it functions properly.

**Estimated recording time:** 15-20 minutes (including retakes)
**Final video length:** 4-6 minutes

You got this! 🎓🚀
