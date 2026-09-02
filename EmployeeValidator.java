import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class EmployeeValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9]+([._%+-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$");

    private static final Pattern ID_PATTERN =
            Pattern.compile("^[A-Za-z0-9_-]+$");

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z\\s.'-]+$");

    private static final Pattern TEXT_FIELD_PATTERN =
            Pattern.compile("^[A-Za-z0-9\\s.'-]+$");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void validateEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new ValidationException("Employee ID cannot be empty.");
        }
        if (!ID_PATTERN.matcher(employeeId.trim()).matches()) {
            throw new ValidationException("Employee ID must contain only alphanumeric characters, hyphens, or underscores.");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty.");
        }
        if (name.trim().length() < 2 || name.trim().length() > 100) {
            throw new ValidationException("Name length must be between 2 and 100 characters.");
        }
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            throw new ValidationException("Name contains invalid characters.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Invalid Email format. Example of a valid email: user@example.com");
        }
    }

    public static void validateDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new ValidationException("Department cannot be empty.");
        }
        if (!TEXT_FIELD_PATTERN.matcher(department.trim()).matches()) {
            throw new ValidationException("Department contains invalid characters.");
        }
    }

    public static void validateDesignation(String designation) {
        if (designation == null || designation.trim().isEmpty()) {
            throw new ValidationException("Designation cannot be empty.");
        }
        if (!TEXT_FIELD_PATTERN.matcher(designation.trim()).matches()) {
            throw new ValidationException("Designation contains invalid characters.");
        }
    }

    public static void validateJoiningDate(String joiningDate) {
        if (joiningDate == null || joiningDate.trim().isEmpty()) {
            throw new ValidationException("Joining Date cannot be empty.");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(joiningDate.trim(), DATE_FORMATTER);
            // Verify date formatting string match to prevent loose parsing (e.g. 2023-2-5 vs 2023-02-05)
            if (!parsedDate.format(DATE_FORMATTER).equals(joiningDate.trim())) {
                throw new ValidationException("Joining Date must be in exact 'yyyy-MM-dd' format (e.g., 2024-01-15).");
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid Joining Date format or calendar date. Must be 'yyyy-MM-dd'.");
        }
    }

    /**
     * Validates all fields of an Employee object. Throws ValidationException if any field is invalid.
     */
    public static void validateEmployee(Employee employee) {
        if (employee == null) {
            throw new ValidationException("Employee record cannot be null.");
        }
        validateEmployeeId(employee.getEmployeeId());
        validateName(employee.getName());
        validateEmail(employee.getEmail());
        validateDepartment(employee.getDepartment());
        validateDesignation(employee.getDesignation());
        validateJoiningDate(employee.getJoiningDate());
    }
}
