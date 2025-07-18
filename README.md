# Academy Amey - Spring Boot Learning Project

A Spring Boot application demonstrating REST APIs, database operations, and file processing.

## Features

- **Employee Management**: CRUD operations with JPA
- **Master Tools System**: Excel upload, search, pagination
- **File Processing**: Excel parsing with Apache POI
- **Database**: PostgreSQL with Flyway migrations
- **Exception Handling**: Global error handling

## Tech Stack

- Spring Boot 2.7.8
- Java 17
- PostgreSQL
- Spring Data JPA
- Apache POI
- Lombok
- Flyway

## API Endpoints

### Employee API
- `GET /api/v1/employee` - Get all employees
- `GET /api/v1/employee/{id}` - Get employee by ID
- `POST /api/v1/employee` - Create employee

### Master Tools API
- `POST /masterTools` - Upload Excel file
- `GET /masterTools` - Get tools with pagination
- `POST /masterTools/search` - Search tools
- `PUT /masterTools` - Update tool
- `DELETE /masterTools` - Delete tools

## Setup

1. Configure database in `application.yml`
2. Run: `./mvnw spring-boot:run`
3. API available at `http://localhost:8080`

## Database Schema

Tables created via Flyway migrations:
- `Employeeentity` - Employee data
- `toolsmain` - Tools master data  
- `toolsrefrence` - Tools reference data
├── entity/                             # JPA Entities
│   ├── Employee.java                   # Employee entity
│   └── MasterTools.java                # Master tools entity (POJO)
├── exceptions/                         # Exception handling
│   ├── ErrorResponse.java              # Generic error response
│   ├── GlobalExceptionHandler.java     # Global exception handler
│   ├── MasterToolsErrorResponse.java   # Tools-specific error response
│   └── MasterToolsException.java       # Custom tools exception
├── helper/                             # Utility helpers
│   ├── CountCheckToolHelper.java       # Count check utilities
│   └── ExcelFileHelper.java            # Excel processing utilities
├── internal/                           # Internal tools
│   └── CountCheckTool.java             # Count checking tool
├── reader/                             # File readers
│   ├── AbstractReader.java             # Abstract file reader
│   └── FileReader.java                 # File reading implementation
├── repository/                         # Data access layer
│   ├── EmployeeRepository.java         # Employee JPA repository
│   └── MasterToolsRepository.java      # Master tools custom repository
├── services/                           # Business logic layer
│   ├── EmployeeService.java            # Employee business logic
│   └── MasterToolsService.java         # Master tools business logic
└── utils/                              # Utility classes
    └── ByteConversion.java             # Byte conversion utilities
```

## 🛠️ Technology Stack

- **Framework**: Spring Boot 2.7.8
- **Java Version**: 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Migration**: Flyway
- **File Processing**: Apache POI 5.2.3
- **Validation**: Spring Boot Starter Validation
- **JSON Processing**: Jackson Databind
- **Utility**: Lombok, Apache Commons Collections 4.4
- **Build Tool**: Maven
- **Testing**: Spring Boot Test

## 📊 Database Schema

The application uses two main database tables:
- **Employeeentity**: Stores employee information (ID, email, name, address)
- **toolsmain**: Main tools information table
- **toolsrefrence**: Tools reference data table

Database migrations are managed through Flyway with SQL scripts in `src/main/resources/db/migration/`.

## 🌐 API Endpoints

### Employee Management
- `GET /api/v1/employee` - Get all employees
- `GET /api/v1/employee/{id}` - Get employee by ID
- `POST /api/v1/employee/` - Create new employee

### Master Tools Management
- `GET /api/v1/masterTools` - Get tools with pagination and search
- `GET /api/v1/masterTools/{id}` - Get tool by ID
- `POST /api/v1/masterTools` - Upload Excel file or create tool
- `PUT /api/v1/masterTools/{id}` - Update tool
- `DELETE /api/v1/masterTools` - Delete tools (bulk operation)
- `POST /api/v1/masterTools/search` - Advanced search

### Utility Endpoints
- `GET /api/v1/countCheck` - Run count check tool

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL database
- Maven 3.6+

### Installation
1. Clone the repository
2. Configure database connection in `application.yml`
3. Run database migrations: `mvn flyway:migrate`
4. Build the project: `mvn clean compile`
5. Run the application: `mvn spring-boot:run`

### Running the Application
```bash
# Using Maven
mvn spring-boot:run

# Using Java
java -jar target/acadmey_amey-0.0.1-SNAPSHOT.jar
```

## 📝 Features in Detail

### Excel File Processing
- Upload Excel files containing tools data
- Automatic data validation and parsing
- Error reporting for invalid rows
- Bulk data insertion with conflict resolution

### Search and Filtering
- Global search across all columns
- Column-specific search functionality
- Pagination support
- Sorting by multiple criteria

### Error Handling
- Global exception handling
- Custom error responses
- Detailed error messages with error codes
- HTTP status code mapping

## 📖 Learning Objectives
This project demonstrates:
- Spring Boot application structure and configuration
- REST API design and implementation
- JPA and custom repository patterns
- File upload and processing
- Database migrations with Flyway
- Exception handling strategies
- DTO pattern implementation
- Service layer architecture
- Cross-cutting concerns (CORS, validation)

## 🔄 Future Enhancements
- Add authentication and authorization
- Implement caching mechanisms
- Add comprehensive unit and integration tests
- Implement API documentation with Swagger
- Add monitoring and logging capabilities
- Containerization with Docker
