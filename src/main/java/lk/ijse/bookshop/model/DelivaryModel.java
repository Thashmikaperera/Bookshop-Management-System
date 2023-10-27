package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Delivary;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DelivaryModel {
    public static boolean addDelivery(Delivary delivary) throws SQLException {
        return CrudUtil.execute("insert into delivary values(?,?,?,?,?,?)"
        ,delivary.getDelivaryId(),delivary.getDate(),delivary.getOrderCount(),
                delivary.getVehicleId(),delivary.getDriverId(),delivary.getStatus());
    }

    public static boolean updateDelivery(Delivary delivary) throws SQLException {
        return CrudUtil.execute("update delivary set date = ? , orderCount = ? , vehicleId = ? , driverId = ?" +
                "where delivaryId = ?",delivary.getDate(),delivary.getOrderCount(),delivary.getVehicleId(),
                delivary.getDriverId());
    }

    public static boolean deleteDelivery(Delivary delivary) throws SQLException {
        return CrudUtil.execute("delete from delivary where delivaryId = ?",delivary.getDelivaryId());
    }

    public static boolean setDeliveryStatus(String id) throws SQLException {
        return CrudUtil.execute("update delivary set status = ?" +
                        "where delivaryId = ?","delivered",id);
    }


    public static List<Delivary> getNotDeliveredDeliveryList() throws SQLException {
        ResultSet rs = CrudUtil.execute("select * from delivary where status = 'not delivered'");
        ArrayList<Delivary> list = new ArrayList<>();
        while (rs.next()){
            list.add(new Delivary(rs.getString(1),rs.getString(2),rs.getInt(3),
                    rs.getString(4),rs.getString(5),rs.getString(6)));
        }
        return list;
    }
}
