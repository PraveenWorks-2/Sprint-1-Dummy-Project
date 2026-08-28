# Role-Permission Service - SRIVASANTHI

## 1. Overview

The Role-Permission Service is responsible for managing the relationship between roles and permissions in the application.

A role can have multiple permissions, and a permission can be assigned to multiple roles.

This service provides APIs to:

- Assign a permission to a role
- Retrieve role-permission mappings
- Remove a permission from a role
- Prevent duplicate role-permission assignments
- Validate roles using the Role Service
- Validate permissions using the Permission Service

---

## 2. Technology Stack

| Technology | Version / Usage |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.8 |
| Spring Data JPA | Database persistence |
| PostgreSQL | Database |
| Spring Cloud OpenFeign | Service-to-service communication |
| Lombok | Boilerplate code reduction |
| Maven | Build and dependency management |
| Postman | API testing |

---

## 3. Service Information

| Property | Value |
|---|---|
| Service Name | Role-Permission Service |
| Port | 8083 |
| Database | PostgreSQL |
| Communication | REST + OpenFeign |

## Development Workflow

Recommended development flow:

1. Start PostgreSQL
2. Start Role-Permission Service
3. Verify application startup
4. Test Role APIs
5. Test Permission APIs
6. Test Role-Permission mapping
7. Verify exception handling
8. Run complete Postman collection
9. Build Docker image
10. Test Docker container
11. Push changes to Git
12. Create Pull Request
    Build

## To create a production build:

1. [x] mvn clean package
2. [x] 
3. [x] The generated JAR will be available under:target/ Git
4. [x] 
5. [x] After completing the implementation:git status
6. [x] 
7. [x] Add the changes:git add .
8. [x] 
9. [x] Commit:git commit -m "Implement role permission management service"
10. [x] 
11. [x] Push the branch:git push origin <branch-name>
12. [x] 
13. [x] Then create a Pull Request for review.
14. [x] 
15. [x] Current Implementation
16. [x] 
17. [x] The Role-Permission Management Service has been implemented and tested locally.
18. [x] 
## The implementation includes:
1. [ ] 
2. [ ] * Role management
3. [ ] * Permission management
4. [ ] * Role-Permission association
5. [ ] * DTO-based request/response handling
6. [ ] * Entity and repository layers
7. [ ] * Service-layer business logic
8. [ ] * REST controllers
9. [ ] * Validation
10. [ ] * Exception handling
11. [ ] * Postman API testing
12. [ ] * Docker support


### Author

# Srivasanthi


### Module: Role-Permission Management Service

