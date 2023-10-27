package lk.ijse.bookshop.dto;

public class User {
    private String userName;
    private String userPassword;
    private String hint;
    private String employeeId;

    public User() {
    }

    public User(String userName, String userPassword, String hint, String employeeId) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.hint = hint;
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
