import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService {

    private final List<Employee> employeeList = new ArrayList<>();

    /**
     * Adds a new employee to the system after full validation and duplicate check.
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new ValidationException("Employee cannot be null.");
        }

        EmployeeValidator.validateEmployee(employee);

        if (isDuplicateId(employee.getEmployeeId())) {
            throw new DuplicateEmployeeException("Employee ID '" + employee.getEmployeeId() + "' already exists.");
        }

        employeeList.add(new Employee(employee));
    }

    /**
     * Retrieves all employees.
     */
    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(employeeList);
    }

    /**
     * Searches for an employee by ID.
     */
    public Optional<Employee> getEmployeeById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        return employeeList.stream()
                .filter(emp -> emp.getEmployeeId().equalsIgnoreCase(id.trim()))
                .findFirst()
                .map(Employee::new); // Defensive copy
    }

    /**
     * Updates an employee record atomically.
     * All updated values are validated BEFORE modifying the existing record.
     * If validation fails, no fields are modified.
     */
    public void updateEmployee(String id, Employee updatedData) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Target Employee ID cannot be empty.");
        }

        if (updatedData == null) {
            throw new ValidationException("Updated employee data cannot be null.");
        }

        Employee existingEmployee = employeeList.stream()
                .filter(emp -> emp.getEmployeeId().equalsIgnoreCase(id.trim()))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID '" + id + "' not found."));

        // Create a candidate employee representation to validate proposed new state
        Employee candidate = new Employee(
                existingEmployee.getEmployeeId(), // ID remains constant
                updatedData.getName(),
                updatedData.getEmail(),
                updatedData.getDepartment(),
                updatedData.getDesignation(),
                updatedData.getJoiningDate()
        );

        // Validate all new values before applying any changes
        EmployeeValidator.validateEmployee(candidate);

        // Atomic application of changes only after successful validation
        existingEmployee.setName(candidate.getName());
        existingEmployee.setEmail(candidate.getEmail());
        existingEmployee.setDepartment(candidate.getDepartment());
        existingEmployee.setDesignation(candidate.getDesignation());
        existingEmployee.setJoiningDate(candidate.getJoiningDate());
    }

    /**
     * Deletes an employee by ID.
     */
    public void deleteEmployee(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Employee ID cannot be empty.");
        }

        boolean removed = employeeList.removeIf(emp -> emp.getEmployeeId().equalsIgnoreCase(id.trim()));

        if (!removed) {
            throw new EmployeeNotFoundException("Employee with ID '" + id + "' not found.");
        }
    }

    /**
     * Sorts the employees by name in place and returns an unmodifiable list of sorted employees.
     */
    public List<Employee> getEmployeesSortedByName() {
        employeeList.sort(Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER));
        return Collections.unmodifiableList(new ArrayList<>(employeeList));
    }

    /**
     * Filters employees by department (case-insensitive).
     */
    public List<Employee> filterByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new ValidationException("Department search term cannot be empty.");
        }

        String searchDept = department.trim();

        return employeeList.stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(searchDept))
                .map(Employee::new)
                .collect(Collectors.toList());
    }

    /**
     * Checks whether an employee ID already exists.
     */
    public boolean isDuplicateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        return employeeList.stream()
                .anyMatch(emp -> emp.getEmployeeId().equalsIgnoreCase(id.trim()));
    }
}