# **Sprint-1 Dummy Project**

## Overview

Dummy Project is a Spring Boot application for managing users.

### Technologies

Java

Spring Boot

Spring Data JPA

Hibernate

PostgreSQL

Lombok

Maven

Postman

### User Module

The User module provides the following features:

User registration

User activation

User deactivation

Bulk user upload

CSV user import

Duplicate email validation

### API Endpoints

#### Register User

#### POST

/user/register

Example request:

{
"firstName": "Aadvik",
"lastName": "Karthikeyan",
"email": "aadvik@gmail.com",
"phone": "9442267066",
"password": "Aadvik@15"
}

### Bulk Upload Users

POST

#### /user/bulk-upload

Request body:

[
{
"firstName": "Anu",
"lastName": "Kumar",
"email": "anu.kumar@test.com",
"phone": "9876500011"
},
{
"firstName": "Vijay",
"lastName": "Raj",
"email": "vijay.raj@test.com",
"phone": "9876500012"
}
]

#### Import Users from CSV

POST

/user/import

Use multipart/form-data with the key:

file

CSV format:

firstName,lastName,email,phone
Arjun,Kumar,arjun.kumar@test.com,9876543210
Priya,Raj,priya.raj@test.com,9876543211

## Project Structure

src/main/java/com/oneenterprise/dummyproject
│
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── mapper
│   ├── repository
│   └── service
│       └── impl
│
└── DummyprojectApplication.java

## Database

The application uses PostgreSQL for storing user information.

Configure the database connection in:

src/main/resources/application.properties

##### Example:

spring.datasource.url=jdbc:postgresql://localhost:5433/dummyproject_team3
spring.datasource.username=postgres
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

## Running the Project

Start PostgreSQL.

Create the required database.

Update the database credentials in application.properties.

Run the Spring Boot application.

Test the APIs using Postman.

Default application URL:

http://localhost:8080

## Testing

The APIs can be tested using Postman.

For CSV import, use:

Body → form-data → file

and select the CSV file.

| Method | Endpoint                 | Purpose       |
| ------ | ------------------------ | ------------- |
| POST   | `/user/register`         | Register user |
| GET    | `/user/{id}`             | Get user      |
| GET    | `/user/activate/{id}`    | Activate      |
| GET    | `/user/de-activate/{id}` | Deactivate    |
| POST   | `/user/bulk-upload`      | Bulk users    |
| POST   | `/user/import`           | CSV import    |


## Build

Run:

mvn clean install

To run the application:

mvn spring-boot:run