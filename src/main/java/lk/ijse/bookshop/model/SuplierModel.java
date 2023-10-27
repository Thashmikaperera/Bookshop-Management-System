package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Suplier;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuplierModel {
    public static boolean addSupplier(Suplier suplier) throws SQLException {
        String sql= "insert into suplier values(?,?,?,?)";
        return CrudUtil.execute(sql,suplier.getSuplierId(),suplier.getSuplierName()
                ,suplier.getContact(),suplier.getEmail());
    }
    public static boolean deleteSupplier(String id) throws SQLException {
        String sql="delete from suplier where suplierId=?";
        return CrudUtil.execute(sql,id);
    }
    public static boolean updateSupplier(Suplier suplier) throws SQLException {
        String sql="update suplier set suplierName=?,contact = ?, email=? where suplierId=?";
        return CrudUtil.execute(sql,suplier.getSuplierName(),suplier.getContact(),
                suplier.getEmail(),suplier.getSuplierId());
    }
    public static Suplier searchSupplier(String id) throws SQLException {
        String sql="select * from suplier where suplierId=?";
        ResultSet rs = CrudUtil.execute(sql,id);
        if(rs.next()){
            String supplierId = rs.getString(1);
            String supplierName = rs.getString(2);
            String contact = rs.getString(3);
            String email = rs.getString(4);
            return new Suplier(supplierId,supplierName,contact,email);
        }
        return null;
    }
    public static ArrayList<Suplier> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("select * from suplier");
        ArrayList<Suplier> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Suplier(rs.getString(1),rs.getString(2),
                    rs.getString(3), rs.getString(4)));
        }
        return list;
    }
}
