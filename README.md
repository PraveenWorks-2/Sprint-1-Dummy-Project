 Sprint-1-Dummy-Project

## Sprint 1 - Dummy Project | Team 3

---

## 1. Project Overview

Sprint-1-Dummy-Project is a Spring Boot based backend application developed as part of Sprint 1.

The application provides REST APIs for handling application functionality and user-related operations. It follows a layered architecture using Spring Boot, Spring Data JPA, Hibernate, and MySQL.

The project is designed using a modular approach so that different team members can work on different functionalities while maintaining a common application and database.

---

# 2. Project Objective

The primary objective of this project is to develop a backend application using enterprise-level development practices.

The application provides:

- RESTful APIs
- Database integration
- Business logic through service classes
- Persistence using Spring Data JPA
- MySQL database support
- JSON-based API communication
- CSV-based user import
- Bulk user upload
- API testing using Postman
- Git and GitHub based source-code management

---

# 3. Module Implemented

## User Import Module

The User Import module is responsible for adding multiple users to the application.

The module supports two methods of user import:

1. Bulk User Upload using JSON
2. User Import using CSV

### Responsibilities

The module handles:

- Receiving bulk user data
- Processing user information
- Validating required user fields
- Checking existing users where applicable
- Saving users into the database
- Processing CSV files
- Generating import/upload responses
- Providing REST APIs for client applications

---

# 4. Technology Stack

| Technology | Details |
|---|---|
| Programming Language | Java 21 |
| Framework | Spring Boot 4.0.8 |
| ORM | Hibernate |
| Persistence | Spring Data JPA |
| Database | MySQL 8.0.x |
| Database Driver | MySQL Connector/J |
| Build Tool | Maven |
| API Testing | Postman |
| IDE | Eclipse / IntelliJ / STS |
| Version Control | Git |
| Repository | GitHub |
| Code Generation | Lombok |
| Server | Embedded Apache Tomcat |

---

# 5. System Architecture

The application follows a layered architecture.

```text
                   Client
                     |
                     |
                  REST API
                     |
                     ↓
              Controller Layer
                     |
                     ↓
               Service Layer
                     |
                     ↓
             Repository Layer
                     |
                     ↓
                Entity Layer
                     |
                     ↓
               MySQL Database