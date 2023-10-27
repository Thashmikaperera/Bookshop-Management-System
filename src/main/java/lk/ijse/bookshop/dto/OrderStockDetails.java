package lk.ijse.bookshop.dto;

public class OrderStockDetails {
    private String orderId;
    private String stockId;
    private int qty;

    public OrderStockDetails() {
    }

    public OrderStockDetails(String orderId, String stockId, int qty) {
        this.orderId = orderId;
        this.stockId = stockId;
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
