package lk.ijse.bookshop.dto.tm;

public class bestCustomer {
    private String custid;
    private int count;

    public bestCustomer(String custid, int count) {
        this.custid = custid;
        this.count = count;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
