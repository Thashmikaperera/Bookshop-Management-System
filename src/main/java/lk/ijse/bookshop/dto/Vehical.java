package lk.ijse.bookshop.dto;

public class Vehical {
    private String vehicalId;
    private String vehicalType;

    public Vehical(String vehicalId, String vehicalType) {
        this.vehicalId = vehicalId;
        this.vehicalType = vehicalType;
    }

    public String getVehicalId() {
        return vehicalId;
    }

    public void setVehicalId(String vehicalId) {
        this.vehicalId = vehicalId;
    }

    public String getVehicalType() {
        return vehicalType;
    }

    public void setVehicalType(String vehicalType) {
        this.vehicalType = vehicalType;
    }
}


