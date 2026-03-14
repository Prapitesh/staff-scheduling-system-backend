Smart Staff Rotation & Scheduling System
Project Overview

The Smart Staff Rotation & Scheduling System is a backend application built using Spring Boot that manages staff scheduling, shift assignments, and automatic rotation of staff across shifts.

The system ensures fair shift distribution, conflict detection, and automated scheduling, making it useful for organizations like hospitals, IT support teams, call centers, and retail operations.

This project demonstrates real-world backend development concepts such as REST APIs, scheduling algorithms, database relationships, and business rule validation.

Tech Stack
Backend

Java

Spring Boot

Spring Data JPA

Hibernate

Database

PostgreSQL

Tools

Maven

Postman (API Testing)

GitHub

System Features
Staff Management

Create staff members

Update staff information

Activate / deactivate staff

Assign department and skills

Shift Management

Create shifts (Day / Evening / Night)

Define shift timings

Assign shifts to staff

Staff Shift Assignment

Assign shifts to staff on specific dates

Prevent multiple shifts for the same staff on the same day

Track shift history

Automatic Rotation Algorithm

The system automatically generates a rotation schedule.

Example rotation:

Week 1

Staff A → Morning
Staff B → Evening
Staff C → Night

Week 2

Staff A → Evening
Staff B → Night
Staff C → Morning

This ensures fair distribution of shifts among staff.

Availability System

Staff members can set their availability:

AVAILABLE

LEAVE

Staff marked as LEAVE cannot be assigned a shift.

Skill-Based Assignment

Some shifts require specific skills.

Example:

Night Shift requires AWS skill

The system validates staff skills before assigning shifts.

Leave Management

Staff can request leave.

Leave statuses:

PENDING

APPROVED

REJECTED

Approved leave prevents shift assignment during the leave period.

Admin Features
Generate Next Month Schedule

Admin can automatically generate shift schedules for the upcoming month.

API Example:

POST /api/admin/schedule/generate-next-month
Regenerate Rotation

Admin can regenerate the schedule if conflicts occur.

Manual Override

Admin can manually change the staff assigned to a shift.

API Example:

PUT /api/admin/rotation/override
Conflict Detection

The system detects scheduling issues such as:

Duplicate shift assignments

Staff assigned during approved leave

Understaffed shifts

API:

GET /api/admin/schedule/conflicts

Example Response:

{
  "duplicateAssignments": 0,
  "staffOnLeaveAssigned": 0,
  "understaffedShifts": 0
}
Schedule Export

The system allows exporting the schedule in CSV format.

API:

GET /api/admin/schedule/export/csv

Example Output:

Staff Name,Shift Type,Shift Date
Rohit,DAY,2026-03-01
Anjali,NIGHT,2026-03-01
Suresh,DAY,2026-03-02
Database Design

Main tables used in the system:

Staff

Department

Skills

Shift

StaffShiftAssignments

RotationSchedule

LeaveRequests

The system uses JPA relationships to manage these entities.

API Testing

All APIs were tested using Postman.

Example base URL:

http://localhost:8080/api
Project Structure
controller
service
repository
entity
dto
exception
enums
config

This follows clean layered architecture commonly used in Spring Boot applications.

How to Run the Project
Clone the Repository
git clone https://github.com/your-username/staff-scheduling-system-backend.git
Navigate to Project
cd staff-scheduling-system-backend
Run the Application
mvn spring-boot:run

Server will start at:

http://localhost:8080
Future Improvements

Frontend Admin Dashboard (React)

Export schedule as Excel or PDF

Real-time notifications

Role-based access control

Schedule visualization using calendar UI

Author

Prapitesh

Backend Developer
Spring Boot | Java | REST API Development
