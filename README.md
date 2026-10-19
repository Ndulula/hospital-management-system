README.md
markdown
# 🏥 Hospital Management System

A full-stack hospital management platform for managing patients, doctors, appointments, and billing.

**Tech Stack:** Spring Boot · React · PostgreSQL · JWT

---

## Features

- JWT authentication with role-based access control (Admin, Doctor, Nurse, Receptionist)
- Patient, doctor, and department management
- Appointment booking with conflict detection
- Automated billing with tax calculation
- Dashboard with real-time charts
- Notifications for upcoming appointments and pending bills

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Node.js 18+
- PostgreSQL

### Backend

```bash
psql -U postgres -c "CREATE DATABASE hospital_db;"
mvn spring-boot:run
Backend: http://localhost:8080/api

Frontend
bash
cd frontend
cp .env.example .env
npm install
npm start
Frontend: http://localhost:3000

Default Login
Username	Password
admin	admin123
Environment Variables
Variable	Description
DB_URL	PostgreSQL connection URL
DB_USERNAME	Database username
DB_PASSWORD	Database password
JWT_SECRET	Secret key for signing JWT tokens
PORT	Backend port (default 8080)
See .env.example for reference.

Project Structure
text
├── src/main/java/       # Spring Boot backend
├── frontend/            # React frontend
├── .env.example
└── README.md
Author
Ndulula — GitHub
