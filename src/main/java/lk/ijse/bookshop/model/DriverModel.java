package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Driver;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {
    public static boolean addDriver(Driver driver) throws SQLException {
        String sql="insert into driver values(?,?,?,?,?)";
        return CrudUtil.execute(sql,driver.getDriverId(),driver.getDriverName(),
                driver.getAddress(),driver.getContact(),driver.getEmail());
    }
    public static boolean deleteDriver(String id) throws SQLException {
        String sql="Delete from driver where driverId=?";
        return CrudUtil.execute(sql,id);
    }
    public static boolean updateDriver(Driver driver) throws SQLException {
        String sql="Update driver set driverName=?,address=?,contact=?,email=? where DriverId=?";
        return CrudUtil.execute(sql,driver.getDriverName(),driver.getAddress(),driver.getContact(),driver.getEmail(),driver.getDriverId());
    }
    public static Driver searchDriver(String id) throws SQLException {
        String sql="select * from driver where DriverId=?";
        ResultSet rs = CrudUtil.execute(sql, id);
        if (rs.next()){
            String dri_id = rs.getString(1);
            String dri_name = rs.getString(2);
            String dri_address = rs.getString(3);
            String dri_contact = rs.getString(4);
            String dri_email = rs.getString(5);
            Driver driver=new Driver(dri_id,dri_name,dri_address,dri_contact,dri_email);
            return driver;
        }
        return null;
    }

    public static ArrayList<Driver> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("select * from driver");
        ArrayList<Driver> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Driver(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
        return list;
    }
}