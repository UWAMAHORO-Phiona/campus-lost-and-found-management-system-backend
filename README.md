# Campus Lost and Found Management System

**Student Name:** Uwamahoro Phiona  
**Student ID:** 27125

## Project Description

The Campus Lost and Found Management System is a backend application designed to help manage lost and found items within a campus. It allows reporters to report items, custodians to handle reports, and the system to manage item categories, posts, report statuses, and return receipts.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Postman

## Database

- Database name: `campus_lostandfound`
- Database: PostgreSQL
- Port: `5432`

## How to Run the Project

1. Install Java 17 and PostgreSQL.
2. Create a PostgreSQL database named `campus_lostandfound`.
3. Open the project folder in VS Code.
4. Check the database username and password in `application.properties`.
5. Open the terminal in the project folder.
6. Run the following command:

```powershell
.\mvnw.cmd spring-boot:run
```

7. The application will start on:

`http://localhost:8080`

## Main Features

- Manage item categories, posts, custodians, reporters, and item reports.
- Search reports using derived queries and JPQL.
- Validate item report dates and prevent duplicate reports.
- Update report statuses safely.
- Assign item categories to custodians.
- Change custodian posts.
- Manage return receipts.
- View item report statistics by status.
- Cancel reports for a custodian on a specific date.

## API Testing

The API endpoints can be tested using Postman.