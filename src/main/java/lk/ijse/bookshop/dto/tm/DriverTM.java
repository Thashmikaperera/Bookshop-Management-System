package lk.ijse.bookshop.dto.tm;

public class DriverTM {
    private String driverId;
    private String driverName;
    private String address;
    private String contact;
    private String email;

    public DriverTM() {
    }

    public DriverTM(String driverId, String driverName, String address, String contact, String email) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
