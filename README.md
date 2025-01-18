Employee Performance Appraisal System
The Employee Performance Appraisal System is a Spring Boot application designed to manage and evaluate employee performance. This system provides an efficient way to assess, track, and improve employee performance based on a predefined rating system. It includes features for adding new performance records, viewing performance distributions, calculating deviations, and suggesting adjustments based on performance ratings and Test case coverage. 

Features
Employee Performance Management:

Add individual or multiple employee performance records.
Retrieve all employee performance records.
Performance Analysis:

Calculate the actual distribution of ratings for all employees.
Compare actual rating percentages with the predefined standard percentages.
Suggest adjustments to improve rating distributions based on deviations from the standards.
Batch Processing:

Ability to add multiple employee performance records in a single request.
RESTful APIs:

All functionalities are exposed via REST APIs, making it suitable for integration with other systems.
Testing:

Unit and integration tests using JUnit 5 and Mockito.
API tests with MockMvc to verify the correctness of the API responses.
Technology Stack
Backend:

Java 11 (or higher)
Spring Boot 2.x
Spring Data JPA for database interaction
Spring Web for RESTful services
Spring Boot Test, JUnit 5, Mockito for testing
Database:

H2 (in-memory database by default)
Supports MySQL/PostgreSQL through configuration changes
Testing:

JUnit 5
Mockito for mocking dependencies
Spring MockMvc for API endpoint testing
Build Tool: Maven

API Documentation:
Swagger UI via Springdoc OpenAPI for easy exploration of available endpoints.

Prerequisites
To run the project locally, ensure you have the following software installed:
Java Development Kit (JDK) version 11 or higher
Maven version 3.6 or higher
Git for version control
