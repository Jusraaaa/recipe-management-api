# 🍽️ Recipe Management REST API – Spring Boot

## 📌 Project Description

This project is a **Spring Boot REST API** developed for managing cooking recipes in a structured and scalable way.
The system supports full recipe management, including categories, ingredients, users, and reviews, following a clean **Controller–Service–Repository** architecture.

The API is designed as a **backend-only system**, focusing on business logic, validation, exception handling, and proper RESTful design principles.

---

## ⚙️ Technologies Used

* **Java 17**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **H2 Database** (in-memory, for development)
* **JUnit 5**
* **Mockito**
* **Gradle**
* **Postman** (for API testing)

---

## 🏗️ System Architecture

The application follows a layered architecture:

* **Controller Layer** – Handles HTTP requests and responses
* **Service Layer** – Contains business logic and calculations
* **Repository Layer** – Data access using Spring Data JPA
* **DTO Layer** – Separates internal entities from API responses
* **Mapper Layer** – Converts Entities ↔ DTOs
* **Exception Handling** – Global exception handling with custom exceptions

---

## 🧩 Entities

The system includes the following entities:

* **User**
* **Recipe**
* **Review**
* **Ingredient**
* **Category**

Relationships between entities are properly defined using JPA annotations.

---

## 🚀 Core Features

* CRUD operations for all main entities
* Recipe categorization and ingredient management
* User-based recipe reviews
* **Average rating calculation** for recipes
* Filtering and searching support
* Input validation using annotations
* Custom exceptions with global handling
* Clean DTO-based API responses
* Recipe sorting support (by name, preparationTime, etc.)
* Average rating endpoint for recipes

---

## 🌐 API Endpoints (Examples)

### Recipe

* `POST /recipes` – Create a recipe
* `GET /recipes?sortBy={field}&dir={asc|desc}` – Get all recipes with sorting
* `GET /recipes/{id}` – Get recipe by ID
* `PUT /recipes/{id}` – Update recipe
* `DELETE /recipes/{id}` – Delete recipe
* `GET /recipes/{id}/average-rating`– Get average rating for a recipe


### Review

* `POST /recipes/{id}/reviews` – Add review to recipe
* `GET /recipes/{id}/reviews` – Get reviews for a recipe

*(Additional endpoints exist for categories, ingredients, and users)*

---

## 🧪 Unit Testing

All tests are executed using Gradle (`./gradlew test`) and generate an HTML test report under `build/reports/tests/test/index.html`.
Tests also cover sorting functionality and average rating calculation.


Covered layers:

* **Service layer tests** – Business logic validation
* **Controller layer tests** – REST endpoint behavior

Tests ensure correctness, isolation of logic, and proper exception handling.

---

## 🗄️ Database Configuration

The application uses an **H2 in-memory database**.

Configuration is located in `application.properties`:

* No external database setup required
* Data is initialized automatically on application startup

---

## ▶️ How to Run the Project

1. Clone the repository
2. Open the project in **IntelliJ IDEA** or any Java IDE
3. Make sure Java 17 is installed
4. Run the main class:

   ```
   DemoApplication.java
   ```
5. Access the API via:

   ```
   http://localhost:8080
   ```
6. Use **Postman** or **Insomnia** to test endpoints

---

## 📎 Notes

* This is a **backend-focused academic project**
* Designed to demonstrate Spring Boot, REST APIs, and clean architecture
* Project developed as part of the **Programming in Java** course
* The API supports sorting, filtering, and rating calculations implemented at the service layer.

