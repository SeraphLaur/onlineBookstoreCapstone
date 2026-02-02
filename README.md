# Online Bookstore - Capstone Project

## Project Overview

This capstone project is a full-stack web application for an online bookstore, developed as part of the HTD Java Developer training program. The project demonstrates comprehensive skills in Java development, web technologies, database management, and software engineering best practices.

---

##  Project Objectives

This project showcases the trainee's ability to:
- Design and implement a complete full-stack web application
- Apply Object-Oriented Programming principles and design patterns
- Develop RESTful APIs using Spring Boot
- Create responsive user interfaces with HTML, CSS, and JavaScript
- Implement proper database design and management
- Write comprehensive unit tests
- Follow industry-standard coding practices and conventions

---

## Features

### User Management
- **User Registration**: New users can create an account with secure credential storage
- **User Authentication**: Secure login system for registered users

### Book Catalog
- **Browse Books**: Display comprehensive list of available books
- **Book Details**: View detailed information including title, author, price, and description
- **Search Functionality**: Search books by title, author, or keywords
- **Category Filtering**: Filter books by categories for easier navigation

### Shopping Experience
- **Shopping Cart**: Add/remove books from cart with quantity management
- **Checkout Process**: Complete purchase workflow
- **Order History**: View past orders and order details

---

## Technology Stack

### Backend
- **Java** - Core programming language
- **Spring Boot** - Application framework
- **MySQL** - Relational database management
- **REST API** - Backend-frontend communication

### Frontend
- **HTML5** - Page structure
- **CSS3** - Styling and responsive design
- **JavaScript** - Client-side interactivity
- **Fetch API** - Asynchronous backend communication

### Testing
- **Mockito** - Mocking framework for dependencies

### Tools & Practices
- **Git** - Version control
- **Maven/Gradle** - Build automation and dependency management
- **MVC Pattern** - Application architecture
- **Singleton Pattern** - Database connection management

---

## Project Structure

```
online-bookstore/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bookstore/
│   │   │       ├── controller/      # REST API Controllers
│   │   │       ├── model/           # Entity classes (User, Book, Order)
│   │   │       ├── repository/      # Database repositories
│   │   │       ├── service/         # Business logic
│   │   │       └── config/          # Configuration classes
│   │   │                
│   │   ├── resources/
│   │   │   ├── static/              # Frontend files (HTML, CSS, JS)
│   │   │   ├── templates/           # Template files (if using)
│   │   │   └── application.properties
│   └── test/
│       └── java/                     # Unit tests  
├── pom.xml / build.gradle            # Dependencies
└── README.md
```

---

## Getting Started

### Prerequisites

- **Java JDK**: Version 11 or higher
- **MySQL**: Version 8.0 or higher
- **Maven**: Version 3.6+ or Gradle 7.0+
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (recommended)
- **Git**: For version control

### Installation & Setup

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd online-bookstore
   ```

2. **Configure Database**

   Create a MySQL database:
   ```sql
   CREATE DATABASE bookstore;
   ```

   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Install Dependencies**

   Using Maven:
   ```bash
   mvn clean install
   ```


4. **Run the Application**

   Using Maven:
   ```bash
   mvn spring-boot:run
   ```

 

5. **Access the Application**

   Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

---

##  Database Schema

### Entity Relationship Diagram

The database consists of 6 interconnected tables with proper normalization and referential integrity.

### Tables

#### **users**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | BIGINT | PRIMARY KEY |
| `email` | VARCHAR(255) | NOT NULL, UNIQUE |
| `first_name` | VARCHAR(255) | NOT NULL |
| `hashed_password` | VARCHAR(255) | NOT NULL |
| `last_name` | VARCHAR(255) | NOT NULL |

**Relationships:**
- One user can have many orders (one-to-many)
- One user can have one cart (one-to-one)

---

#### **categories**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INT | PRIMARY KEY |
| `name` | VARCHAR(255) | NOT NULL, UNIQUE |

**Relationships:**
- One category can have many books (one-to-many)

---

#### **books**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | BIGINT | PRIMARY KEY |
| `author` | VARCHAR(255) | NOT NULL |
| `description` | TEXT | |
| `isbn` | VARCHAR(255) | UNIQUE |
| `price` | DECIMAL(12,2) | NOT NULL |
| `stock` | INT | NOT NULL |
| `title` | VARCHAR(255) | NOT NULL |
| `category_id` | INT | FOREIGN KEY → categories(id) |
| `image_url` | VARCHAR(2048) | |

**Relationships:**
- Many books belong to one category (many-to-one)
- One book can appear in many cart items (one-to-many)
- One book can appear in many order items (one-to-many)

---

#### **carts**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | BIGINT | PRIMARY KEY |
| `user_id` | BIGINT | FOREIGN KEY → users(id) |

**Indexes:**
- `PRIMARY` on id

**Relationships:**
- One cart belongs to one user (one-to-one)
- One cart can have many cart items (one-to-many)

---

#### **cart_items**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | BIGINT | PRIMARY KEY |
| `quantity` | INT | NOT NULL |
| `book_id` | BIGINT | FOREIGN KEY → books(id) |
| `cart_id` | BIGINT | FOREIGN KEY → carts(id) |

**Indexes:**
- `PRIMARY` on id
- `fk_cart_items_book` on book_id
- `fk_cart_items_cart` on cart_id

**Relationships:**
- Many cart items belong to one cart (many-to-one)
- Many cart items reference one book (many-to-one)

---

#### **orders**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | BIGINT | PRIMARY KEY |
| `status` | VARCHAR(255) | NOT NULL |
| `user_id` | BIGINT | FOREIGN KEY → users(id) |
| `total` | DECIMAL(12,2) | NOT NULL |

**Indexes:**
- `PRIMARY` on id
- `fk_orders_users` on user_id

**Relationships:**
- Many orders belong to one user (many-to-one)
- One order can have many order items (one-to-many)

---

#### **order_items**
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | BIGINT | PRIMARY KEY |
| `quantity` | INT | NOT NULL |
| `book_id` | BIGINT | FOREIGN KEY → books(id) |
| `order_id` | BIGINT | FOREIGN KEY → orders(id) |
| `unit_price` | DECIMAL(12,2) | NOT NULL |
| `line_total` | DECIMAL(12,2) | NOT NULL |

**Indexes:**
- `PRIMARY` on id
- `fk_order_item_book` on book_id
- `fk_order_item_order` on order_id

**Relationships:**
- Many order items belong to one order (many-to-one)
- Many order items reference one book (many-to-one)

---

### Database Relationships Summary

```
users (1) ─────< orders (M)
              └─< order_items (M) >─── books (1)
                                          ↑
users (1) ─── carts (1) ────< cart_items (M) >───┘
                                          
categories (1) ─────< books (M)
```

### Key Design Features

 **Normalization**: Properly normalized to 3NF to reduce redundancy  
 **Referential Integrity**: Foreign key constraints maintain data consistency  
 **Indexing**: Appropriate indexes on foreign keys for query optimization  
 **Data Types**: Appropriate data types for each field (DECIMAL for currency, TEXT for descriptions)  
 **Scalability**: BIGINT for primary keys to handle large datasets

---

##  API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Books
- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by ID
- `GET /api/books/search?query={query}` - Search books
- `GET /api/books/category/{category}` - Filter by category

### Cart
- `GET /api/cart` - Get user's cart
- `POST /api/cart/add` - Add item to cart
- `PUT /api/cart/update/{id}` - Update cart item
- `DELETE /api/cart/remove/{id}` - Remove item from cart

### Orders
- `POST /api/orders/checkout` - Create new order
- `GET /api/orders` - Get user's order history
- `GET /api/orders/{id}` - Get order details

---

##  Testing

### Running Unit Tests

Using Maven:
```bash
mvn test
```

### Test Coverage

The project includes comprehensive unit tests for:
- **Service Layer**: Business logic validation
- **Controller Layer**: API endpoint testing


Test coverage goal: **Minimum 50%**

### Testing Frameworks Used

- Mockito for mocking dependencies


---

##  Design Patterns Implemented

### 1. **Model-View-Controller (MVC)**
Separates the application into three interconnected components for better organization and maintainability.

### 2. **Singleton Pattern**
Used for database connection management to ensure only one instance exists throughout the application lifecycle.

### 3. **Repository Pattern**
Abstracts data access logic and provides a clean API for data operations.

### 4. **Service Layer Pattern**
Encapsulates business logic separate from controllers and repositories.

---

## Coding Best Practices Demonstrated

### Code Quality
-  **Clean Code**: Readable, well-structured code with proper indentation
-  **Meaningful Naming**: Descriptive variable, method, and class names
-  **Code Comments**: Inline documentation for complex logic
-  **DRY Principle**: Avoiding code duplication through modularization

### Architecture
-  **Separation of Concerns**: Clear separation between layers
-  **Single Responsibility**: Each class has one well-defined purpose
-  **Dependency Injection**: Using Spring's IoC container

### Documentation
-  **README**: Project setup and usage instructions

### Version Control
-  **Git Workflow**: Regular commits with meaningful messages
-  **Branching Strategy**: Feature branches for development
-  **`.gitignore`**: Excluding unnecessary files

---

##  Frontend Features

- **Responsive Design**: Mobile-friendly interface
- **User-Friendly Navigation**: Intuitive menu and layout
- **Dynamic Content**: AJAX-based updates without page reload
- **Form Validation**: Client-side and server-side validation
- **Shopping Cart UI**: Real-time cart updates

---

##  Security Considerations

- Password hashing using BCrypt
- Input validation and sanitization
- SQL injection prevention through parameterized queries
- CORS configuration for API security
- Session management for authenticated users

---

##  Troubleshooting

### Common Issues

**Database Connection Error**
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure database exists

**Port Already in Use**
- Change server port in `application.properties`:
  ```properties
  server.port=8081
  ```

**Dependencies Not Resolved**
- Run `mvn clean install` or `gradle clean build`
- Check internet connection for dependency downloads

---

##  Future Enhancements

Potential improvements for extended versions:
- Payment gateway integration
- Book reviews and ratings
- Wishlist functionality
- Admin panel for book management
- Email notifications
- Book recommendations

---

## Developer Information

**Project Type**: Capstone Project  
**Training Program**: HTD Java Developer  
**Development Period**: January 28, 2026 - February 2, 2026
**Developer**: Laurence Arvin M. Arcilla


---



**Last Updated**: February 2, 2026  
**Version**: 1.0.0