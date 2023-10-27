package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Vehical;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicalModel {
    public static boolean addVehical(Vehical vehical) throws SQLException {
        String sql="insert into vehical values(?,?) ";
        return CrudUtil.execute(sql,vehical.getVehicalId(),vehical.getVehicalType());
    }

    public static boolean deleteVehical(String id) throws SQLException {
        String sql="delete from vehical where vehicalId=?";
        return CrudUtil.execute(sql,id);
    }

    public static boolean updateVehical(Vehical vehical) throws SQLException {
        String sql="update vehical set vehicalType=? where vehicalId=?";
        return CrudUtil.execute(sql,vehical.getVehicalType(),vehical.getVehicalId());
    }

    public static Vehical searchVehical(String id) throws SQLException {
        String sql="select * from vehical where vehicalId=?";
        ResultSet rs = CrudUtil.execute(sql, id);
        if (rs.next()){
            String vehi_id = rs.getString(1);
            String vehi_type = rs.getString(2);

            Vehical vehical=new Vehical(vehi_id,vehi_type);
            return vehical;
        }
        return null;
    }
    public static ArrayList<Vehical> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("select * from vehical");
        ArrayList<Vehical> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Vehical(rs.getString(1),rs.getString(2)));
        }
        return list;
    }
}
