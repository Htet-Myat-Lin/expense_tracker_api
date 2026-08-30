# Expense Tracker - Backend

REST API for the Expense Tracker PWA. Users register, log in, and manage income/expense transactions categorized by type.

## Tech Stack

- **Spring Boot 4.1.1** (Java 25)
- **Spring WebMVC** + **Spring Security** (JWT authentication)
- **Spring Data JPA / Hibernate**
- **MySQL** (via `mysql-connector-j`)
- **JJWT 0.12.6** for JWT generation and validation
- **Jakarta Bean Validation** + **Lombok**
- **Maven** (with Maven Wrapper)

## Project Structure

```
backend/
├── pom.xml
├── Dockerfile
├── mvnw / mvnw.cmd
└── src/
    ├── main/java/com/example/expense_tracker/
    │   ├── ExpenseTrackerApplication.java   # Entry point
    │   ├── config/                          # Security config, JWT filter, auth entry point
    │   ├── controller/                      # Auth, Transaction, Category REST controllers
    │   ├── service/                         # Auth, JWT, Transaction, UserDetails services
    │   ├── entity/                          # User, Transaction, Category, TokenBlacklist entities
    │   ├── repository/                      # Spring Data JPA repositories
    │   ├── dto/                             # Request/response DTOs
    │   ├── enums/                           # TransactionType (INCOME / EXPENSE)
    │   ├── specification/                   # JPA Specification for dynamic filtering
    │   ├── seeder/                          # CategorySeeder (default categories on startup)
    │   ├── validation/                      # Custom @PasswordMatches validator
    │   └── exception/                       # Global exception handler
    └── resources/
        └── application.properties
```

## Getting Started

### Prerequisites

- **Java 25+**
- **MySQL** instance (or reachable database)

### Configuration

Configuration lives in `src/main/resources/application.properties`. All sensitive values are overridable via environment variables:

| Environment Variable | Default | Description |
|---|---|---|
| `DB_URL` | MySQL Database URL | JDBC connection URL |
| `DB_USERNAME` | Database Username | Database username |
| `DB_PASSWORD` | Database Password | Database password |
| `JWT_SECRET` | JWT Secret Key | HMAC-SHA signing key (512 bits for production) |
| `JWT_EXPIRATION` | `43200000` | Token validity in ms (12 hours) |
| `PORT` | `8080` | Server port (used in Docker entrypoint) |

### Run Locally

```bash
# From the backend/ directory
./mvnw spring-boot:run
```

### Build & Package

```bash
./mvnw clean package
java -jar target/expense-tracker-0.0.1-SNAPSHOT.jar
```

## API Endpoints

All responses follow an `ApiResponse<T>` envelope: `{ success, message, content }`.

### Auth

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Register (username, email, password, confirmPassword). Sets JWT cookie. |
| `POST` | `/api/auth/login` | Public | Login with email/password. Sets JWT cookie. |
| `POST` | `/api/auth/logout` | Yes | Revokes the current JWT. |
| `GET` | `/api/auth/get-current-user` | Yes | Returns the authenticated user profile. |

### Transactions

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/api/transactions` | Yes | Create a transaction (name, amount, type, categoryId). |
| `GET` | `/api/transactions` | Yes | List transactions. Query params: `type`, `page`, `limit`. |
| `PATCH` | `/api/transactions/{id}` | Yes | Update a transaction. |
| `DELETE` | `/api/transactions/{id}` | Yes | Delete a transaction. |
| `GET` | `/api/transactions/report` | Yes | Balance, total income, total expense. |

### Categories

| Method | Path | Auth | Description |
|---|---|---|---|
| `GET` | `/api/categories` | Yes | List all seeded categories. |

## Security

- **Stateless** sessions, CSRF disabled.
- JWT is delivered via an **HTTP-only, Secure, SameSite=None cookie** named `token` (not an Authorization header). Requires HTTPS.
- Passwords hashed with **BCrypt**.
- Logout uses a **token blacklist** table to revoke JWTs server-side.
- CORS allows all origins with credentials; the `Set-Cookie` header is exposed.
