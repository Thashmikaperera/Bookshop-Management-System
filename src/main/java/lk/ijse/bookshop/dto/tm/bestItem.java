package lk.ijse.bookshop.dto.tm;

public class bestItem {
    private String itemid;
    private int count;

    public bestItem(String itemid, int count) {
        this.itemid = itemid;
        this.count = count;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
