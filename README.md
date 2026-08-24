# Sprint 1 Dummy Project — Team 2 — Organization Management

This is the **single Team 2 Spring Boot backend module** for the common Sprint 1 repository.

## Team 2 responsibilities

- **Naveen Kumar Vaddepalli:** Company Setup, Business Units
- **Rayi Mohan:** Departments, Branches, Locations

Both feature sets live in this same project, same package root, same Git branch and same PostgreSQL database.

## Package

`com.enterprise.organization`

```text
com.enterprise.organization
├── OrganizationManagementApplication.java
├── config/
├── common/exception/
├── company/              # Naveen
├── businessunit/         # Naveen
├── location/             # Mohan
├── branch/               # Mohan
└── department/           # Mohan
```

## Technology

- Java 21
- Spring Boot 3.3.4
- Spring Data JPA
- Hibernate
- PostgreSQL
- REST APIs
- Postman
- Git/GitHub
- Maven
- Docker Compose
- Swagger/OpenAPI

## Architecture

`Client/Postman → Controller → DTO → Service → ServiceImpl → Repository → Entity → PostgreSQL`

Each Team 2 feature follows the same layered pattern used by Naveen's Company and Business Unit implementation.

## Mohan's APIs

### Locations

| Method | Endpoint |
|---|---|
| POST | `/api/locations` |
| GET | `/api/locations` |
| GET | `/api/locations/{id}` |
| PUT | `/api/locations/{id}` |
| PATCH | `/api/locations/{id}/status` |
| DELETE | `/api/locations/{id}` |

### Branches

| Method | Endpoint |
|---|---|
| POST | `/api/branches` |
| GET | `/api/branches` |
| GET | `/api/branches/{id}` |
| GET | `/api/branches/location/{locationId}` |
| PUT | `/api/branches/{id}` |
| PATCH | `/api/branches/{id}/status` |
| DELETE | `/api/branches/{id}` |

### Departments

| Method | Endpoint |
|---|---|
| POST | `/api/departments` |
| GET | `/api/departments` |
| GET | `/api/departments/{id}` |
| GET | `/api/departments/business-unit/{businessUnitId}` |
| GET | `/api/departments/branch/{branchId}` |
| PUT | `/api/departments/{id}` |
| PATCH | `/api/departments/{id}/status` |
| DELETE | `/api/departments/{id}` |

## Relationship design

The existing Naveen module already establishes:

`Company → BusinessUnit`

Mohan's implementation extends the organization model with:

`Location → Branch`

and Department connects the two organizational dimensions:

`BusinessUnit → Department ← Branch`

Therefore the recommended data creation order is:

1. Create Company.
2. Create Business Unit using `companyId`.
3. Create Location.
4. Create Branch using `locationId`.
5. Create Department using `businessUnitId` and `branchId`.

The Department service validates both parent IDs and returns 404 when either parent does not exist.

## Validation and exception handling

The existing shared `GlobalExceptionHandler`, `DuplicateResourceException` and `ResourceNotFoundException` are reused.

Mohan's DTOs use Jakarta Bean Validation.

Expected errors:

- `400` validation/status errors
- `404` missing Company/Business Unit/Location/Branch/Department
- `409` duplicate codes
- `500` unexpected errors

## PostgreSQL

Database:

`dummyproject_team2`

The existing `application.yml` and Docker Compose from Naveen's project are retained. The database password is configurable with `DB_PASSWORD` and defaults to `postgres`, matching the Docker Compose database.

For a local Docker database:

```bash
docker compose up -d
```

Then:

```bash
mvn clean test
mvn spring-boot:run
```

Application:

`http://localhost:8080`

Swagger:

`http://localhost:8080/swagger-ui.html`

OpenAPI:

`http://localhost:8080/v3/api-docs`

## Postman

Mohan's collection is:

`postman/Organization-Management-Team2-Mohan.postman_collection.json`

Suggested test order:

1. Create Company.
2. Create Business Unit.
3. Create Location.
4. Create Branch.
5. Create Department.
6. Test GET endpoints.
7. Test PUT endpoints.
8. Test PATCH status endpoints.
9. Test duplicate codes → 409.
10. Test invalid parent IDs → 404.
11. Test invalid/missing required fields → 400.

## Git

Common repository:

`https://github.com/PraveenWorks-2/Sprint-1-Dummy-Project.git`

Team branch:

`team2-organization`

Typical workflow:

```bash
git checkout team2-organization
git pull origin team2-organization
# merge/verify Team 2 Company + Business Unit + Department + Branch + Location
mvn clean test
git status
git add .
git commit -m "feat: complete team2 organization management"
git pull origin team2-organization
git push origin team2-organization
```

After Team 2 end-to-end testing, raise the team's Pull Request from `team2-organization` to `main`.

## Integration warning

There must be **one** Spring Boot application, one `pom.xml`, one `application.yml` and one common exception handler in the final Team 2 module.

Before pushing, compare any shared files with the latest Team 2 branch so that Naveen's work is not overwritten.

## POC explanation for Mohan

I implemented the Organization Management features assigned to me: Departments, Branches and Locations. I followed the same layered architecture already used by Naveen: Controller, DTO, Service, ServiceImpl, Repository and Entity. I reused the team's common exception handling and PostgreSQL configuration. Locations can contain branches, and departments reference both an existing business unit and branch. All parent IDs are validated in the service layer, duplicate codes are prevented, status can be changed between ACTIVE and INACTIVE, and the APIs are available for Postman and Swagger testing.


## Package-name note

Naveen's supplied working ZIP uses `com.enterprise.organization`, and Mohan's modules deliberately use the **same package root** so that they merge without package/import conflicts.

The assignment PDF shows an example package root `com.oneenterprise.dummyproject.organization`. That differs from Naveen's actual working ZIP. Do not rename the entire package tree during this merge unless the team explicitly agrees to do so, because that would change every existing import/package declaration. Consistency with the existing Team 2 code is the safer integration choice.
