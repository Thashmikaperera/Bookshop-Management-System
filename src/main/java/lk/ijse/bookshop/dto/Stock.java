package lk.ijse.bookshop.dto;

public class Stock {
    private String stockId;
    private String description;
    private Double price;
    private int qty;
    private String suplierId;

    public Stock() {
    }

    public Stock(String stockId, String description, Double price, int qty, String suplierId) {
        this.stockId = stockId;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.suplierId = suplierId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSuplierId() {
        return suplierId;
    }

    public void setSuplierId(String suplierId) {
        this.suplierId = suplierId;
    }
}
