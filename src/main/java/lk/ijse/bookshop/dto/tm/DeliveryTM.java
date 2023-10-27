package lk.ijse.bookshop.dto.tm;

public class DeliveryTM {
    private String orderId;
    private String customerId;

    public DeliveryTM() {

    }

    public DeliveryTM(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
