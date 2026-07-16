# Employee Management System

[![GitHub](https://img.shields.io/badge/GitHub-ps--sonam--giri%2FEmployee__management__system-blue?logo=github)](https://github.com/ps-sonam-giri/Employee_management_system)

A console-based Employee Management System built in Java. It allows you to add, view, search, update, delete, sort, and filter employee records through an interactive menu.

---

## Features

- Add new employees with validation (unique ID, valid email)
- View all employees
- Search employee by ID
- Update employee details
- Delete an employee
- Sort employees alphabetically by name
- Filter employees by department

---

## Project Structure

```
Employee Management System/
├── Employee.java          # Employee model (fields, getters, setters, toString)
├── EmployeeService.java   # Business logic (CRUD, sort, filter, validation)
└── Program.java           # Entry point with interactive menu loop
```

---

## Prerequisites

- Java JDK 8 or higher
- Java added to your system PATH

To verify Java is installed, run:
```
java -version
javac -version
```

If Java is not installed, download it from [Adoptium](https://adoptium.net) or [Oracle](https://www.oracle.com/java/technologies/downloads/).

---

## How to Run

**1. Open a terminal in the project folder:**
```
cd "path/to/Employee Management System"
```

**2. Compile all Java files:**
```
javac Employee.java EmployeeService.java Program.java
```

**3. Run the program:**
```
java Program
```

---

## Menu Options

```
==================================
   EMPLOYEE MANAGEMENT SYSTEM
==================================
 1. Add Employee
 2. View All Employees
 3. Search Employee by ID
 4. Update Employee
 5. Delete Employee
 6. Sort Employees by Name
 7. Filter Employees by Department
 0. Exit
==================================
```

---

## Employee Fields

| Field        | Description                        |
|--------------|------------------------------------|
| Employee ID  | Unique identifier (cannot be empty)|
| Name         | Full name of the employee          |
| Email        | Must be a valid email format       |
| Department   | Department the employee belongs to |
| Designation  | Job title / role                   |
| Joining Date | Date the employee joined           |

---

## Validation Rules

- **Employee ID** — must be non-empty and unique
- **Name** — must be non-empty
- **Email** — must match standard email format (e.g. `user@example.com`)
- If an invalid email is entered during update, the old email is retained

---

## Sample Usage

```
Enter Employee ID: EMP001
Enter Name: Alice Johnson
Enter Email: alice@example.com
Enter Department: Engineering
Enter Designation: Software Engineer
Enter Joining Date: 2024-01-15
Employee Added Successfully.
```

---

## Tech Stack

- **Language:** Java
- **Data Storage:** In-memory (`ArrayList`) — data is not persisted between sessions
- **I/O:** Console (`Scanner`)
