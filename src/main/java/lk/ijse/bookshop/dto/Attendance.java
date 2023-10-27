package lk.ijse.bookshop.dto;

public class Attendance {
    private String attendanceId;
    private String date;
    private String status;
    private String employeeId;

    public Attendance() {
    }

    public Attendance(String attendanceId, String date, String status, String employeeId) {
        this.attendanceId = attendanceId;
        this.date = date;
        this.status = status;
        this.employeeId = employeeId;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
