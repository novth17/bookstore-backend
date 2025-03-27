# Bookstore Backend

This project serves as the backend for a bookstore application, providing APIs for management.


## Technologies Used

- **Spring Boot**: Framework for building the backend application.
- **Spring Data JPA**: Simplifies database interactions.
- **Spring Security**: Manages authentication and authorization.
- **H2 Database**: In-memory database for development and testing.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/novth17/bookstore_backend.git
   ```

2. Navigate to the project directory:

   ```
   cd bookstore_backend
   ```

3. Build the project:

   ```
   mvn clean install
   ```

4. Run the application:

   ```
   mvn spring-boot:run
   ```

   The application will start and be accessible at http://localhost:8080.

### Book Management Routes

| HTTP Method | URL            | Description                                                 |
| ----------- | -------------- | ----------------------------------------------------------- |
| GET         | `/login`       | Displays the login form (`login.html`)                      |
| GET         | `/booklist`    | Lists all books (`booklist.html`), shows logged-in username |
| GET         | `/addbook`     | Shows form to add a new book (`addbook.html`)               |
| POST        | `/addbook`     | Processes book addition form data                           |
| GET         | `/edit/{id}`   | Loads book info for editing (`editbook.html`)               |
| POST        | `/edit/{id}`   | Submits updated book information                            |
| GET         | `/delete/{id}` | Deletes a book (Only accessible to users with `ADMIN` role) |
