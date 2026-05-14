# MailingService

A Spring Boot microservice responsible for handling email delivery within a distributed system. It integrates with a service registry, exposes a web interface via Thymeleaf, and communicates with other services using OpenFeign.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4.0.5 |
| Templating | Thymeleaf |
| Persistence | Spring Data JPA + PostgreSQL |
| Email | Spring Boot Mail |
| Service Discovery | Netflix Eureka Client |
| Inter-service Calls | OpenFeign |
| Security | Spring Security |
| Build Tool | Maven (Maven Wrapper included) |
| Containerization | Docker (eclipse-temurin:25-jdk-alpine) |

---

## Prerequisites

- Java 25+
- Maven 3.9+ (or use the included `./mvnw` wrapper)
- PostgreSQL database
- A running Eureka server (service registry)
- An SMTP mail server or service (e.g. Gmail, Mailgun)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Bigman004/MailingService.git
cd MailingService
```

### 2. Configure the application

Create or update `src/main/resources/application.properties` (or `application.yml`) with your environment values:

```properties
# Server
server.port=8081

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Mail
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
spring.application.name=mailing-service
```

### 3. Build the project

```bash
./mvnw clean package -DskipTests
```

### 4. Run the application

```bash
java -jar target/mailingService-0.0.1-SNAPSHOT.jar
```

The service will start on port **8081** by default.

---

## Running with Docker

### Build the image

```bash
./mvnw clean package -DskipTests
docker build -t mailing-service .
```

---

## Project Structure

```
MailingService/
├── src/
│   ├── main/
│   │   ├── java/         # Application source code
│   │   └── resources/    # Configuration & Thymeleaf templates
│   └── test/             # Unit and integration tests
├── .github/workflows/    # CI/CD pipelines
├── Dockerfile
├── pom.xml
└── mvnw / mvnw.cmd
```

---

## CI/CD

This project includes a GitHub Actions workflow (`.github/workflows/`) for automated build and Docker image publishing.

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## License

This project is open source. See the repository for details.