package lk.ijse.bookshop.dto;

public class Customer {
    private String customerId;
    private String customerName;
    private String address;
    private String contact;
    private String email;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String address, String contact, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
