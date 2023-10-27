package lk.ijse.bookshop.dto;

public class Orders {
    private String orderId;
    private String orderDate;
    private Double price;
    private Double income;
    private String userName;
    private String customerId;
    private String delivaryId;

    public Orders() {
    }

    public Orders(String orderId, String orderDate, Double price, Double income, String userName, String customerId, String delivaryId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.price = price;
        this.income = income;
        this.userName = userName;
        this.customerId = customerId;
        this.delivaryId = delivaryId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDelivaryId() {
        return delivaryId;
    }

    public void setDelivaryId(String delivaryId) {
        this.delivaryId = delivaryId;
    }
}
