package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Stock;
import lk.ijse.bookshop.dto.User;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean save(User user) throws SQLException {
        String sql= "insert into user values(?,?,?,?)";
        return CrudUtil.execute(sql,user.getUserName(),user.getUserPassword(),user.getHint(),user.getEmployeeId());
    }
    public static User search(String id) throws SQLException {
        String sql="select * from user where userName=?";
        ResultSet rs = CrudUtil.execute(sql,id);
        if(rs.next()){
            return new User(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
            );
        }
        return null;
    }

    public static User searchByEmp(String eid) throws SQLException {
        String sql="select * from user where employeeId=?";
        ResultSet rs = CrudUtil.execute(sql,eid);
        if(rs.next()){
            return new User(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
            );
        }
        return null;
    }

}
