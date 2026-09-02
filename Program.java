import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    private static final EmployeeService service = new EmployeeService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Invalid input. Please enter a valid menu number.");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 1:
                    handleAddEmployee();
                    break;
                case 2:
                    handleViewEmployees();
                    break;
                case 3:
                    handleSearchEmployee();
                    break;
                case 4:
                    handleUpdateEmployee();
                    break;
                case 5:
                    handleDeleteEmployee();
                    break;
                case 6:
                    handleSortByName();
                    break;
                case 7:
                    handleFilterByDepartment();
                    break;
                case 0:
                    System.out.println("\nExiting... Goodbye!");
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n==================================");
        System.out.println("   EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("==================================");
        System.out.println(" 1. Add Employee");
        System.out.println(" 2. View All Employees");
        System.out.println(" 3. Search Employee by ID");
        System.out.println(" 4. Update Employee");
        System.out.println(" 5. Delete Employee");
        System.out.println(" 6. Sort Employees by Name");
        System.out.println(" 7. Filter Employees by Department");
        System.out.println(" 0. Exit");
        System.out.println("==================================");
        System.out.print(" Enter your choice: ");
    }

    private static void handleAddEmployee() {
        System.out.println("\n--- Add Employee ---");
        try {
            System.out.print("Enter Employee ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Email (e.g. user@example.com): ");
            String email = scanner.nextLine();

            System.out.print("Enter Department: ");
            String department = scanner.nextLine();

            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Enter Joining Date (yyyy-MM-dd): ");
            String joiningDate = scanner.nextLine();

            Employee employee = new Employee(id, name, email, department, designation, joiningDate);
            service.addEmployee(employee);
            System.out.println("\n[SUCCESS] Employee Added Successfully.");
        } catch (ValidationException | DuplicateEmployeeException e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }

    private static void handleViewEmployees() {
        System.out.println("\n--- Employee List ---");
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No Employees Found.");
            return;
        }
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void handleSearchEmployee() {
        System.out.println("\n--- Search Employee by ID ---");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        Optional<Employee> employeeOpt = service.getEmployeeById(id);
        if (employeeOpt.isPresent()) {
            System.out.println(employeeOpt.get());
        } else {
            System.out.println("\n[ERROR] Employee Not Found.");
        }
    }

    private static void handleUpdateEmployee() {
        System.out.println("\n--- Update Employee ---");
        System.out.print("Enter Employee ID to Update: ");
        String id = scanner.nextLine();

        Optional<Employee> existingOpt = service.getEmployeeById(id);
        if (!existingOpt.isPresent()) {
            System.out.println("\n[ERROR] Employee with ID '" + id + "' Not Found.");
            return;
        }

        Employee existing = existingOpt.get();
        System.out.println("Target Employee Found: " + existing.getName());

        try {
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter New Email (e.g. user@example.com): ");
            String email = scanner.nextLine();

            System.out.print("Enter New Department: ");
            String department = scanner.nextLine();

            System.out.print("Enter New Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Enter New Joining Date (yyyy-MM-dd): ");
            String joiningDate = scanner.nextLine();

            Employee updatedDraft = new Employee(id, name, email, department, designation, joiningDate);
            service.updateEmployee(id, updatedDraft);
            System.out.println("\n[SUCCESS] Employee Updated Successfully.");
        } catch (ValidationException e) {
            System.out.println("\n[ERROR] Update failed: " + e.getMessage());
            System.out.println("[INFO] No changes were made to the employee record.");
        }
    }

    private static void handleDeleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        System.out.print("Enter Employee ID to Delete: ");
        String id = scanner.nextLine();

        try {
            service.deleteEmployee(id);
            System.out.println("\n[SUCCESS] Employee Deleted Successfully.");
        } catch (ValidationException | EmployeeNotFoundException e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }

    private static void handleSortByName() {
        System.out.println("\n--- Sorting Employees by Name ---");
        List<Employee> sortedList = service.getEmployeesSortedByName();
        if (sortedList.isEmpty()) {
            System.out.println("No Employees Found.");
            return;
        }
        for (Employee employee : sortedList) {
            System.out.println(employee);
        }
        System.out.println("\n[SUCCESS] Employees Sorted and Displayed Successfully.");
    }

    private static void handleFilterByDepartment() {
        System.out.println("\n--- Filter Employees by Department ---");
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        try {
            List<Employee> filteredList = service.filterByDepartment(department);
            if (filteredList.isEmpty()) {
                System.out.println("\nNo Employees Found matching department '" + department + "'.");
                return;
            }
            for (Employee employee : filteredList) {
                System.out.println(employee);
            }
        } catch (ValidationException e) {
            System.out.println("\n[ERROR] " + e.getMessage());
        }
    }
}
