package lk.ijse.bookshop.dto.tm;

public class OrderTM {
    private String itemid;
    private String desc;
    private double price;
    private int qty;
    private double total;

    public OrderTM(String itemid, String desc, double price, int qty, double total) {
        this.itemid = itemid;
        this.desc = desc;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
