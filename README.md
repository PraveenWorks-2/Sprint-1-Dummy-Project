## Team 4 — Authentication Module

### Implemented Features

The Team 4 Authentication module was implemented using Java 21, Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, Spring Security, REST APIs, Maven and Postman.

### Authentication Features

- User Login API
- JWT Authentication
- JWT Token Generation and Validation
- Login Request Validation
- Account Enabled/Disabled Validation
- Account Locked Validation
- Invalid Credentials Handling

### Password Management

- BCrypt Password Encryption
- Secure Password Storage
- Password Change API
- Current Password Verification
- Password Policy Validation
- Minimum Password Length Validation
- Uppercase Character Validation
- Lowercase Character Validation
- Number Validation
- Special Character Validation

### MFA Framework

- Console-based OTP Generation
- OTP Verification
- OTP Expiration Handling
- Invalid OTP Validation
- OTP Reuse Prevention

### Validation & Exception Handling

- Request validation using Jakarta Validation
- Invalid credentials handling
- Account disabled handling
- Account locked handling
- Invalid password handling
- Password policy validation
- Invalid/expired MFA OTP handling

### Database

PostgreSQL database:

`dummyproject_team4`

Authentication data is stored in the `users` table. Passwords are stored using BCrypt encryption rather than plain text.

### API Testing

The implemented APIs were tested using Postman, including:

- Login
- JWT-protected API access
- Password Change
- Password Policy validation
- MFA OTP generation
- MFA OTP verification
- Invalid OTP handling
- Expired OTP handling
- OTP reuse validation

### Architecture
```text

  Client / Postman
         ↓
  Controller
         ↓
  DTO
         ↓
  Service
         ↓
  ServiceImpl
         ↓
  Repository
         ↓
  Entity
         ↓
  PostgreSQL
```
# Team 4 Contribution

# Sai Charan
- Login
- JWT Authentication
- Login Validation
- Validation and exception handling related to assigned features

# Kirubakaran S.
- Password Encryption
- Password Policy
- MFA Framework
