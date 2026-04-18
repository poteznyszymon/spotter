# Spotter

A Spring Boot application with PostgreSQL, MinIO, and Mailpit integration.

## Overview
Spotter is a Java-based backend application built with Spring Boot. It provides JWT-based authentication, file storage via MinIO, and email testing with Mailpit.

## Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 4.0.4
- **Package Manager:** Maven
- **Database:** PostgreSQL 16
- **Authentication:** JWT (jjwt)
- **Object Storage:** MinIO
- **Mail Testing:** Mailpit
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)

## Requirements
- Java 21 (JDK 21+)
- Docker and Docker Compose
- Maven (or use the provided `./mvnw` wrapper)

## Setup & Run

### 1. Environment Configuration
The application uses environment variables for configuration. Copy `.env.local` to `.env` and adjust values:

```bash
# Postgres
POSTGRES_DB=spotter
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_HOST=localhost
POSTGRES_PORT=5432

# MinIO
MINIO_ROOT_USER=minioadmin
MINIO_ROOT_PASSWORD=minioadmin
MINIO_ENDPOINT=http://localhost:9000
MINIO_BUCKET=spotter

# JWT
JWT_SECRET=change-me-to-a-long-random-string
JWT_EXPIRATION_MS=3600000
JWT_ACCESS_TOKEN_NAME=token

# APP
ADMIN_USERNAME=admin
ADMIN_PASSWORD=admin
```

### 2. Start Infrastructure
Start the required services (PostgreSQL, Mailpit, MinIO) using Docker Compose:

```bash
docker-compose up -d
```

### 3. Run the Application
Use the Maven wrapper to run the Spring Boot application:

```bash
./mvnw spring-boot:run
```
The application will be available at `http://localhost:8080`.

## Scripts & Entry Points
- **Entry Point:** `com.example.spotter.SpotterApplication`
- **Maven Commands:**
  - `./mvnw clean install` - Build and install the project
  - `./mvnw spring-boot:run` - Start the application
  - `./mvnw test` - Run unit and integration tests

## Service URLs
Once the application and infrastructure are running, you can access the following services:

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI definition:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **MinIO Console:** [http://localhost:9001](http://localhost:9001)
- **Mailpit Web UI:** [http://localhost:8025](http://localhost:8025)

## Architecture
The project follows **Hexagonal Architecture** (also known as Ports and Adapters), ensuring a clean separation of concerns and making the core logic independent of external frameworks or tools.

## Tests
To run the tests:
```bash
./mvnw test
```
The project includes tests for Spring Boot application context, Security, and Web MVC.

## License
TODO: Add License Information.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.4/maven-plugin)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.0.4/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.4/reference/web/servlet.html)
* [Spring Security](https://docs.spring.io/spring-boot/4.0.4/reference/web/spring-security.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.0.4/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [SpringDoc OpenAPI](https://springdoc.org/)
