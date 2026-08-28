# Notification Service

**OneEnterprise Access, Tenant & Security Management Platform**
Owner: **Naveen Kumar** | Branch: `naveen-notification-service`

## Responsibilities
- Notification Creation
- Kafka Consumer (Role, Permission, Security events)
- Kafka Producer (Notification-sent events for Audit Service)
- Email / In-App Notification Processing
- Notification History

## Project Structure
```
notification-service/
├── src/main/java/com/oneenterprise/notificationservice/
│   ├── controller/        # REST endpoints
│   ├── service/           # Service interface
│   ├── serviceimpl/       # Business logic implementation
│   ├── repository/        # Spring Data JPA repository
│   ├── entity/            # JPA entities & enums
│   ├── dto/                # Request/Response DTOs
│   ├── kafka/              # Kafka producer, consumer, event payload
│   ├── config/             # Kafka topic configuration
│   └── exception/          # Global exception handling
├── src/main/resources/application.properties
└── pom.xml
```

## Tech Stack
Java 21 • Spring Boot 3.3 • Spring Data JPA • Hibernate • MySQL • Apache Kafka • Maven • Lombok

## Prerequisites
- Java 21
- Maven 3.9+
- MySQL running locally (e.g. via MySQL Workbench / MySQL Server) — or update `application.properties`
- Kafka broker running on `localhost:9092`

## Database Setup
In MySQL Workbench, run:
```sql
CREATE DATABASE IF NOT EXISTS oneenterprise_notification_db;
```
(The connection URL also has `createDatabaseIfNotExist=true`, so this is created automatically on first run if it doesn't exist.)

Update `spring.datasource.username` / `spring.datasource.password` in `application.properties` to match your MySQL Workbench connection credentials.

Table `notifications` is auto-created via `spring.jpa.hibernate.ddl-auto=update`.

## Run Locally
```bash
mvn clean install
mvn spring-boot:run
```
Service starts on **http://localhost:8088**

## REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/v1/notifications` | Create a notification |
| GET    | `/api/v1/notifications` | Get all notifications |
| GET    | `/api/v1/notifications/{id}` | Get notification by ID |
| GET    | `/api/v1/notifications/user/{userId}` | Get notification history for a user |
| PATCH  | `/api/v1/notifications/{id}/mark-sent` | Mark notification as sent |
| PATCH  | `/api/v1/notifications/{id}/mark-failed` | Mark notification as failed |
| DELETE | `/api/v1/notifications/{id}` | Delete a notification |

### Sample Create Request
```json
POST /api/v1/notifications
{
  "recipientUserId": 101,
  "tenantId": 5,
  "title": "Role Assigned",
  "message": "You have been assigned the ADMIN role.",
  "notificationType": "ROLE_ASSIGNED",
  "channel": "BOTH",
  "sourceEvent": "role-assigned-events"
}
```

## Kafka Topics

| Topic | Direction | Purpose |
|-------|-----------|---------|
| `role-assigned-events` | Consumed | From Role-Permission / User-Role Service |
| `permission-changed-events` | Consumed | From Permission Service |
| `security-activity-events` | Consumed | From Security & Session Service |
| `notification-events` | Produced | Consumed by Audit & Activity Service |

Update topic names/bootstrap servers in `application.properties` to match the shared team Kafka config.

## Git Workflow
```bash
git clone https://github.com/PraveenWorks-2/Sprint-1-Dummy-Project.git
cd Sprint-1-Dummy-Project
git checkout naveen-notification-service
git pull origin naveen-notification-service

# work inside notification-service/ only

git add .
git commit -m "feat: implement notification service with Kafka integration"
git push origin naveen-notification-service
# open a Pull Request for review, merge only after approval
```

## Postman
Import the endpoints above into a Postman collection named `Notification-Service.postman_collection.json` for testing, and export it as part of deliverables.

## Notes
- This service owns only the `notifications` table — do not modify other services' schemas.
- Coordinate event names/payload contracts (`NotificationEvent`) with Role, Permission, and Security & Session Service owners before finalizing.
