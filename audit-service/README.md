# Audit & Activity Service

Part of the **OneEnterprise** microservices project. Handles audit logging, activity tracking, and consumes Kafka events from upstream services (Role, Permission, Security & Session, User-Role).

## Tech Stack
Java 21 · Spring Boot 4.1.1 · Spring Data JPA · PostgreSQL · Apache Kafka · Maven

## Setup

1. Create the database:
   ```sql
   CREATE DATABASE audit_service_db;
   ```
2. Update `application.properties` with your Postgres username/password.
3. Start Kafka and create the topics:
   ```
   brew services start kafka
   kafka-topics --create --topic role-events --bootstrap-server localhost:9092
   kafka-topics --create --topic permission-events --bootstrap-server localhost:9092
   kafka-topics --create --topic security-events --bootstrap-server localhost:9092
   kafka-topics --create --topic user-events --bootstrap-server localhost:9092
   ```
4. Run:
   ```
   mvn clean spring-boot:run
   ```
   Service runs on **port 8086**.

## API Endpoints

Base URL: `http://localhost:8086/api/v1/audit-logs`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/` | Create audit log |
| GET | `/` | Get all logs |
| GET | `/{id}` | Get log by ID |
| GET | `/user/{userId}` | Filter by user |
| GET | `/tenant/{tenantId}` | Filter by tenant |
| GET | `/action/{action}` | Filter by action |
| GET | `/source/{sourceService}` | Filter by source service |
| GET | `/entity/{entityName}/{entityId}` | Audit trail for an entity |

## Kafka Topics

`role-events` · `permission-events` · `security-events` · `user-events`

Expected event shape:
```json
{
  "userId": 5,
  "tenantId": 2,
  "action": "ROLE_ASSIGNED",
  "module": "ROLE_SERVICE",
  "entityName": "Role",
  "entityId": "10",
  "description": "Admin role assigned to user",
  "sourceService": "role-service"
}
```
> Payload shape is a working assumption — confirm with upstream service owners before final integration.

## Branch
`saicharan-audit-service`

## Author
Sai Charan