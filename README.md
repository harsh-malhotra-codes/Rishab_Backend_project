# Expense App Backend

A robust and secure Spring Boot backend for an Expense Management System with JWT authentication, built with modern Java technologies.

## 🚀 Features

- **User Authentication & Authorization**
  - JWT-based authentication
  - Secure password handling
  - Role-based access control

- **Expense Management**
  - Add, view, update, and delete expenses
  - Categorize expenses
  - Track expense history

- **User Profile**
  - User registration and profile management
  - Secure password updates

- **RESTful API**
  - Clean and consistent API design
  - Proper HTTP status codes
  - Request/Response DTOs
  - Global exception handling

## 🛠️ Tech Stack

- **Backend Framework**: Spring Boot 3.4.1
- **Java Version**: 17
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **Validation**: Bean Validation
- **Object Mapping**: ModelMapper
- **Containerization**: Docker

## 📦 Dependencies

- Spring Web
- Spring Data JPA
- Spring Security
- JWT Authentication
- PostgreSQL Driver
- Lombok
- ModelMapper
- Validation API

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8.4 or higher
- PostgreSQL 12 or higher
- Docker (optional, for containerization)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ExpenseApp_Backend-main
   ```

2. **Database Setup**
   - Create a PostgreSQL database
   - Update the database configuration in `application.properties`

3. **Build the application**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

### Using Docker

1. **Build the Docker image**
   ```bash
   docker build -t expense-app-backend .
   ```

2. **Run the container**
   ```bash
   docker run -p 8080:8080 expense-app-backend
   ```

## 🌐 API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate user and get JWT token

### Expenses
- `GET /api/expenses` - Get all expenses (authenticated)
- `POST /api/expenses` - Create a new expense (authenticated)
- `GET /api/expenses/{id}` - Get expense by ID (authenticated)
- `PUT /api/expenses/{id}` - Update an expense (authenticated)
- `DELETE /api/expenses/{id}` - Delete an expense (authenticated)

### User Profile
- `GET /api/profile` - Get user profile (authenticated)
- `PUT /api/profile` - Update user profile (authenticated)

## 🔒 Security

- JWT-based authentication
- Password encryption using BCrypt
- CORS configuration
- CSRF protection
- Secure headers

## 🧪 Testing

Run the test suite with:
```bash
./mvnw test
```

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
