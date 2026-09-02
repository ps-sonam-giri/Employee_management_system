import java.util.Objects;

public class Employee {

    private String employeeId;
    private String name;
    private String email;
    private String department;
    private String designation;
    private String joiningDate;

    // Default Constructor
    public Employee() {
    }

    // Parameterized Constructor
    public Employee(String employeeId, String name, String email,
                    String department, String designation, String joiningDate) {
        this.employeeId = employeeId != null ? employeeId.trim() : null;
        this.name = name != null ? name.trim() : null;
        this.email = email != null ? email.trim() : null;
        this.department = department != null ? department.trim() : null;
        this.designation = designation != null ? designation.trim() : null;
        this.joiningDate = joiningDate != null ? joiningDate.trim() : null;
    }

    // Copy Constructor
    public Employee(Employee other) {
        if (other != null) {
            this.employeeId = other.employeeId;
            this.name = other.name;
            this.email = other.email;
            this.department = other.department;
            this.designation = other.designation;
            this.joiningDate = other.joiningDate;
        }
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    // Setters
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId != null ? employeeId.trim() : null;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : null;
    }

    public void setDepartment(String department) {
        this.department = department != null ? department.trim() : null;
    }

    public void setDesignation(String designation) {
        this.designation = designation != null ? designation.trim() : null;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate != null ? joiningDate.trim() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    // Display Employee Details
    @Override
    public String toString() {
        return "\n----------------------------------" +
               "\nEmployee ID : " + employeeId +
               "\nName        : " + name +
               "\nEmail       : " + email +
               "\nDepartment  : " + department +
               "\nDesignation : " + designation +
               "\nJoining Date: " + joiningDate +
               "\n----------------------------------";
    }
}