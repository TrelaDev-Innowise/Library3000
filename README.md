# 📚 Library3000App

Library3000App is a console application for managing books, written in Java using Spring.  
Book data is now stored in a PostgreSQL database via Docker and accessed using **Spring JDBC (JdbcTemplate)**.
## ✨ Features
✅ Display the list of books  
✅ Add a new book  
✅ Edit a book  
✅ Delete a book  
✅ Search books by keyword  
✅ Multi-language support (English, Polish)  
✅ Logging & caching via Spring AOP

## 🛠 Requirements
🔹 Java 21 
🔹 Gradle  
🔹 Docker & Docker Compose

## 🚀 Setup & Running

### 1️⃣ Start the Database
Run the following command in the root of the project to start the PostgreSQL database using Docker Compose:

```sh
docker-compose up -d
```

This will start a PostgreSQL container with the database used by the application.


### 2️⃣ Build the Project
Build the project and create a fat JAR:

```sh
gradlew shadowJar
```

### 3️⃣ Run the Application
Once the database is up and the JAR is built, run the application:

```sh
java -jar build/libs/Library3000-1.0-SNAPSHOT.jar
```

Flyway will automatically initialize the schema and insert sample data when the application starts.
---

## 📂 Project Structure

```
src/
└── main/
    ├── java/
    │   └── testing/
    │       ├── config/           # Spring configuration (DataSource, MessageSource, etc.)
    │       ├── model/            # Domain models: Book, Author, Genre
    │       ├── repository/       # Repositories using JdbcTemplate
    │       ├── service/          # Business logic layer
    │       ├── util/             # Utility/helper classes (if any)
    │       └── Library3000App.java  # Main application class (Spring Boot entry point)
    └── resources/
        ├── migration/
        │   ├── V1__create_tables.sql
        │   └── V2__insert_initial_data.sql
        ├── messages_en.properties
        └── messages_pl.properties

```

---

## 🗃 Database Structure

Instead of a CSV file, the application now uses a PostgreSQL database with the following schema:

**Tables:**

- `books`: `id`, `title`, `description`, `genre`, `rating`, `pages`
- `authors`: `author_id`, `name`
- `books_authors`: `book_id`, `author_id` (many-to-many relationship between books and authors)
- `genres`: `id`, `name` (list of available book genres)

The database schema is managed automatically by **Flyway**.


---

## 🎮 How to Use

After running the app, you will see a menu:

```sql
Choose an option:
1 - Display book list
2 - Create a new book
3 - Edit a book
4 - Delete a book
5 - Search by keyword
6 - Exit
Your choice:
```

🔹 Add a book with multiple authors  
🔹 Search by keyword in title, description, or author name  
🔹 Validation for fields like rating (0–5), genre, and pages

---

# 🌍 Multi-Language Support

The application supports multiple languages using `MessageSource`.

Language selection is prompted at runtime:
```text
Select language: 1 for English, 2 for Polski
```

Localized messages are loaded from:
- `messages.properties` (English)
- `messages_pl.properties` (Polish)

You can easily add new languages by adding more properties files.

---

# 🛠 AOP Logging and Caching

Implemented using Spring AOP. The `LoggingAndCachingAspect` handles:
- Logging method calls, returns, and execution time
- Caching return values of service methods to optimize performance

---

## ⚙ Technologies Used

🔹 **Java 17** – Core language  
🔹 **Spring Context & AOP** – Configuration, dependency injection, and aspect logic  
🔹 **Spring JDBC (JdbcTemplate)** – Type-safe, efficient database access  
🔹 **JDBC (JdbcTemplate)** – Database access  
🔹 **PostgreSQL** – Relational database (via Docker)  
🔹 **Flyway** – Database schema migration  
🔹 **Gradle + Shadow Plugin** – Building fat JAR  
🔹 **Docker Compose** – Running PostgreSQL instance
🔹 **Lombok** – Reduces boilerplate code (getters, setters, constructors, etc.)

---
📌 **Note**: Make sure Docker is running before launching the application.
