🚀 Smart Staff Rotation & Scheduling System

A backend scheduling system that automatically manages staff shift rotations while enforcing real-world constraints like leave management, skill requirements, and conflict detection.

This project demonstrates real-world backend architecture using Spring Boot, REST APIs, scheduling algorithms, and database relationships.

📌 Project Goal

Organizations often struggle with fair staff scheduling.
This system automatically:

Generates monthly shift rotations

Ensures fairness in scheduling

Prevents shift conflicts

Handles staff leave requests

Supports manual admin overrides

Example use cases:

Hospitals

Customer support teams

IT operations teams

Retail staff scheduling

🧰 Tech Stack
Backend

Java 17

Spring Boot

Spring Data JPA

Hibernate

Database

PostgreSQL

Tools

Maven

Postman

Git

GitHub

🏗 System Architecture
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Database (PostgreSQL)
Project Structure
src/main/java
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── exception
 ├── enums
 └── config

This follows clean layered architecture used in production backend systems.

🗄 Database Design

Main tables:

Table	Description
Staff	Staff information
Department	Staff department
Skills	Skills required for shifts
Shift	Shift types and timings
StaffShiftAssignments	Staff assigned to shifts
RotationSchedule	Monthly generated schedule
LeaveRequests	Staff leave requests

Relationships are managed using JPA/Hibernate.

⚙ Core Features
👨‍💼 Staff Management

Create staff members

Update staff details

Activate / deactivate staff

Assign skills

⏰ Shift Management

Create shifts such as:

Shift	Example Time
Day	09:00 - 18:00
Evening	14:00 - 23:00
Night	18:00 - 03:00
🔄 Automatic Rotation Algorithm

The system rotates staff across shifts automatically.

Example:

Week 1

A → Morning
B → Evening
C → Night

Week 2

A → Evening
B → Night
C → Morning

This ensures fair distribution of shifts.

🟢 Availability System

Staff can mark availability:

AVAILABLE
LEAVE

Staff on LEAVE cannot be assigned shifts.

🧠 Skill-Based Assignment

Example rule:

Night Shift requires AWS skill

The system validates staff skills before assigning the shift.

🏖 Leave Management

Leave statuses:

PENDING
APPROVED
REJECTED

Approved leave prevents shift assignment.

👨‍💻 Admin Features
Generate Next Month Schedule
POST /api/admin/schedule/generate-next-month

Automatically generates shift rotation.

Regenerate Schedule
POST /api/admin/rotation/regenerate

Rebuilds schedule if staff availability changes.

Manual Override
PUT /api/admin/rotation/override

Admin can manually change shift assignments.

⚠ Conflict Detection

Detects scheduling problems such as:

Duplicate assignments

Staff assigned during leave

Understaffed shifts

API:

GET /api/admin/schedule/conflicts

Example response:

{
  "duplicateAssignments": 0,
  "staffOnLeaveAssigned": 0,
  "understaffedShifts": 0
}
📤 Export Schedule

Export the full schedule as CSV.

GET /api/admin/schedule/export/csv

Example output:

Staff Name,Shift Type,Shift Date
Rohit,DAY,2026-03-01
Anjali,NIGHT,2026-03-01
Suresh,DAY,2026-03-02
📡 API Overview
Method	Endpoint	Description
POST	/api/staff	Create staff
GET	/api/staff	List staff
POST	/api/shifts	Create shift
POST	/api/admin/schedule/generate-next-month	Auto schedule
PUT	/api/admin/rotation/override	Manual override
GET	/api/admin/schedule/conflicts	Detect conflicts
GET	/api/admin/schedule/export/csv	Export schedule
🧪 API Testing

All APIs were tested using Postman.

Base URL:

http://localhost:8080/api
▶ How to Run the Project
Clone Repository
git clone https://github.com/YOUR_USERNAME/staff-scheduling-system-backend.git
Navigate to Project
cd staff-scheduling-system-backend
Run Application
mvn spring-boot:run

Server will start at:

http://localhost:8080
🔮 Future Improvements

React Admin Dashboard

Calendar schedule view

Export schedule as Excel / PDF

Role-based access control

Real-time notifications

👨‍💻 Author

Prapitesh

Backend Developer
Java | Spring Boot | REST API | PostgreSQL

⭐ If you like this project

Give the repository a star ⭐ on GitHub.
