package lk.ijse.bookshop.dto;

public class Delivary {
    private String delivaryId;
    private String date;
    private int orderCount;
    private String vehicleId;
    private String driverId;
    private String status;

    public Delivary() {
    }

    public Delivary(String delivaryId, String date, int orderCount, String vehicalId, String driverId, String status) {
        this.delivaryId = delivaryId;
        this.date = date;
        this.orderCount = orderCount;
        this.vehicleId = vehicalId;
        this.driverId = driverId;
        this.status = status;
    }

    public String getDelivaryId() {
        return delivaryId;
    }

    public void setDelivaryId(String delivaryId) {
        this.delivaryId = delivaryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
