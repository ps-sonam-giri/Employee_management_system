import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        EmployeeService service = new EmployeeService();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
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

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 1:
                    service.addEmployee();
                    break;
                case 2:
                    service.viewEmployees();
                    break;
                case 3:
                    service.searchEmployeeById();
                    break;
                case 4:
                    service.updateEmployee();
                    break;
                case 5:
                    service.deleteEmployee();
                    break;
                case 6:
                    service.sortByName();
                    service.viewEmployees();
                    break;
                case 7:
                    service.filterByDepartment();
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
