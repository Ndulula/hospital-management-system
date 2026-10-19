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
# Create database
psql -U postgres -c "CREATE DATABASE hospital_db;"

# Set environment variables
DB_PASSWORD=your_password
JWT_SECRET=your-256-bit-secret

# Run
mvn spring-boot:run