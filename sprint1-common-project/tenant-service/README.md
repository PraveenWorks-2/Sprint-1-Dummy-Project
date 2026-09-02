# OneEnterprise Tenant Management Service

**Developer:** Rayi Mohan  
**Assigned branch:** `mohan-tenant-service`  
**Microservice:** Tenant Management Service  
**Repository:** https://github.com/PraveenWorks-2/Sprint-1-Dummy-Project.git

## Assignment scope from the provided PDF

The PDF assigns Rayi Mohan the **Tenant Management Service** on branch `mohan-tenant-service`.

Responsibilities:
- Create Tenant
- Tenant Profile
- Tenant Status
- Tenant Configuration
- Tenant Update APIs

Expected implementation:
- Entity
- DTO
- Repository
- Service
- ServiceImpl
- Controller
- Validation
- Exception handling
- PostgreSQL integration
- Postman testing

## Important source-based note

The PDF does not specify the exact tenant fields, endpoint paths, port, or database name. Those details are therefore implementation choices in this ZIP, designed to cover every assigned responsibility without changing the scope.

## Endpoints implemented

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/tenants` | Create tenant |
| GET | `/api/tenants` | Get all tenants |
| GET | `/api/tenants/{id}` | Get tenant by ID |
| PUT | `/api/tenants/{id}` | Update tenant |
| DELETE | `/api/tenants/{id}` | Deactivate tenant (soft delete) |
| GET | `/api/tenants/{id}/profile` | Get tenant profile |
| PUT | `/api/tenants/{id}/profile` | Update tenant profile |
| GET | `/api/tenants/{id}/status` | Get tenant status |
| PATCH | `/api/tenants/{id}/status` | Update tenant status |
| GET | `/api/tenants/{id}/configuration` | Get tenant configuration |
| PUT | `/api/tenants/{id}/configuration` | Update tenant configuration |

## Technology

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Bean Validation
- Maven
- JUnit 5 + Mockito
- Postman

## Project structure

```text
tenant-service/
├── pom.xml
├── README.md
├── .gitignore
├── postman/
│   └── OneEnterprise-Tenant-Service.postman_collection.json
└── src/
    ├── main/
    │   ├── java/com/oneenterprise/tenant/
    │   │   ├── TenantServiceApplication.java
    │   │   ├── controller/
    │   │   ├── dto/
    │   │   ├── entity/
    │   │   ├── exception/
    │   │   ├── repository/
    │   │   ├── service/
    │   │   └── serviceimpl/
    │   └── resources/
    │       ├── application.properties
    │       └── application-local.properties
    └── test/
        └── java/com/oneenterprise/tenant/serviceimpl/
            └── TenantServiceImplTest.java
```

## PostgreSQL setup

Create the database:

```sql
CREATE DATABASE oneenterprise_tenant_db;
```

Default settings:

```text
Host: localhost
Port: 5432
Database: oneenterprise_tenant_db
Username: postgres
Password: postgres
```

If your PostgreSQL credentials differ, edit:

`src/main/resources/application.properties`

The service uses:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Hibernate creates/updates only these service-owned tables:

- `tenants`
- `tenant_configurations`

Do not change another developer's tables.

## Run in Eclipse

1. Extract the ZIP.
2. Eclipse -> File -> Import -> Existing Maven Projects.
3. Select the extracted `tenant-service` folder.
4. Wait for Maven dependencies.
5. Confirm Java 21 is selected.
6. Start PostgreSQL and create `oneenterprise_tenant_db`.
7. Run `TenantServiceApplication.java` as **Java Application**.
8. Verify: `http://localhost:8083/actuator/health`

Expected:

```json
{"status":"UP"}
```

## Maven test/build

From the project root:

```bash
mvn clean test
mvn clean package
mvn spring-boot:run
```

The included JUnit/Mockito test does not require PostgreSQL.

## End-to-end Postman test

Import:

`postman/OneEnterprise-Tenant-Service.postman_collection.json`

Run the requests in order:

1. Create Tenant
2. Get All Tenants
3. Get Tenant By ID
4. Get Tenant Profile
5. Update Tenant Profile
6. Get Tenant Status
7. Update Tenant Status
8. Get Tenant Configuration
9. Update Tenant Configuration
10. Update Tenant
11. Deactivate Tenant
12. Get Tenant After Deactivation
13. Validation Check

The Create request automatically stores the returned UUID in the collection variable `tenantId`.

Expected results:
- Create: `201 Created`, status `PENDING`
- Update status: `200 OK`, status `ACTIVE`
- Deactivate: `204 No Content`, tenant remains with status `INACTIVE`
- Validation: `400 Bad Request`
- Unknown UUID: `404 Not Found`

## Git and Pull Request workflow

The PDF requires:

1. Clone the common repository.
2. Checkout the assigned branch.
3. Pull latest changes before starting.
4. Work only on the assigned microservice.
5. Commit with a clear message.
6. Push to the assigned branch.
7. Create a Pull Request.
8. Merge only after approval.

Commands:

```bash
git clone https://github.com/PraveenWorks-2/Sprint-1-Dummy-Project.git
cd Sprint-1-Dummy-Project

git fetch origin
git checkout mohan-tenant-service
git pull origin mohan-tenant-service
```

If the branch does not exist locally but exists on GitHub:

```bash
git fetch origin
git checkout -b mohan-tenant-service origin/mohan-tenant-service
```

Copy this `tenant-service` folder into the common repository at the location agreed by the team.

Then:

```bash
git status
git add tenant-service
git commit -m "feat: implement tenant management service"
git push -u origin mohan-tenant-service
```

On GitHub create a Pull Request:

- **base:** your team's agreed common integration branch
- **compare:** `mohan-tenant-service`
- **title:** `feat: Tenant Management Service`

Suggested PR summary:

```text
Implemented Tenant Management Service for the OneEnterprise sprint.

- Create and retrieve tenants
- Tenant profile APIs
- Tenant status APIs
- Tenant configuration APIs
- Tenant update/deactivation
- Validation and global exception handling
- PostgreSQL/JPA persistence
- JUnit/Mockito service test
- Postman end-to-end collection
```

Do not directly merge into the common branch unless your team/maintainer approves the PR.

## Integration coordination

Page 4 of the PDF says developers must coordinate API contracts, IDs, event names and integration requirements. This implementation uses UUID tenant IDs.

Before the PR is merged, confirm with the Role/Permission developers that:
- they use the same tenant ID format
- their tenant-related API contracts match
- no other developer is changing the Tenant Service tables

The PDF's common service flow starts with:

`Tenant Service -> Role Service -> Permission Service -> Role-Permission Service -> User-Role Service -> Security & Session Service -> Audit & Activity Service -> Notification Service`

## Redis and Kafka

The PDF assigns Redis mainly to Security & Session Service and Kafka processing mainly to Audit & Activity and Notification services. Therefore this Tenant Service intentionally does not add Redis/Kafka implementation unless the team later defines a shared contract requiring it.

## Authentication

The PDF assigns security/session responsibilities to the Security & Session Service. This Tenant Service therefore does not invent a JWT/security implementation that was not assigned in the source document.
