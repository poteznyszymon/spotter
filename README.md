# Spotter

A Spring Boot application with PostgreSQL, FusionAuth, MinIO, and Mailpit integration.

## Overview
Spotter is a Java-based backend application built with Spring Boot. It provides security integration with FusionAuth, file storage via MinIO, and email testing with Mailpit.

## Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 4.0.4
- **Package Manager:** Maven
- **Database:** PostgreSQL 16
- **Identity Provider:** FusionAuth
- **Object Storage:** MinIO
- **Mail Testing:** Mailpit
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)

## Requirements
- Java 21 (JDK 21+)
- Docker and Docker Compose
- Maven (or use the provided `./mvnw` wrapper)

## Setup & Run

### 1. Environment Configuration
The application uses environment variables for configuration. A `.env` file is present in the root directory. Ensure it contains correct values:

```bash
# Postgres
POSTGRES_DB=spotter
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_HOST=localhost
POSTGRES_PORT=5432

# FusionAuth
DATABASE_USERNAME=fusionauth
DATABASE_PASSWORD=fusionauth
FUSIONAUTH_APP_MEMORY=512M
FUSIONAUTH_APP_RUNTIME_MODE=development
FUSIONAUTH_APP_KICKSTART_FILE=/usr/local/fusionauth/kickstart/kickstart.json
FUSIONAUTH_API_KEY=...
FUSIONAUTH_CLIENT_ID=...
FUSIONAUTH_CLIENT_SECRET=...

# MinIO
MINIO_ROOT_USER=minio123456
MINIO_ROOT_PASSWORD=minio123456d

# JWT
JWT_SECRET=...
JWT_EXPIRATION=3600000
```

### 2. Start Infrastructure
Start the required services (PostgreSQL, FusionAuth, Mailpit, MinIO) using Docker Compose:

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
- **FusionAuth Admin UI:** [http://localhost:9011](http://localhost:9011)
- **MinIO Console:** [http://localhost:9001](http://localhost:9001)
- **Mailpit Web UI:** [http://localhost:8025](http://localhost:8025)

## Project Structure
- `src/main/java`: Source code
  - `com.example.spotter.configuration`: Configuration classes (Security, etc.)
  - `com.example.spotter.SpotterApplication`: Main entry point
- `src/main/resources`: Configuration files (application.yaml)
- `src/test/java`: Tests
- `docker-compose.yml`: Infrastructure services configuration

## Tests
To run the tests:
```bash
./mvnw test
```
The project includes tests for Spring Boot application context, Security, and Web MVC.

## TODOs
- [ ] Add specific license information (currently empty in `pom.xml`)
- [ ] Document specific API endpoints beyond the Swagger UI link
- [ ] Configure CI/CD pipelines
- [ ] Update `kickstart.json` documentation for FusionAuth (if applicable)

## License
TODO: Add License Information.

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.4/maven-plugin/build-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.0.4/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.4/reference/web/servlet.html)
* [Spring Security](https://docs.spring.io/spring-boot/4.0.4/reference/web/spring-security.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.0.4/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [SpringDoc OpenAPI](https://springdoc.org/)
