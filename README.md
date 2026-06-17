# E-Commerce Backend System

A production-oriented eCommerce backend application built using Spring Boot 3.2.5 and Java 21. The system provides secure REST APIs for user management, product catalog management, shopping cart operations, order processing, and order lifecycle management.

Designed using layered architecture principles with JWT-based authentication, Redis caching, PostgreSQL persistence, Docker containerization, and comprehensive unit testing to simulate real-world backend development practices.

Domain Model:
Core entities used in the system:

User,
Role,
Address,
Category,
Product,
Cart,
Order,
OrderItem,
Payment,
Refund

## Tech Stack
### Backend
* Java 21
* Spring Boot 3.2.5
* Spring Security
* Spring Data JPA
* Hibernate

### Database & Cache
* PostgreSQL
* Redis

### Authentication & Authorization
* JWT (JSON Web Token)
* Role-Based Access Control (ADMIN / USER)

### Testing
* JUnit 5
* Mockito

### Tools & DevOps
* Docker
* Maven
* MapStruct
* Git

## Key Features

* User registration and authentication
* JWT-based stateless security
* Role-based authorization (Admin/User)
* Product catalog management
* Shopping cart management
* Order placement and lifecycle tracking
* Address management
* Pagination and filtering support
* Redis caching for performance optimization
* Global exception handling
* Request validation with meaningful error messages
* DTO mapping using MapStruct
* Dockerized application deployment

## API Modules
* Authentication
* User Management
* Address Management
* Product Management
* Category Management
* Shopping Cart Management
* Order Management
* Administrative Operations

## Testing
Implemented unit testing using JUnit 5 and Mockito covering:

* Service layer business logic
* Validation scenarios
* Exception handling
* Authentication workflows

## Future Enhancements

* Product search using QueryDSL
* Apache Kafka based notification service
* Elasticsearch integration for advanced product search
* CI/CD pipeline integration using Jenkins/GitHub Actions
* Kubernetes deployment support
