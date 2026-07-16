import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class EmployeeService {

    private ArrayList<Employee> employeeList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Add Employee
    public void addEmployee() {

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }

        if (isDuplicateId(id)) {
            System.out.println("Employee ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        if (!isValidEmail(email)) {
            System.out.println("Invalid Email.");
            return;
        }

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();

        System.out.print("Enter Joining Date: ");
        String joiningDate = scanner.nextLine();

        Employee employee = new Employee(id, name, email, department,
                designation, joiningDate);

        employeeList.add(employee);

        System.out.println("Employee Added Successfully.");
    }

    // View All Employees
    public void viewEmployees() {

        if (employeeList.isEmpty()) {
            System.out.println("No Employees Found.");
            return;
        }

        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }

    // Search Employee
    public void searchEmployeeById() {

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        for (Employee employee : employeeList) {
            if (employee.getEmployeeId().equals(id)) {
                System.out.println(employee);
                return;
            }
        }

        System.out.println("Employee Not Found.");
    }

    // Update Employee
    public void updateEmployee() {

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        for (Employee employee : employeeList) {

            if (employee.getEmployeeId().equals(id)) {

                System.out.print("Enter New Name: ");
                employee.setName(scanner.nextLine());

                System.out.print("Enter New Email: ");
                String email = scanner.nextLine();

                if (isValidEmail(email))
                    employee.setEmail(email);
                else
                    System.out.println("Invalid Email. Old Email Retained.");

                System.out.print("Enter New Department: ");
                employee.setDepartment(scanner.nextLine());

                System.out.print("Enter New Designation: ");
                employee.setDesignation(scanner.nextLine());

                System.out.print("Enter New Joining Date: ");
                employee.setJoiningDate(scanner.nextLine());

                System.out.println("Employee Updated Successfully.");
                return;
            }
        }

        System.out.println("Employee Not Found.");
    }

    // Delete Employee
    public void deleteEmployee() {

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        for (Employee employee : employeeList) {

            if (employee.getEmployeeId().equals(id)) {
                employeeList.remove(employee);
                System.out.println("Employee Deleted Successfully.");
                return;
            }
        }

        System.out.println("Employee Not Found.");
    }

    // Sort by Name
    public void sortByName() {

        Collections.sort(employeeList, Comparator.comparing(Employee::getName));

        System.out.println("Employees Sorted Successfully.");
    }

    // Filter by Department
    public void filterByDepartment() {

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        boolean found = false;

        for (Employee employee : employeeList) {

            if (employee.getDepartment().equalsIgnoreCase(department)) {
                System.out.println(employee);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No Employee Found.");
        }
    }

    // Check Duplicate ID
    private boolean isDuplicateId(String id) {

        for (Employee employee : employeeList) {
            if (employee.getEmployeeId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    // Email Validation
    private boolean isValidEmail(String email) {

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}