# Product Management REST API

A production-ready RESTful Product Management API built using Java, Spring Boot, Spring Data JPA, PostgreSQL, Spring Security, JWT, Refresh Tokens, Swagger/OpenAPI, JUnit, Mockito, H2, Docker, and Docker Compose.

This project demonstrates REST API development, CRUD operations, authentication, authorization, role-based access control, validation, exception handling, pagination, database integration, automated testing, API documentation, and containerized deployment.

---

## Features

- RESTful Product CRUD API
- Product and Item relationship
- JWT authentication
- Access Token and Refresh Token
- Refresh Token rotation
- Role-Based Access Control (RBAC)
- ADMIN and USER roles
- BCrypt password hashing
- Jakarta Bean Validation
- Global exception handling
- Standardized JSON error responses
- Pagination
- PostgreSQL database
- H2 test database
- Spring Data JPA / Hibernate
- Swagger / OpenAPI documentation
- JUnit 5 testing
- Mockito unit testing
- Spring Boot integration testing
- MockMvc API testing
- Docker support
- Docker Compose
- CORS configuration
- Stateless authentication
- Environment-based configuration

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 4.1.1 | Backend framework |
| Spring Web MVC | REST API |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| PostgreSQL | Production database |
| H2 | Test database |
| Spring Security | Authentication and authorization |
| JWT | Access token authentication |
| Refresh Token | Token renewal |
| BCrypt | Password hashing |
| Jakarta Validation | Request validation |
| JUnit 5 | Testing |
| Mockito | Mocking |
| MockMvc | API testing |
| Swagger/OpenAPI | API documentation |
| Maven | Build tool |
| Docker | Containerization |
| Docker Compose | Application and database orchestration |

---

## Architecture

The project follows a layered architecture.

```text
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
PostgreSQL
```

### Authentication flow

```text
Client
   |
   | username + password
   v
AuthController
   |
   v
AuthService
   |
   v
UserRepository
   |
   v
PostgreSQL
   |
   v
JWT Access Token
+
Refresh Token
```

### Protected request flow

```text
Client
   |
   | Authorization: Bearer <JWT>
   v
JwtAuthenticationFilter
   |
   v
Spring Security
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
PostgreSQL
```

---

## Project Structure

```text
CRUD-Springboot/
│
├── src/
│   │
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── CRUD_Springboot/
│   │   │               │
│   │   │               ├── config/
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   ├── DataSeeder.java
│   │   │               │   └── OpenApiConfig.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   ├── ProductController.java
│   │   │               │   └── AuthController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── ProductRequest.java
│   │   │               │   ├── ProductUpdateRequest.java
│   │   │               │   ├── ProductResponse.java
│   │   │               │   ├── ProductMapper.java
│   │   │               │   ├── ItemResponse.java
│   │   │               │   ├── LoginRequest.java
│   │   │               │   ├── LoginResponse.java
│   │   │               │   └── RefreshTokenRequest.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   ├── Product.java
│   │   │               │   ├── Item.java
│   │   │               │   ├── User.java
│   │   │               │   └── RefreshToken.java
│   │   │               │
│   │   │               ├── exception/
│   │   │               │   ├── ResourceNotFoundException.java
│   │   │               │   ├── InvalidCredentialsException.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── ProductRepository.java
│   │   │               │   ├── ItemRepository.java
│   │   │               │   ├── UserRepository.java
│   │   │               │   └── RefreshTokenRepository.java
│   │   │               │
│   │   │               ├── security/
│   │   │               │   └── JwtAuthenticationFilter.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── ProductService.java
│   │   │               │   ├── ItemService.java
│   │   │               │   ├── AuthService.java
│   │   │               │   ├── JwtService.java
│   │   │               │   ├── RefreshTokenService.java
│   │   │               │   │
│   │   │               │   └── impl/
│   │   │               │       ├── ProductServiceImpl.java
│   │   │               │       ├── ItemServiceImpl.java
│   │   │               │       ├── AuthServiceImpl.java
│   │   │               │       ├── JwtServiceImpl.java
│   │   │               │       └── RefreshTokenServiceImpl.java
│   │   │               │
│   │   │               └── CrudSpringbootApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       │
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── CRUD_Springboot/
│       │               │
│       │               ├── controller/
│       │               │   ├── AuthControllerTest.java
│       │               │   └── ProductControllerTest.java
│       │               │
│       │               ├── repository/
│       │               │   └── ProductRepositoryTest.java
│       │               │
│       │               ├── security/
│       │               │   └── SecurityIntegrationTest.java
│       │               │
│       │               ├── service/
│       │               │   └── impl/
│       │               │       ├── ProductServiceImplTest.java
│       │               │       └── ItemServiceImplTest.java
│       │               │
│       │               └── CrudSpringbootApplicationTests.java
│       │
│       └── resources/
│           └── application.properties
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── .dockerignore
└── README.md
```

---

## Database Design

The application uses PostgreSQL.

### Product Table

```text
product
--------------------------------
id
product_name
created_by
created_on
modified_by
modified_on
```

### Item Table

```text
item
--------------------------------
id
product_id
quantity
```

### User Table

```text
app_user
--------------------------------
id
username
password
role
```

### Refresh Token Table

```text
refresh_token
--------------------------------
id
token
user_id
expires_at
revoked
```

### Relationship

```text
Product
   |
   | 1
   |
   | *
   v
 Item
```

A product can have multiple items.

---

## Authentication

The application uses Spring Security with JWT authentication.

```text
Spring Security
      +
     JWT
      +
 Refresh Token
```

### Login Flow

```text
POST /api/v1/auth/login
          |
          v
Validate username/password
          |
          v
Verify BCrypt password
          |
          v
Generate Access Token
          |
          v
Generate Refresh Token
          |
          v
Return tokens
```

### Login API

```
POST /api/v1/auth/login
```

Request:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Response:

```json
{
  "accessToken": "JWT_ACCESS_TOKEN",
  "refreshToken": "REFRESH_TOKEN"
}
```

The access token is used for protected APIs.

Send it using:

```
Authorization: Bearer <ACCESS_TOKEN>
```

### Refresh Token

When the access token expires, the refresh token can be used to obtain a new access token.

```
POST /api/v1/auth/refresh
```

Request:

```json
{
  "refreshToken": "REFRESH_TOKEN"
}
```

Response:

```json
{
  "accessToken": "NEW_ACCESS_TOKEN",
  "refreshToken": "NEW_REFRESH_TOKEN"
}
```

The old refresh token is revoked during rotation.

---

## Role-Based Access Control

The application supports two roles:

- ADMIN
- USER

### Permissions

| Operation | ADMIN | USER |
|---|---|---|
| Get products | ✅ | ✅ |
| Get product by ID | ✅ | ✅ |
| Get product items | ✅ | ✅ |
| Create product | ✅ | ❌ |
| Update product | ✅ | ❌ |
| Delete product | ✅ | ❌ |

### HTTP Security Responses

Unauthenticated requests:

```
401 UNAUTHORIZED
```

Authenticated users without sufficient permission:

```
403 FORBIDDEN
```

### Default Development Users

The application seeds development users.

**Admin**
```
Username: admin
Password: admin123
Role: ADMIN
```

**User**
```
Username: user
Password: user123
Role: USER
```

These credentials are for local development/testing only.

Production deployments should use secure credentials.

---

## API Documentation

Base API path:

```
/api/v1
```

### Authentication Endpoints

**Login**
```
POST /api/v1/auth/login
```

**Refresh Token**
```
POST /api/v1/auth/refresh
```

### Product Endpoints

**Get All Products**
```
GET /api/v1/products
```

Pagination:

```
GET /api/v1/products?page=0&size=10
```

Parameters:

```
page = page number
size = number of products per page
```

Example:

```
page=0
size=10
```

means the first page with up to 10 products.

**Get Product**
```
GET /api/v1/products/{id}
```

Example:

```
GET /api/v1/products/1
```

**Create Product** (ADMIN only)
```
POST /api/v1/products
```

Request:

```json
{
  "productName": "Laptop"
}
```

**Update Product** (ADMIN only)
```
PUT /api/v1/products/{id}
```

Request:

```json
{
  "productName": "Gaming Laptop"
}
```

**Delete Product** (ADMIN only)
```
DELETE /api/v1/products/{id}
```

Response:

```
204 No Content
```

**Get Product Items**
```
GET /api/v1/products/{id}/items
```

Example:

```
GET /api/v1/products/1/items
```

Example response:

```json
[
  {
    "id": 1,
    "productId": 1,
    "quantity": 10
  },
  {
    "id": 2,
    "productId": 1,
    "quantity": 5
  }
]
```

---

## Validation

Product creation and update requests validate the product name.

Example:

```json
{
  "productName": ""
}
```

Returns:

```json
{
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "Product name is required"
}
```

Validation is implemented using Jakarta Bean Validation.

---

## Error Handling

The application uses a global exception handler.

**400 Bad Request** — used for validation errors.

```json
{
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "Product name is required"
}
```

**401 Unauthorized**

```json
{
  "status": 401,
  "error": "UNAUTHORIZED",
  "message": "Authentication is required"
}
```

**403 Forbidden**

```json
{
  "status": 403,
  "error": "FORBIDDEN",
  "message": "You do not have permission to perform this operation"
}
```

**404 Not Found**

```json
{
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Product not found with id: 1"
}
```

**500 Internal Server Error**

```json
{
  "status": 500,
  "error": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred"
}
```

---

## Swagger / OpenAPI

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```
http://localhost:8080/v3/api-docs
```

Swagger can be used to:

- View all API endpoints
- View request/response schemas
- Authenticate using JWT
- Execute APIs
- Test CRUD operations
- Test validation
- Test authorization

---

## Local Development Setup

### Prerequisites

Install:

- Java 17+
- Maven
- PostgreSQL
- Docker
- Docker Compose
- Git

Verify Java:

```
java -version
```

Verify Docker:

```
docker --version
```

Verify Docker Compose:

```
docker compose version
```

### PostgreSQL Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE product_api;
```

Configure the application using environment variables.

Example:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/product_api
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

JWT_SECRET=your-secure-secret
JWT_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=604800000
```

### Run the Application Locally

Clone the repository:

```
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

Move into the project:

```
cd CRUD-Springboot
```

Build the project:

```
./mvnw clean package
```

On Windows:

```
.\mvnw.cmd clean package
```

Run:

```
.\mvnw.cmd spring-boot:run
```

The application starts on:

```
http://localhost:8080
```

### Run with Docker

Build the application:

```
.\mvnw.cmd clean package -DskipTests
```

Start the application and PostgreSQL:

```
docker compose up --build
```

The application will be available at:

```
http://localhost:8080
```

Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

### Docker Architecture

```text
                 Docker Compose
                       |
          +------------+------------+
          |                         |
          v                         v
   Product API                PostgreSQL
   Spring Boot                Database
      :8080                      :5432
          |
          |
          +------ JDBC -----------+
```

The Spring Boot container connects to PostgreSQL using the Docker Compose service name:

```
postgres
```

Database connection:

```
jdbc:postgresql://postgres:5432/product_api
```

### Docker Commands

Start containers:

```
docker compose up
```

Build and start:

```
docker compose up --build
```

Run in background:

```
docker compose up -d
```

Stop containers:

```
docker compose down
```

Stop and remove volumes:

```
docker compose down -v
```

View running containers:

```
docker ps
```

View application logs:

```
docker compose logs app
```

View PostgreSQL logs:

```
docker compose logs postgres
```

---

## Testing

The project contains:

- Unit tests
- Controller tests
- Repository tests
- Security integration tests
- Authentication tests
- Application context tests

Run all tests:

```
.\mvnw.cmd clean test
```

Run a specific test class:

```
.\mvnw.cmd -Dtest=ProductServiceImplTest test
```

Run authentication tests:

```
.\mvnw.cmd -Dtest=AuthControllerTest test
```

Run security integration tests:

```
.\mvnw.cmd -Dtest=SecurityIntegrationTest test
```

### Test Coverage Areas

The tests verify:

```text
Product Service
    |
    +-- Get product
    +-- Create product
    +-- Update product
    +-- Delete product
    +-- Product not found
    +-- Pagination

Item Service
    |
    +-- Get product items
    +-- Product not found

Authentication
    |
    +-- Valid login
    +-- Invalid login
    +-- Valid refresh token
    +-- Invalid refresh token

Security
    |
    +-- No token -> 401
    +-- USER GET -> allowed
    +-- USER POST -> 403
    +-- USER DELETE -> 403
    +-- ADMIN GET -> allowed
    +-- ADMIN POST -> allowed

Repository
    |
    +-- Save product
    +-- Find product
```

---

## Pagination

Products support Spring Data pagination.

Example:

```
GET /api/v1/products?page=0&size=10
```

Pagination concepts:

```
page = 0
```

represents the first page.

```
size = 10
```

returns up to 10 products per page.

---

## Security

The following security practices are implemented:

- JWT authentication
- Stateless sessions
- BCrypt password hashing
- Role-based authorization
- Refresh token rotation
- Revoked refresh tokens
- Request validation
- CORS configuration
- Authentication error handling
- Authorization error handling
- Environment-based JWT configuration

JWT configuration should be supplied through environment variables:

```
JWT_SECRET
JWT_EXPIRATION
JWT_REFRESH_EXPIRATION
```

Secrets should never be committed to GitHub.

---

## CORS

The application supports local frontend development origins such as:

```
http://localhost:3000
http://localhost:5173
```

Allowed methods:

```
GET
POST
PUT
DELETE
OPTIONS
```

---

## Configuration

Example environment variables:

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
JWT_SECRET
JWT_EXPIRATION
JWT_REFRESH_EXPIRATION
```

Example:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/product_api
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=your-secure-secret
JWT_EXPIRATION=900000
JWT_REFRESH_EXPIRATION=604800000
```

Do not use development credentials in production.

---

## API Testing Flow

Recommended testing sequence:

```text
1. Start application
       |
       v
2. Login
       |
       v
3. Copy access token
       |
       v
4. Authorize Swagger
       |
       v
5. GET products
       |
       v
6. Create product as ADMIN
       |
       v
7. Update product as ADMIN
       |
       v
8. Delete product as ADMIN
       |
       v
9. Login as USER
       |
       v
10. GET products -> 200
       |
       v
11. POST product -> 403
       |
       v
12. PUT product -> 403
       |
       v
13. DELETE product -> 403
       |
       v
14. Refresh token -> new tokens
```

---

## Example cURL Requests

### Login

```bash
curl -X POST "http://localhost:8080/api/v1/auth/login" \
-H "Content-Type: application/json" \
-d '{
  "username": "admin",
  "password": "admin123"
}'
```

### Get Products

```bash
curl -X GET "http://localhost:8080/api/v1/products?page=0&size=10" \
-H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Create Product

```bash
curl -X POST "http://localhost:8080/api/v1/products" \
-H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
-H "Content-Type: application/json" \
-d '{
  "productName": "Laptop"
}'
```

### Get Product

```bash
curl -X GET "http://localhost:8080/api/v1/products/1" \
-H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Update Product

```bash
curl -X PUT "http://localhost:8080/api/v1/products/1" \
-H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
-H "Content-Type: application/json" \
-d '{
  "productName": "Gaming Laptop"
}'
```

### Delete Product

```bash
curl -X DELETE "http://localhost:8080/api/v1/products/1" \
-H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Refresh Token

```bash
curl -X POST "http://localhost:8080/api/v1/auth/refresh" \
-H "Content-Type: application/json" \
-d '{
  "refreshToken": "YOUR_REFRESH_TOKEN"
}'
```

---

## HTTP Status Codes

| Status | Meaning |
|---|---|
| 200 | Successful request |
| 201 | Resource created |
| 204 | Resource deleted successfully |
| 400 | Invalid request / validation error |
| 401 | Authentication required or invalid credentials |
| 403 | Insufficient permissions |
| 404 | Resource not found |
| 500 | Internal server error |

---

## Development Workflow

```text
Developer
    |
    v
Write Code
    |
    v
Run Unit Tests
    |
    v
Run Integration Tests
    |
    v
Build JAR
    |
    v
Build Docker Image
    |
    v
Docker Compose
    |
    +---- Spring Boot
    |
    +---- PostgreSQL
    |
    v
Swagger API Testing
```

---

## Build

Build the project:

```
.\mvnw.cmd clean package
```

Skip tests:

```
.\mvnw.cmd clean package -DskipTests
```

Run tests:

```
.\mvnw.cmd clean test
```

---

## Git Workflow

Check status:

```
git status
```

Add changes:

```
git add .
```

Commit:

```
git commit -m "Complete product REST API assignment"
```

Push:

```
git push
```

---

## Environment and Secret Management

Never commit:

- JWT_SECRET
- Database passwords
- Production credentials
- API keys
- Private keys

Use environment variables for sensitive configuration.

Example:

```
JWT_SECRET=<secure-secret>
SPRING_DATASOURCE_PASSWORD=<secure-password>
```

### .gitignore

The repository should ignore:

```
target/
.idea/
.vscode/
*.iml
.env
*.log
```

Do not commit generated build files or secrets.

### .dockerignore

The Docker build context should exclude unnecessary files such as:

```
.git
.gitignore
.idea
.vscode
target/*
*.log
README.md
```

The JAR required by the Dockerfile should remain available in the build context.

---

## Production Considerations

For production deployment, the following should be considered:

- Use HTTPS
- Use secure JWT secrets
- Use environment variables or a secret manager
- Use strong PostgreSQL credentials
- Use database migrations such as Flyway or Liquibase
- Configure production CORS origins
- Configure proper database connection pooling
- Add centralized logging
- Add monitoring and health checks
- Use a reverse proxy/load balancer
- Use secure refresh-token storage
- Avoid development credentials
- Use a production database
- Disable unnecessary debug logging

---

## Project Goals

This project was built to demonstrate practical backend development skills including:

- Java
- Spring Boot
- REST API
- Spring Security
- JWT
- Refresh Tokens
- RBAC
- JPA
- Hibernate
- PostgreSQL
- Validation
- Exception Handling
- Pagination
- JUnit
- Mockito
- Integration Testing
- Swagger
- Docker
- Docker Compose
- Git

---

## Assignment Requirements Covered

- ✅ RESTful Product API
- ✅ Java 17+
- ✅ Spring Boot
- ✅ Spring Data JPA / Hibernate
- ✅ PostgreSQL
- ✅ JWT Authentication
- ✅ Refresh Token
- ✅ Refresh Token Rotation
- ✅ Role-Based Access Control
- ✅ Jakarta Validation
- ✅ Standardized Error Handling
- ✅ Pagination
- ✅ Unit Testing
- ✅ Integration Testing
- ✅ H2 Test Database
- ✅ Swagger/OpenAPI
- ✅ Docker
- ✅ Docker Compose
- ✅ CORS
- ✅ Environment-based configuration
- ✅ Clean layered architecture

---

## Quick Start

The fastest way to run the complete project:

```bash
# Clone repository
git clone <YOUR_GITHUB_REPOSITORY_URL>

# Enter project
cd CRUD-Springboot

# Build
.\mvnw.cmd clean package -DskipTests

# Start application + PostgreSQL
docker compose up --build
```

Open Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

Login:

```
Username: admin
Password: admin123
```

Authorize Swagger with:

```
Bearer <ACCESS_TOKEN>
```

Then test the Product APIs.

---

## Author

**Muhammed Muqhtadeer M**

GitHub: `<YOUR_GITHUB_PROFILE_URL>`

LinkedIn: `<YOUR_LINKEDIN_PROFILE_URL>`

---

## License

This project was created as a backend development / hiring assignment project.
