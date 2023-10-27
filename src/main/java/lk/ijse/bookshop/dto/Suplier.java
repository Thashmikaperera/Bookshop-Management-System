package lk.ijse.bookshop.dto;

public class Suplier {
    private String suplierId;
    private String suplierName;
    private String contact;
    private String email;

    public Suplier() {
    }

    public Suplier(String suplierId, String suplierName, String contact, String email) {
        this.suplierId = suplierId;
        this.suplierName = suplierName;
        this.contact = contact;
        this.email = email;
    }

    public String getSuplierId() {
        return suplierId;
    }

    public void setSuplierId(String suplierId) {
        this.suplierId = suplierId;
    }

    public String getSuplierName() {
        return suplierName;
    }

    public void setSuplierName(String suplierName) {
        this.suplierName = suplierName;
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
