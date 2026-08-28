# OneEnterprise Permission Management Service

This is Prasanth K's assigned microservice from the OneEnterprise Access, Tenant & Security Management Platform.

## Responsibilities

- Create Permission
- Permission Categories
- Module Permissions
- Get Permissions
- Update Permissions
- Delete Permissions
- Deactivate Permission

## Stack

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Jakarta Validation
- Swagger/OpenAPI
- Maven
- Docker Compose

## Project structure

```text
permission-service
├── pom.xml
├── docker-compose.yml
├── README.md
└── src/main
    ├── java/com/oneenterprise/permission
    │   ├── PermissionServiceApplication.java
    │   ├── controller
    │   ├── dto
    │   ├── entity
    │   ├── exception
    │   ├── repository
    │   └── service
    └── resources
        ├── application.properties
        └── data.sql
```

## Run PostgreSQL

```bash
docker compose up -d
```

PostgreSQL is exposed on port `5433` on the host.

## Run application

```bash
mvn clean spring-boot:run
```

or run `PermissionServiceApplication` from Eclipse.

## Swagger

Open:

`http://localhost:8082/swagger-ui.html`

## APIs

### Create

POST `/api/permissions`

```json
{
  "name": "Create User",
  "code": "USER_CREATE",
  "description": "Allows creating users",
  "category": "USER_MANAGEMENT",
  "module": "USER"
}
```

### Get all

GET `/api/permissions`

### Get by ID

GET `/api/permissions/1`

### Update

PUT `/api/permissions/1`

```json
{
  "name": "Create User",
  "description": "Allows creating a user",
  "category": "USER_MANAGEMENT",
  "module": "USER",
  "active": true
}
```

### Delete

DELETE `/api/permissions/1`

### Deactivate

PATCH `/api/permissions/1/deactivate`

### Category

GET `/api/permissions/category/USER_MANAGEMENT`

### Module

GET `/api/permissions/module/USER`

## Git workflow

```bash
git checkout prasanth-permission-service
git pull origin prasanth-permission-service

git add .
git commit -m "Implement permission management service"

git push origin prasanth-permission-service
```

Then create a Pull Request to the team's target branch.

## Important team rule

This service owns its permission database/table. Do not directly modify another microservice's tables. Coordinate API contracts, IDs and event contracts with the other developers.
