import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;

    @BeforeEach
    void setUp() {
        service = new EmployeeService();
    }

    @Test
    @DisplayName("Add employee successfully")
    void testAddEmployeeSuccess() {
        Employee emp = new Employee("EMP101", "Bob Smith", "bob@example.com", "Sales", "Manager", "2023-05-10");
        service.addEmployee(emp);

        List<Employee> allEmployees = service.getAllEmployees();
        assertEquals(1, allEmployees.size());
        assertEquals("EMP101", allEmployees.get(0).getEmployeeId());
    }

    @Test
    @DisplayName("Adding employee with duplicate ID throws DuplicateEmployeeException")
    void testAddDuplicateEmployeeId() {
        Employee emp1 = new Employee("EMP101", "Bob Smith", "bob@example.com", "Sales", "Manager", "2023-05-10");
        Employee emp2 = new Employee("EMP101", "Alice Doe", "alice@example.com", "HR", "Lead", "2022-01-01");

        service.addEmployee(emp1);

        DuplicateEmployeeException exception = assertThrows(
                DuplicateEmployeeException.class,
                () -> service.addEmployee(emp2)
        );
        assertTrue(exception.getMessage().contains("EMP101"));
    }

    @Test
    @DisplayName("Search employee by ID returns correct Optional")
    void testGetEmployeeById() {
        Employee emp = new Employee("EMP102", "Carol White", "carol@example.com", "Engineering", "Developer", "2024-02-01");
        service.addEmployee(emp);

        Optional<Employee> found = service.getEmployeeById("EMP102");
        assertTrue(found.isPresent());
        assertEquals("Carol White", found.get().getName());

        Optional<Employee> notFound = service.getEmployeeById("NON_EXISTENT");
        assertFalse(notFound.isPresent());
    }

    @Test
    @DisplayName("Atomic update: Valid update succeeds")
    void testUpdateEmployeeSuccess() {
        Employee original = new Employee("EMP103", "David Miller", "david@example.com", "IT", "SysAdmin", "2021-11-20");
        service.addEmployee(original);

        Employee updatedDraft = new Employee("EMP103", "David Miller Jr.", "david.jr@example.com", "IT Infrastructure", "Senior SysAdmin", "2021-11-20");
        service.updateEmployee("EMP103", updatedDraft);

        Optional<Employee> result = service.getEmployeeById("EMP103");
        assertTrue(result.isPresent());
        assertEquals("David Miller Jr.", result.get().getName());
        assertEquals("david.jr@example.com", result.get().getEmail());
        assertEquals("IT Infrastructure", result.get().getDepartment());
        assertEquals("Senior SysAdmin", result.get().getDesignation());
    }

    @Test
    @DisplayName("Atomic update: Invalid field in candidate leaves original employee state completely unmodified")
    void testUpdateEmployeeAtomicFailure() {
        Employee original = new Employee("EMP104", "Emma Stone", "emma@example.com", "Marketing", "Specialist", "2023-03-15");
        service.addEmployee(original);

        // Candidate update with a valid name but INVALID email address
        Employee invalidDraft = new Employee("EMP104", "Emma Watson", "invalid-email-format", "Marketing", "Director", "2023-03-15");

        assertThrows(ValidationException.class, () -> service.updateEmployee("EMP104", invalidDraft));

        // Verify original employee data remains UNCHANGED
        Optional<Employee> checkedOriginal = service.getEmployeeById("EMP104");
        assertTrue(checkedOriginal.isPresent());
        assertEquals("Emma Stone", checkedOriginal.get().getName(), "Name should not have changed");
        assertEquals("emma@example.com", checkedOriginal.get().getEmail(), "Email should not have changed");
        assertEquals("Specialist", checkedOriginal.get().getDesignation(), "Designation should not have changed");
    }

    @Test
    @DisplayName("Update non-existing employee throws EmployeeNotFoundException")
    void testUpdateNonExistingEmployee() {
        Employee updatedDraft = new Employee("EMP999", "Ghost", "ghost@example.com", "HR", "None", "2020-01-01");
        assertThrows(EmployeeNotFoundException.class, () -> service.updateEmployee("EMP999", updatedDraft));
    }

    @Test
    @DisplayName("Delete employee successfully")
    void testDeleteEmployeeSuccess() {
        Employee emp = new Employee("EMP105", "Frank Wright", "frank@example.com", "Finance", "Analyst", "2022-08-10");
        service.addEmployee(emp);

        service.deleteEmployee("EMP105");

        assertFalse(service.getEmployeeById("EMP105").isPresent());
    }

    @Test
    @DisplayName("Delete non-existent employee throws EmployeeNotFoundException")
    void testDeleteNonExistentEmployee() {
        assertThrows(EmployeeNotFoundException.class, () -> service.deleteEmployee("EMP999"));
    }

    @Test
    @DisplayName("Sort employees by name returns properly sorted list")
    void testSortByName() {
        service.addEmployee(new Employee("EMP01", "Charlie", "charlie@example.com", "HR", "Lead", "2020-01-01"));
        service.addEmployee(new Employee("EMP02", "Alice", "alice@example.com", "Sales", "Exec", "2021-01-01"));
        service.addEmployee(new Employee("EMP03", "Bob", "bob@example.com", "IT", "Dev", "2022-01-01"));

        List<Employee> sorted = service.getEmployeesSortedByName();

        assertEquals(3, sorted.size());
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertEquals("Charlie", sorted.get(2).getName());
    }

    @Test
    @DisplayName("Filter by department returns matching employees case-insensitively")
    void testFilterByDepartment() {
        service.addEmployee(new Employee("EMP01", "Charlie", "charlie@example.com", "Engineering", "Lead", "2020-01-01"));
        service.addEmployee(new Employee("EMP02", "Alice", "alice@example.com", "Sales", "Exec", "2021-01-01"));
        service.addEmployee(new Employee("EMP03", "Bob", "bob@example.com", "engineering", "Dev", "2022-01-01"));

        List<Employee> engEmployees = service.filterByDepartment("ENGINEERING");

        assertEquals(2, engEmployees.size());
        assertTrue(engEmployees.stream().allMatch(e -> e.getDepartment().equalsIgnoreCase("Engineering")));
    }
}
