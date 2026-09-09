# OneEnterprise - Security & Session Service

## Overview

The **Security & Session Service** is a core microservice of the
**OneEnterprise Access, Tenant & Security Management Platform**.

The service provides:

-   User session management
-   Device registration and management
-   Login history
-   Account security validation
-   Multi-Factor Authentication (MFA)
-   SSO validation integration boundary
-   Account lockout and automatic unlock
-   Redis-based session and MFA challenge storage
-   Session validation and security checks
-   Kafka-based security activity events
-   Session invalidation when devices are deactivated

## Project Information

  -----------------------------------------------------------------------
  Property                            Value
  ----------------------------------- -----------------------------------
  Project                             OneEnterprise Access, Tenant &
                                      Security Management Platform

  Microservice                        Security & Session Service

  Developer                           Kirubakaran

  Git Branch                          `kirubakaran-security-service`

  Java                                21

  Spring Boot                         4.0.8

  Build Tool                          Maven

  Database                            PostgreSQL

  Cache / Session Store               Redis

  Messaging                           Apache Kafka

  API Testing                         Postman

  Service Port                        8087
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# Responsibilities

## 1. Session Management

The service supports:

-   Create user sessions
-   Get session by ID
-   Get active sessions for a user
-   Terminate sessions
-   Validate sessions using PostgreSQL and Redis
-   Maintain session expiration
-   Store active session information in Redis
-   Detect expired or invalid sessions
-   Invalidate sessions when their device is deactivated

### Redis Session Format

``` text
Key:
session:<session-token>

Value:
<userId>:<deviceId>

TTL:
3600 seconds
```

------------------------------------------------------------------------

## 2. Device Management

The service supports:

-   Register user devices
-   Get device by ID
-   Get active devices for a user
-   Deactivate devices
-   Maintain device information
-   Invalidate active sessions associated with a deactivated device

When a device is deactivated, associated active sessions are invalidated
and their Redis session entries are removed.

------------------------------------------------------------------------

## 3. Login History

The service records:

-   Successful login attempts
-   Failed login attempts
-   Device information
-   IP address
-   Failure reason
-   Login timestamp

Login history is used for account security validation and account
lockout handling.

------------------------------------------------------------------------

## 4. Account Security Validation

The service validates:

-   Active sessions
-   Active devices
-   Failed login history
-   Current failed authentication attempts
-   Account lock status
-   MFA status

### Endpoint

``` http
GET /api/security/validate/{userId}
```

Example:

``` http
GET http://localhost:8087/api/security/validate/104
```

Example response:

``` json
{
  "userId": 104,
  "accountSecure": false,
  "activeSessions": 0,
  "activeDevices": 1,
  "failedLogins": 0,
  "accountLocked": false,
  "failedAttempts": 0,
  "mfaEnabled": true,
  "message": "No active sessions found"
}
```

------------------------------------------------------------------------

# Sprint 2 Security Enhancements

Sprint 2 strengthens the Security & Session Service with:

-   MFA validation flow
-   SSO integration boundary
-   Account lockout
-   Automatic account unlock
-   Enhanced security validation
-   Redis session expiry and lookup
-   Session validation
-   Device/session invalidation
-   Security alert events through Kafka

------------------------------------------------------------------------

## 5. Multi-Factor Authentication (MFA)

The service supports:

-   MFA challenge generation
-   Six-digit OTP generation
-   Redis-based MFA challenge storage
-   Configurable MFA challenge TTL
-   OTP validation
-   Challenge removal after successful validation
-   MFA success security events
-   MFA failure security events
-   MFA enable/disable support

### Create MFA Challenge

``` http
POST /api/security/mfa/challenge
```

Request:

``` json
{
  "userId": 104
}
```

Example response:

``` json
{
  "challengeId": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
  "expiresInSeconds": 300,
  "otp": "685218",
  "userId": 104
}
```

> The OTP is exposed only for local development/testing. A production
> implementation must deliver the OTP through a secure MFA channel and
> must never expose it in an API response.

### Validate MFA

``` http
POST /api/security/mfa/validate
```

Request:

``` json
{
  "userId": 104,
  "challengeId": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
  "otp": "685218"
}
```

Successful response:

``` json
{
  "accountLocked": false,
  "message": "MFA validation successful",
  "userId": 104,
  "validated": true
}
```

### Enable MFA

``` http
PUT /api/security/mfa/{userId}/enable
```

### Disable MFA

``` http
PUT /api/security/mfa/{userId}/disable
```

------------------------------------------------------------------------

## 6. SSO Integration Boundary

The service provides an SSO validation boundary for integration with an
external SSO provider.

### Endpoint

``` http
POST /api/security/sso/validate
```

The current implementation provides a development integration boundary
where the upstream/provider validation result is represented by the
request.

A production implementation should verify the actual provider token or
assertion using the provider SDK or signing keys/JWKs, including issuer
and audience validation. A client-provided validation flag must not be
trusted in production.

------------------------------------------------------------------------

## 7. Account Lockout

The service implements account lockout protection against repeated
failed authentication attempts.

### Default Configuration

``` properties
app.security.lockout.max-failed-attempts=5
app.security.lockout.duration-minutes=15
```

### Lockout Flow

``` text
Failed authentication
        |
        v
failedAttempts++
        |
        v
5 failed attempts
        |
        v
Account locked
        |
        v
15 minute lockout
        |
        v
Automatic unlock
        |
        v
failedAttempts reset to 0
```

Account security information is stored in:

``` text
account_security
```

Fields include:

-   User ID
-   Failed attempts
-   Locked status
-   Lock expiration
-   MFA enabled status
-   Created timestamp
-   Updated timestamp

------------------------------------------------------------------------

## 8. Automatic Account Unlock

When the configured lockout period expires, the account is automatically
unlocked when its security state is checked.

The service resets:

``` text
failedAttempts = 0
locked = false
lockedUntil = null
```

An `ACCOUNT_UNLOCKED` security event is generated when automatic unlock
processing occurs.

------------------------------------------------------------------------

## 9. Redis

Redis is used for:

-   Active session storage
-   Session lookup
-   Session expiration
-   MFA challenge storage
-   MFA challenge expiration

### Configuration

``` text
Application host: 127.0.0.1
Application host port: 6380
Redis container port: 6379
```

### Session Key

``` text
session:<session-token>
```

### Session Value

``` text
<userId>:<deviceId>
```

### Session TTL

``` text
3600 seconds
```

### MFA Key

``` text
mfa:challenge:<challengeId>
```

### MFA TTL

``` text
300 seconds
```

------------------------------------------------------------------------

## 10. Session Validation

The service validates a session against:

-   PostgreSQL session status
-   Redis session presence
-   Device active status
-   Session expiration

### Endpoint

``` http
GET /api/sessions/token/{token}/validate
```

Example valid response:

``` json
{
  "databaseSessionActive": true,
  "deviceActive": true,
  "deviceId": "DEVICE-003",
  "expired": false,
  "message": "Session is valid",
  "redisSessionPresent": true,
  "sessionId": 20,
  "userId": 104,
  "valid": true
}
```

Example invalid response:

``` json
{
  "databaseSessionActive": false,
  "deviceActive": false,
  "deviceId": "DEVICE-003",
  "expired": false,
  "message": "Redis session not found",
  "redisSessionPresent": false,
  "sessionId": 19,
  "userId": 101,
  "valid": false
}
```

------------------------------------------------------------------------

## 11. Device Deactivation and Session Invalidation

When an active device is deactivated:

``` text
Device Deactivation
        |
        v
Device active = false
        |
        v
Find active sessions for device
        |
        v
Session active = false
        |
        v
Delete Redis session
        |
        v
Publish SESSION_INVALID event
```

This prevents continued use of sessions associated with a deactivated
device.

------------------------------------------------------------------------

# Kafka Security Activity Events

## 12. Kafka Integration

The service publishes security activity events to:

``` text
security-activity
```

Example:

``` json
{
  "userId": 104,
  "tenantId": 1,
  "action": "MFA_SUCCESS",
  "module": "SECURITY",
  "entityName": "MfaChallenge",
  "entityId": "challenge-id",
  "description": "MFA validation successful",
  "sourceService": "security-session-service"
}
```

### Security Events

The service can publish:

-   `LOGIN`
-   `LOGOUT`
-   `ACCESS_DENIED`
-   `CREATE`
-   `DELETE`
-   `MFA_SUCCESS`
-   `MFA_FAILED`
-   `ACCOUNT_LOCKED`
-   `ACCOUNT_UNLOCKED`
-   `SSO_VALIDATION_FAILED`
-   `SESSION_EXPIRED`
-   `SESSION_INVALID`

These events provide the security activity integration boundary for
downstream Audit & Activity and Notification services.

> `tenantId=1` is currently used as the development/default tenant
> value. Tenant context should be supplied dynamically when integrated
> with the Tenant service.

------------------------------------------------------------------------

# Technology Stack

-   Java 21
-   Spring Boot 4.0.8
-   Spring Web MVC
-   Spring Data JPA
-   Hibernate
-   PostgreSQL
-   Spring Data Redis
-   Redis
-   Spring Kafka
-   Apache Kafka
-   Maven
-   Docker
-   Docker Compose
-   REST APIs
-   Postman
-   Lombok
-   Jakarta Bean Validation
-   Git
-   GitHub

------------------------------------------------------------------------

# Project Structure

``` text
Sprint-1-Dummy-Project/
├── pom.xml
├── docker-compose.yml
├── README.md
├── mvnw
├── mvnw.cmd
├── security session.postman_collection.json
├── .mvn/
│   └── wrapper/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/oneenterprise/securitysession/
    │   │       ├── SecuritySessionServiceApplication.java
    │   │       ├── config/
    │   │       ├── controller/
    │   │       ├── dto/
    │   │       ├── entity/
    │   │       ├── exception/
    │   │       ├── kafka/
    │   │       ├── redis/
    │   │       ├── repository/
    │   │       └── service/
    │   │           └── impl/
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/
```

------------------------------------------------------------------------

# Database

## PostgreSQL

``` text
Database: security_session_db
Host: 127.0.0.1
Port: 5432
Username: postgres
```

The PostgreSQL password is configured locally and must not be committed
to source control.

## Main Tables

### user_sessions

``` text
id
user_id
session_token
device_id
ip_address
created_at
expires_at
active
```

### user_devices

``` text
id
user_id
device_id
device_name
ip_address
created_at
last_used_at
active
```

### login_history

``` text
id
user_id
device_id
ip_address
login_time
success
failure_reason
```

### account_security

``` text
id
user_id
failed_attempts
locked
locked_until
mfa_enabled
created_at
updated_at
```

------------------------------------------------------------------------

# Application Configuration

Configuration is located at:

``` text
src/main/resources/application.properties
```

Important settings:

``` properties
server.port=8087

spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/security_session_db

spring.data.redis.host=127.0.0.1
spring.data.redis.port=6380

spring.kafka.bootstrap-servers=localhost:9092

app.kafka.topic.security-activity=security-activity

app.security.lockout.max-failed-attempts=5
app.security.lockout.duration-minutes=15
app.security.mfa.ttl-seconds=300
```

> Do not commit real database passwords, API keys, JWT secrets, Kafka
> credentials, Redis credentials, or other sensitive values to GitHub.

------------------------------------------------------------------------

# Docker Compose

Start infrastructure:

``` cmd
docker compose up -d
```

Check containers:

``` cmd
docker ps
```

Stop infrastructure:

``` cmd
docker compose down
```

> Avoid `docker compose down -v` unless database and Redis volumes are
> intentionally being deleted.

------------------------------------------------------------------------

# Redis Verification

Connect:

``` cmd
docker exec -it security-session-redis redis-cli
```

Test:

``` redis
PING
```

Expected:

``` text
PONG
```

Check sessions:

``` redis
KEYS session:*
```

Check a session:

``` redis
GET session:<session-token>
```

Check session TTL:

``` redis
TTL session:<session-token>
```

Expected for a newly created session:

``` text
3600
```

Check MFA challenges:

``` redis
KEYS mfa:*
```

Check MFA TTL:

``` redis
TTL mfa:challenge:<challenge-id>
```

Expected:

``` text
300
```

------------------------------------------------------------------------

# Kafka Verification

Kafka topic:

``` text
security-activity
```

Start consumer:

``` cmd
docker exec -it security-session-kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic security-activity --from-beginning
```

Security activity events generated by the service can be verified from
the Kafka console consumer.

------------------------------------------------------------------------

# API Endpoints

## Device APIs

  Method   Endpoint                       Purpose
  -------- ------------------------------ -------------------
  POST     `/api/devices`                 Register device
  GET      `/api/devices/{id}`            Get device
  GET      `/api/devices/user/{userId}`   Get user devices
  DELETE   `/api/devices/{id}`            Deactivate device

## Login History APIs

  Method   Endpoint                             Purpose
  -------- ------------------------------------ -------------------
  POST     `/api/login-history`                 Record login
  GET      `/api/login-history/user/{userId}`   Get login history

## Session APIs

  Method   Endpoint                                 Purpose
  -------- ---------------------------------------- --------------------------
  POST     `/api/sessions`                          Create session
  GET      `/api/sessions/{id}`                     Get session
  GET      `/api/sessions/user/{userId}`            Get active user sessions
  DELETE   `/api/sessions/{id}`                     Terminate session
  GET      `/api/sessions/token/{token}/validate`   Validate session

## Security APIs

  Method   Endpoint                            Purpose
  -------- ----------------------------------- ---------------------------
  GET      `/api/security/validate/{userId}`   Validate account security

## MFA APIs

  Method   Endpoint                               Purpose
  -------- -------------------------------------- ----------------------
  POST     `/api/security/mfa/challenge`          Create MFA challenge
  POST     `/api/security/mfa/validate`           Validate MFA OTP
  PUT      `/api/security/mfa/{userId}/enable`    Enable MFA
  PUT      `/api/security/mfa/{userId}/disable`   Disable MFA

## SSO API

  ------------------------------------------------------------------------------
  Method                  Endpoint                       Purpose
  ----------------------- ------------------------------ -----------------------
  POST                    `/api/security/sso/validate`   Validate SSO
                                                         integration boundary

  ------------------------------------------------------------------------------

------------------------------------------------------------------------

# Postman Testing

The project contains:

``` text
security session.postman_collection.json
```

## Sprint 1 Testing

Previously tested functionality:

-   Device registration
-   Device retrieval
-   User device retrieval
-   Device deactivation
-   Login history
-   Session creation
-   Session retrieval
-   Active sessions
-   Session termination
-   Redis session storage
-   Redis session deletion
-   Basic security validation

## Sprint 2 Testing

Verified functionality:

-   MFA challenge generation
-   MFA OTP validation
-   MFA Redis challenge storage
-   MFA Redis challenge removal
-   MFA success event
-   MFA failure event
-   Account lockout after five failed attempts
-   `ACCOUNT_LOCKED` Kafka event
-   Automatic account unlock
-   Failed-attempt counter reset
-   MFA status in security validation
-   SSO validation failure event
-   Session validation using PostgreSQL and Redis
-   Device deactivation and session invalidation
-   Redis session removal after device deactivation
-   `SESSION_INVALID` security event

------------------------------------------------------------------------

# Sprint 2 Testing Results

  Test Area                             Result
  ------------------------------------- --------
  MFA challenge generation              PASS
  MFA validation                        PASS
  MFA Redis challenge storage           PASS
  MFA Redis challenge cleanup           PASS
  MFA success event                     PASS
  MFA failure event                     PASS
  Account lockout after five failures   PASS
  `ACCOUNT_LOCKED` Kafka event          PASS
  Automatic account unlock              PASS
  Failed-attempt counter reset          PASS
  Security validation                   PASS
  SSO validation failure event          PASS
  Session validation                    PASS
  Device/session invalidation           PASS
  Redis session removal                 PASS
  Kafka security activity events        PASS

------------------------------------------------------------------------

# Security & Session Flow

``` text
                    Security & Session Service
                               |
             +-----------------+-----------------+
             |                 |                 |
             v                 v                 v
            MFA               SSO          Account Security
             |                 |                 |
             v                 v                 v
           Redis          Validation           Lockout
             |                 |                 |
             +-----------------+-----------------+
                               |
                               v
                        Session Security
                               |
                  +------------+------------+
                  |                         |
                  v                         v
             PostgreSQL                   Redis
                  |                         |
                  +------------+------------+
                               |
                               v
                             Kafka
                               |
                               v
                       Security Activity
                               |
                  +------------+------------+
                  |                         |
                  v                         v
             Audit & Activity          Notification
```

------------------------------------------------------------------------

# Platform Integration Boundary

The Security & Session Service participates in:

``` text
Tenant
   |
   v
Role
   |
   v
Permission
   |
   v
Role-Permission
   |
   v
User-Role
   |
   v
Security & Session
   |
   v
Audit & Activity
   |
   v
Notification
```

The Security & Session Service publishes security activity events
through Kafka for downstream integration.

------------------------------------------------------------------------

# Build and Test

Run from the project root:

``` cmd
mvnw.cmd clean test
```

Or:

``` cmd
.\mvnw.cmd clean test
```

Expected result:

``` text
BUILD SUCCESS
```

------------------------------------------------------------------------

# Git Workflow

Current development branch:

``` text
kirubakaran-security-service
```

Check branch:

``` cmd
git branch --show-current
```

Check status:

``` cmd
git status
```

Review changes:

``` cmd
git diff --stat
```

Add changes:

``` cmd
git add .
```

Commit:

``` cmd
git commit -m "Complete Sprint 2 security session enhancements"
```

Push:

``` cmd
git push origin kirubakaran-security-service
```

------------------------------------------------------------------------

# Development Notes

-   `tenantId=1` is currently a development/default value.
-   MFA OTP exposure is intended only for local development/testing.
-   The SSO implementation is an integration boundary and is not a
    production identity-provider verification implementation.
-   Database credentials and other secrets must remain outside committed
    source code.
-   Sprint 1 functionality is preserved while Sprint 2 security
    enhancements are added.

------------------------------------------------------------------------

# Author

**Kirubakaran S**

Security & Session Service\
OneEnterprise Access, Tenant & Security Management Platform
