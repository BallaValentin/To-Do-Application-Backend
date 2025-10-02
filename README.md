# ToDo Application - Backend

This is the **backend** of the ToDo Application, built using **Java (Spring Boot)**. It provides RESTful APIs for managing tasks, user authentication, and database interactions.

## Features

- RESTful API endpoints for managing tasks (create, read, update, delete).
- User authentication and authorization using **JWT access and refresh tokens**.
- Role-based access control (**User** and **Admin**).
- Database persistence with **MySQL** via Spring Data **JpaRepository**.
- Secure token lifecycle management and refresh handling.
- Handles client requests from the frontend (React + TypeScript).

## Technologies

- **Java 17+**
- **Spring Boot** for backend development
- **Spring Security** for authentication and authorization
- **MySQL** for relational data storage
- **JPA/Hibernate** for ORM and database access
- **Maven/Gradle** for dependency management

## Project Structure

- `controllers/` – REST controllers for handling API requests  
- `repositories/` – JpaRepository interfaces for database access  
- `services/` – business logic and data handling  
- `security/` – JWT authentication, filters, and role management  
- `models/` – Entity classes mapped to database tables  

## Getting Started

### Prerequisites

- Java 17+  
- Maven or Gradle  
- MySQL (running locally or remotely)  

### Installation

1. Clone the repository:

```bash
git clone https://github.com/BallaValentin/To-Do-Application-Backend.git
```

2. Navigate into the project directory

```bash
cd To-Do-Application-Backend
```

3. Configure your database connection in src/main/resources/application.properties
```code

```yml
spring:
  profiles:
    active: jpa
  mail:
    host: smtp.gmail.com
    port: 587
    username: your_email@example.com
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
    password: SMTP_PASSWORD
server:
  port: 8081
connection:
  database_url: DATABASE_URL
  database_username: DATABASE_USERNAME
  database_password: DATABASE_PASSWORD
```

4.Build and run the backend:

```bash
gradle build
gradle bootRun
```

