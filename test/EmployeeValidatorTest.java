import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeValidatorTest {

    @Test
    @DisplayName("Valid employee passes full validation")
    void testValidEmployee() {
        Employee validEmp = new Employee("EMP-101", "Alice Smith", "alice.smith@example.com",
                "Engineering", "Software Engineer", "2024-01-15");
        assertDoesNotThrow(() -> EmployeeValidator.validateEmployee(validEmp));
    }

    @Test
    @DisplayName("Null employee throws ValidationException")
    void testNullEmployee() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> EmployeeValidator.validateEmployee(null));
        assertEquals("Employee record cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"EMP001", "EMP-100", "EMP_200", "E1"})
    @DisplayName("Valid employee IDs pass validation")
    void testValidEmployeeIds(String validId) {
        assertDoesNotThrow(() -> EmployeeValidator.validateEmployeeId(validId));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "EMP 100", "EMP#101", "EMP@1"})
    @DisplayName("Invalid employee IDs throw ValidationException")
    void testInvalidEmployeeIds(String invalidId) {
        assertThrows(ValidationException.class, () -> EmployeeValidator.validateEmployeeId(invalidId));
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Doe", "Jane O'Connor", "Jean-Luc Picard", "Dr. Bob"})
    @DisplayName("Valid employee names pass validation")
    void testValidNames(String validName) {
        assertDoesNotThrow(() -> EmployeeValidator.validateName(validName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "A", "John123", "User@Name"})
    @DisplayName("Invalid names throw ValidationException")
    void testInvalidNames(String invalidName) {
        assertThrows(ValidationException.class, () -> EmployeeValidator.validateName(invalidName));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@domain.com",
            "first.last@sub.domain.co.uk",
            "user+tag@domain.org",
            "emp_123@company.net"
    })
    @DisplayName("Valid emails pass validation")
    void testValidEmails(String validEmail) {
        assertDoesNotThrow(() -> EmployeeValidator.validateEmail(validEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   ",
            "plainaddress",
            "#@%^%#$@#$@#.com",
            "@domain.com",
            "Joe Blow <joe@domain.com>",
            "email.domain.com",
            "email@domain@domain.com",
            "email@domain",
            "email@domain..com"
    })
    @DisplayName("Invalid emails throw ValidationException")
    void testInvalidEmails(String invalidEmail) {
        assertThrows(ValidationException.class, () -> EmployeeValidator.validateEmail(invalidEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-01-15", "2020-02-29", "1999-12-31"})
    @DisplayName("Valid dates in yyyy-MM-dd format pass validation")
    void testValidJoiningDates(String validDate) {
        assertDoesNotThrow(() -> EmployeeValidator.validateJoiningDate(validDate));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   ",
            "15-01-2024",
            "2024/01/15",
            "2024-02-30", // Invalid February date
            "2023-13-01", // Invalid month
            "invalid-date"
    })
    @DisplayName("Invalid joining dates throw ValidationException")
    void testInvalidJoiningDates(String invalidDate) {
        assertThrows(ValidationException.class, () -> EmployeeValidator.validateJoiningDate(invalidDate));
    }
}
