A production-oriented eCommerce backend built with Spring Boot 3.2.5 and Java 21,
exposing REST APIs for user management, product catalog, shopping cart,
and order lifecycle management.

## Tech Stack

- *Framework:* Spring Boot 3.2.5, Java 21
- *Security:* Spring Security + JWT (stateless auth)
- *Database:* PostgreSQL + Spring Data JPA / Hibernate
- *Cache:* Redis
- *Mapping:* MapStruct
- *Containerization:* Docker
- Implemented unit testing using JUnit 5 and Mockito
   Wrote ** test cases** covering:
    - Service layer logic
    - Validation scenarios
    - Exception handling

## Features

- User registration, login, and role-based access control (ADMIN / USER)
- JWT-based stateless authentication
- Product catalog with CRUD operations
- Shopping cart management (add, update, remove items)
- Order placement and order lifecycle tracking
- Input validation with meaningful error responses
- Redis caching for performance-sensitive reads
