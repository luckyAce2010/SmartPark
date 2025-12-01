# SmartPark - Parking Management System

SmartPark is a Java Spring Boot application for managing parking lots and vehicles. It allows registering parking lots and vehicles, checking vehicles in and out, and viewing current occupancy.

---

## Prerequisites

* Java 17+
* Maven 3.6.3+
* An IDE (IntelliJ, Eclipse, or VS Code)
* Optional: Postman for testing APIs

---

## Getting Started

### 1. Clone the repository

```bash
git clone [your-repo-url]
cd smartpark
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The application will start on **[http://localhost:8080](http://localhost:8080)** by default.

---

## API Endpoints & Example Requests

### 1. Register Parking Lot

**POST** `/smart-park/parking-lots`

---

### 2. Register Vehicle

**POST** `/smart-park/vehicles`

---

### 3. Check-In Vehicle

**POST** `/smart-park/parking-lots/check-in`

---

### 4. Check-Out Vehicle

**POST** `/smart-park/parking-lots/check-out`

---

### 5. Get Vehicles in Lot

**GET** `/smartpark/parking-lots/{lotId}/vehicles`

---

### 6. Get Lot Availability

**GET** `/smart-park/parking-lots/availability`

---

### 7. Get Vehicle List In Lot

**GET** `/smart-park/parking-lots/vehicles`

---

## Running Tests

To run unit tests:

```bash
mvn test
```

All tests are located under `src/test/java`.

---

## Project Structure

```text
src/main/java
 └── com.example.SmartPark
      ├── constants   # App Constants
      ├── controller  # API endpoints
      ├── data        # In memory data
      ├── dto         # Request and Response
      ├── enums       # App Enums
      ├── exception   # Custom Exceptions
      ├── pojo        # App POJOs
      ├── service     # Service Implementations and Interfaces
      └── utils       # Utility classes
```

---

## Notes

* This project does **not use a database**; it stores data in memory (for learning/demo purposes).
* Restarting the application will lose all the data
* All IDs and strings are trimmed automatically using `StringUtils`.
* Validation is included using **Jakarta Validation**

---
