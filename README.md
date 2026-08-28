# OneEnterprise - Security & Session Service

## Overview

The Security & Session Service is one of the common microservices in the **OneEnterprise Access, Tenant & Security Management Platform**.

This microservice is responsible for:

- Session Management
- Device Management
- Login History
- Account Security Validation
- Redis-based Session Support

The service is implemented using Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, Redis, Maven and REST APIs.

---

## Project Information

| Property | Value |
|---|---|
| Project | OneEnterprise Access, Tenant & Security Management Platform |
| Microservice | Security & Session Service |
| Developer | Kirubakaran |
| Git Branch | `kirubakaran-security-service` |
| Java | 21 |
| Spring Boot | 4.0.8 |
| Build Tool | Maven |
| Database | PostgreSQL |
| Cache / Session Store | Redis |
| API Testing | Postman |
| Service Port | 8087 |

---

## Responsibilities

This microservice implements the following responsibilities assigned to the Security & Session Service:

### 1. Session Management

- Create user sessions
- Get session by ID
- Get active sessions for a user
- Terminate sessions
- Maintain session expiration
- Store active session information in Redis

### 2. Device Management

- Register user devices
- Get device by ID
- Get active devices for a user
- Deactivate devices
- Maintain device information

### 3. Login History

- Record successful login attempts
- Record failed login attempts
- Store device and IP information
- Retrieve login history for a user

### 4. Account Security Validation

- Validate account security status
- Check active sessions
- Check active devices
- Check failed login count
- Return an overall security status

### 5. Redis Session Support

Redis is used for fast session access.

Session keys follow the format:

    session:<session-token>

Session values follow the format:

    <userId>:<deviceId>

Sessions are stored with a one-hour TTL.

---

# Technology Stack

- Java 21
- Spring Boot 4.0.8
- Spring Web MVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- Spring Data Redis
- Redis 7
- Maven
- Docker / Docker Compose
- REST API
- Postman
- Git / GitHub
- Lombok
- Bean Validation

---

# Project Structure

```text
security-session-service/
│
├── pom.xml
├── docker-compose.yml
├── README.md
├── mvnw
├── mvnw.cmd
│
└── src/
    ├── main/
    │   │
    │   ├── java/
    │   │   └── com/
    │   │       └── oneenterprise/
    │   │           └── securitysession/
    │   │               │
    │   │               ├── SecuritySessionServiceApplication.java
    │   │               │
    │   │               ├── config/
    │   │               │   └── RedisConfig.java
    │   │               │
    │   │               ├── controller/
    │   │               │   ├── SessionController.java
    │   │               │   ├── DeviceController.java
    │   │               │   ├── LoginHistoryController.java
    │   │               │   └── SecurityController.java
    │   │               │
    │   │               ├── dto/
    │   │               │   ├── SessionRequest.java
    │   │               │   ├── SessionResponse.java
    │   │               │   ├── DeviceRequest.java
    │   │               │   ├── DeviceResponse.java
    │   │               │   ├── LoginHistoryRequest.java
    │   │               │   ├── LoginHistoryResponse.java
    │   │               │   └── SecurityValidationResponse.java
    │   │               │
    │   │               ├── entity/
    │   │               │   ├── UserSession.java
    │   │               │   ├── UserDevice.java
    │   │               │   └── LoginHistory.java
    │   │               │
    │   │               ├── exception/
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   ├── ResourceNotFoundException.java
    │   │               │   └── SecurityValidationException.java
    │   │               │
    │   │               ├── redis/
    │   │               │   └── SessionRedisService.java
    │   │               │
    │   │               ├── repository/
    │   │               │   ├── UserSessionRepository.java
    │   │               │   ├── UserDeviceRepository.java
    │   │               │   └── LoginHistoryRepository.java
    │   │               │
    │   │               └── service/
    │   │                   ├── SessionService.java
    │   │                   ├── DeviceService.java
    │   │                   ├── LoginHistoryService.java
    │   │                   ├── SecurityService.java
    │   │                   │
    │   │                   └── impl/
    │   │                       ├── SessionServiceImpl.java
    │   │                       ├── DeviceServiceImpl.java
    │   │                       ├── LoginHistoryServiceImpl.java
    │   │                       └── SecurityServiceImpl.java
    │   │
    │   └── resources/
    │       └── application.properties
    │
    └── test/
        └── java/
```
#Database
PostgreSQL

#Database:

security_session_db

Host:`127.0.0.1`

Port:`5432`

Username:`postgres`

The PostgreSQL password is configured locally and should not be exposed in documentation or source control.

#Database Tables

The service uses three tables.

##user_sessions
Stores user session information.

Fields:
`
id
user_id
session_token
device_id
ip_address
created_at
expires_at
active
user_devices
`
Stores registered user device information.

Fields:
`
id
user_id
device_id
device_name
ip_address
created_at
last_used_at
active
login_history
`
Stores successful and failed login attempts.

Fields:
`
id
user_id
device_id
ip_address
login_time
success
failure_reason
`
