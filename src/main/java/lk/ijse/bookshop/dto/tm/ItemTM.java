package lk.ijse.bookshop.dto.tm;

public class ItemTM {
    private String id;
    private String description;
    private double price;
    private int qty;
    private String supplierId;

    public ItemTM() {
    }

    public ItemTM(String id, String description, double price, int qty, String supplierId) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.supplierId = supplierId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
