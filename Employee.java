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
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.joiningDate = joiningDate;
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
        this.employeeId = employeeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
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