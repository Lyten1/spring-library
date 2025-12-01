# 📚 BookShelf — Online Book Store REST API

[![Java](https://img.shields.io/badge/Java-17+-red.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)]()
[![Security](https://img.shields.io/badge/Spring%20Security-JWT-yellow.svg)]()
[![Database](https://img.shields.io/badge/Database-MySQL-blue.svg)]()
[![License](https://img.shields.io/badge/Status-Portfolio%20Project-informational.svg)]()

> **BookShelf** is a portfolio-grade backend for an online bookstore.  
> It demonstrates building a production-style REST API with authentication, authorization, cart & order logic, migrations and documentation.

---

## 🧭 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Domain Model](#-domain-model)
- [Controllers & Endpoints](#-controllers--endpoints)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [Getting Started](#-getting-started)
- [Authentication & Authorization](#-authentication--authorization)
- [Testing with Postman](#-testing-with-postman)
- [Demo Video (Loom)](#-demo-video-loom)
- [Challenges & Learnings](#-challenges--learnings)
- [Future Improvements](#-future-improvements)
- [License](#-license)

---

## 🔍 Overview

**BookShelf** is a RESTful API for an online book store.

The application allows:

- Visitors and users to **browse books**
- Authenticated users to **manage a shopping cart** and **place orders**
- Admins to **manage books and categories**

The goal of the project is to showcase:

- Clean layered architecture (Controller → Service → Repository)
- Secure **JWT authentication** with role-based access control
- Realistic domain model with relationships (User, Book, Cart, Order, etc.)
- Database migrations via **Liquibase**
- API documentation via **Swagger / OpenAPI**

---

## 🚀 Features

### 👤 Authentication & Authorization

- User **registration** and **login**
- Authentication using **JWT tokens**
- Role-based access: `ROLE_USER`, `ROLE_ADMIN`
- Security rules:
  - Public endpoints for auth
  - User endpoints for cart & orders
  - Admin-only endpoints for catalog management

### 📚 Book & Category Management

- CRUD operations for **books** (Admin)
- CRUD operations for **categories** (Admin)
- List and view book details for all authenticated users

### 🛒 Shopping Cart

- Each user has a personal **shopping cart**
- Add books to cart
- Update item quantity
- Remove items from cart
- Clear entire cart

### 📦 Orders

- Create an **order** from the current cart
- View **order history** for the authenticated user
- Separate `Order` and `OrderItem` entities
- Soft-delete support to enable future analytics/statistics

### 📘 Documentation & Dev Experience

- Interactive API docs with **Swagger UI**
- Database migrations using **Liquibase**
- Clean DTO-based request/response models
- Separation of concerns and clear package structure

---

## 🛠 Tech Stack

| Category      | Technology                          |
|---------------|-------------------------------------|
| Language      | Java 17+                            |
| Framework     | Spring Boot 3                       |
| Security      | Spring Security, JWT                |
| Persistence   | Spring Data JPA, Hibernate          |
| Database      | MySQL                               |
| Migrations    | Liquibase                           |
| Documentation | Swagger / OpenAPI                   |
| Utilities     | Lombok                              |
| Build Tool    | Maven                               |

---

## 🏗 Architecture

The application follows a layered architecture:

```text
Client
  ↓
Controller layer  →  accepts HTTP requests, maps DTOs
  ↓
Service layer     →  business logic
  ↓
Repository layer  →  Spring Data JPA, queries
  ↓
Database          →  MySQL via Hibernate
```

Security is handled globally using **Spring Security** filters and JWT tokens.

---

## 📁 Project Structure

```text
src/main/java/com/example/bookshelf
├── config         # General configuration (e.g. Swagger)
├── controller     # REST controllers
├── dto            # Request/response DTOs
├── model          # JPA entities
├── repository     # Spring Data JPA repositories
├── security       # JWT, filters, security config
├── service        # Service interfaces
│   └── impl       # Service implementations
└── BookShelfApplication.java
```

---

## 🧱 Domain Model

Main entities:

- **User** – application user, owns a `ShoppingCart` and `Orders`
- **Role** – defines user permissions (`USER`, `ADMIN`)
- **Book** – information about a book (title, author, price, etc.)
- **Category** – category that groups books
- **ShoppingCart** – active cart for a user
- **CartItem** – line in the cart: book + quantity
- **Order** – placed order created from the cart
- **OrderItem** – line in the order: book + quantity + price snapshot

Relationships (simplified):

```text
User 1 ─── 1 ShoppingCart
User 1 ─── * Order
ShoppingCart 1 ─── * CartItem
Order 1 ─── * OrderItem
Category 1 ─── * Book
Book * ─── * OrderItem / CartItem (via relations)
```

---

## 🌐 Controllers & Endpoints

> Exact URLs can be adjusted in your implementation – this is a typical structure.

### 🔐 AuthController

Handles registration and login.

```http
POST /auth/register     # Register a new user
POST /auth/login        # Authenticate and receive JWT token
```

### 📚 BookController

Book operations. Admin-only for write actions.

```http
GET    /books           # List all books
GET    /books/{id}      # Get book details
POST   /books           # Create a new book           (ADMIN)
PUT    /books/{id}      # Update existing book        (ADMIN)
DELETE /books/{id}      # Delete book                 (ADMIN)
```

### 🏷 CategoryController

Category management.

```http
GET    /categories              # List categories
GET    /categories/{id}         # Get category details
POST   /categories              # Create category       (ADMIN)
PUT    /categories/{id}         # Update category       (ADMIN)
DELETE /categories/{id}         # Delete category       (ADMIN)
```

### 🛒 CartController

Shopping cart for the authenticated user.

```http
GET    /cart                    # Get current user's cart
POST   /cart/items              # Add a book to cart
PUT    /cart/items/{itemId}     # Update quantity of a cart item
DELETE /cart/items/{itemId}     # Remove an item from cart
DELETE /cart                    # (Optional) Clear entire cart
```

### 📦 OrderController

Orders for the authenticated user.

```http
POST   /orders                  # Create order from current cart
GET    /orders                  # Get current user's orders
GET    /orders/{id}             # Get specific order details
```

---

## 📘 API Documentation (Swagger)

Once the application is running, open Swagger UI in your browser:

```text
http://localhost:8080/swagger-ui/index.html
```

There you can:

- Explore all available endpoints
- See request/response schemas
- Execute requests directly from the UI (very useful for demos)

---

## ⚙ Getting Started

### ✅ Prerequisites

Make sure you have installed:

- **Java 17+**
- **Maven**
- **MySQL** server
- **Git**

### 1. Clone the repository

```bash
git clone https://github.com/your-username/bookshelf.git
cd bookshelf
```

### 2. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE bookshelf;
```

Configure `src/main/resources/application.properties` (or `.yml`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookshelf
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=validate
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml
```

> Liquibase will handle creating and updating tables based on your changelog files.

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

## 🔐 Authentication & Authorization

The application uses **JWT (JSON Web Token)** for stateless authentication.

### Typical flow

1. **Register** a new user:

   ```http
   POST /auth/register
   ```

2. **Login** with email/username & password:

   ```http
   POST /auth/login
   ```

   Response contains a JWT token, for example:

   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   }
   ```

3. Use the token in all secured requests:

   ```http
   Authorization: Bearer <your-token-here>
   ```

### Roles

- `ROLE_USER`
  - View books & categories
  - Manage own cart
  - Create and view own orders

- `ROLE_ADMIN`
  - All USER capabilities
  - Manage books (CRUD)
  - Manage categories (CRUD)

---

## 🧪 Testing with Postman

A Postman collection can greatly speed up testing.

Recommended path for the collection in the repo:

```text
postman/BookShelf.postman_collection.json
```

Suggested folders inside the collection:

- `Auth` – register, login
- `Books` – list, details, create, update, delete
- `Categories` – CRUD
- `Cart` – add item, update quantity, remove item, get cart
- `Orders` – create order, list orders, get order by id

Import the collection into Postman, set the `{{baseUrl}}` variable to:

```text
http://localhost:8080
```

and update any environment variables (for example `{{token}}`) after login.

---

## 🎥 Demo Video (Loom)

> **Required by the assignment:** record a 2–4 minute Loom video.

Add your link here once recorded:

```text
Loom demo: https://www.loom.com/share/your-video-id
```

Suggested scenario to show in the video:

1. Brief intro: what BookShelf is and tech stack used.
2. Registration and login in Swagger/Postman.
3. Demonstrate getting a JWT token and using it in the `Authorization` header.
4. Show book listing, details, and (as ADMIN) book creation/updating.
5. Add books to the cart, update quantities, and remove an item.
6. Create an order from the cart and view the order history.

---

## 🧩 Challenges & Learnings

During development of **BookShelf**, several real-world challenges were solved:

### 🔐 Security & JWT

- Configuring Spring Security to work with JWT filters.
- Protecting endpoints based on roles (`USER` vs `ADMIN`).
- Handling authentication and authorization errors clearly.

### 🔄 Entity Relationships

- Designing relationships between `User`, `ShoppingCart`, `CartItem`, `Order`, and `OrderItem`.
- Avoiding infinite recursion in JSON serialization (e.g. `@JsonManagedReference`, `@JsonBackReference` or DTO mapping).
- Keeping business logic in the service layer instead of controllers.

### 🗂 Database Migrations with Liquibase

- Writing and organizing Liquibase changelog files.
- Keeping the database schema versioned and reproducible.

### 🛒 Business Logic for Cart & Orders

- Converting cart contents into an order in a safe and consistent way.
- Implementing soft delete for entities to allow future analytics.

These challenges significantly improved understanding of **Spring Boot**, **security**, and **enterprise-style backend architecture**.

---

## 🔭 Future Improvements

Some ideas to evolve BookShelf further:

- ✅ Pagination, filtering and sorting for book listing
- ✅ Search by title/author/category
- 📈 Admin dashboard with statistics (e.g. most sold books)
- 🧪 More unit/integration tests
- ☁ Deployment to a cloud platform (Render/Railway/AWS/Heroku)
- 🧾 Support for discounts, coupons, or promo codes

---

## 📄 License

This project was created for **educational and portfolio** purposes.  
You are free to clone it, explore the code and adapt ideas for your own learning.
