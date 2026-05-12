# 📚 Library Book Issue & Return Service

A RESTful backend service built with **Spring Boot 3** to manage library books, members, and book issue/return operations — complete with Swagger UI documentation and MySQL persistence.

---

## 🧰 Tech Stack

| Layer        | Technology                              |
|--------------|-----------------------------------------|
| Language     | Java 17                                 |
| Framework    | Spring Boot 3.2.5                       |
| Persistence  | Spring Data JPA + Hibernate             |
| Database     | MySQL                                   |
| Validation   | Spring Boot Validation (Jakarta)        |
| API Docs     | Springdoc OpenAPI (Swagger UI) 2.5.0    |
| Build Tool   | Maven                                   |

---

## 📁 Project Structure

```
library-service/
├── src/main/java/com/library/
│   ├── LibraryApplication.java          # Application entry point
│   ├── controller/
│   │   ├── BookController.java          # Book management endpoints
│   │   ├── MemberController.java        # Member management endpoints
│   │   └── IssueController.java         # Issue & return endpoints
│   ├── service/
│   │   ├── BookService.java
│   │   ├── MemberService.java
│   │   └── IssueService.java            # Core business logic
│   ├── model/
│   │   ├── Book.java
│   │   ├── Member.java
│   │   └── IssueRecord.java
│   ├── repository/
│   │   ├── BookRepository.java
│   │   ├── MemberRepository.java
│   │   └── IssueRecordRepository.java
│   ├── dto/
│   │   ├── BookDTO.java
│   │   ├── MemberDTO.java
│   │   ├── IssueRequestDTO.java
│   │   └── ApiResponse.java             # Unified response wrapper
│   └── exception/
│       ├── ResourceNotFoundException.java
│       ├── BusinessRuleException.java
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   ├── application.properties
│   └── data.sql                         # Sample seed data
└── pom.xml
```

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.6+
- MySQL 8+

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Kushwaha45/Library_Book_issues-and-Return-Service.git
cd library-service
```

### 2. Configure the Database

Create a MySQL database (or let Spring auto-create it):

```sql
CREATE DATABASE library_db;
```

Update `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Aniket@123
```

> ⚠️ **Never commit real credentials.** Use environment variables or a secrets manager in production.

### 3. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

The server will start on **http://localhost:8080**

### 4. Explore the API

Open Swagger UI in your browser:

```
http://localhost:8080/swagger-ui.html
```

Raw OpenAPI spec:

```
http://localhost:8080/api-docs
```

---

## 📡 API Reference

### Books — `/api/books`

| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| POST   | `/api/books`        | Add a new book to the catalog        |
| GET    | `/api/books`        | List all books                       |
| GET    | `/api/books/{id}`   | Get a book by ID                     |
| GET    | `/api/books/available` | List all currently available books |
| GET    | `/api/books/search` | Search by title and/or author (case-insensitive, partial match) |

### Members — `/api/members`

| Method | Endpoint                      | Description                          |
|--------|-------------------------------|--------------------------------------|
| POST   | `/api/members`                | Register a new member                |
| GET    | `/api/members`                | List all members                     |
| GET    | `/api/members/{id}`           | Get member details by ID             |
| GET    | `/api/members/{id}/books`     | Get all books currently issued to a member |

### Issue & Return — `/api/issues`

| Method | Endpoint                      | Description                              |
|--------|-------------------------------|------------------------------------------|
| POST   | `/api/issues/issue`           | Issue a book to a member                 |
| PUT    | `/api/issues/return/{issueId}`| Return an issued book                    |
| GET    | `/api/issues`                 | Get all issue records (active + returned) |
| GET    | `/api/issues/active`          | Get all currently active (unreturned) records |

---

## 📏 Business Rules

The `IssueService` enforces the following rules on every issue request:

1. **Book must exist** — returns `404` if not found.
2. **Book must be available** — returns `400` if already issued to another member.
3. **Member must exist** — returns `404` if not found.
4. **Max 3 active issues per member** — a member cannot hold more than 3 books at a time.
5. **No duplicate active issues** — the same book cannot be issued to the same member twice simultaneously.

On a successful return, the return date is recorded and the book is immediately marked available again.

---

## 🗄️ Data Models

### Book

| Field      | Type    | Notes                      |
|------------|---------|----------------------------|
| bookId     | Long    | Auto-generated primary key |
| title      | String  | Required                   |
| author     | String  | Required                   |
| available  | Boolean | Defaults to `true`         |

### Member

| Field    | Type   | Notes                      |
|----------|--------|----------------------------|
| memberId | Long   | Auto-generated primary key |
| name     | String | Required                   |
| email    | String | Required, unique           |

### IssueRecord

| Field      | Type      | Notes                                |
|------------|-----------|--------------------------------------|
| issueId    | Long      | Auto-generated primary key           |
| issueDate  | LocalDate | Set automatically on issue           |
| returnDate | LocalDate | `null` while book is active          |
| book       | Book      | ManyToOne relationship               |
| member     | Member    | ManyToOne relationship               |

---

## 🌱 Sample Seed Data

The `data.sql` file pre-populates the database on startup with:

**Books:** Java: The Complete Reference, Clean Code, Design Patterns, Spring in Action, Head First Java, Effective Java, Data Structures and Algorithms, The Pragmatic Programmer

**Members:** 3 sample members (Aniket Sharma, Priya Patel, Rahul Verma)

Seed inserts use `INSERT IGNORE` so they are safe to run on a non-empty database.

---

## 🔧 Configuration Reference

Key properties in `application.properties`:

```properties
server.port=8080
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.sql.init.mode=always
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -m "Add my feature"`
4. Push to the branch: `git push origin feature/my-feature`
5. Open a Pull Request

---

## 📄 License

This project is open-source. Add your preferred license here (e.g., MIT, Apache 2.0).
