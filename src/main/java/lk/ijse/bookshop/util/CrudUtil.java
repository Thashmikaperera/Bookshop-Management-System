package lk.ijse.bookshop.util;

import lk.ijse.bookshop.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

public class CrudUtil {
    public static <T>T execute(String sql,Object...params) throws SQLException {
        Connection con = DbConnection.getDbConnection().getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        for(int i = 0 ; i < params.length ; i++){
            ps.setObject(i+1,params[i]);
        }
        if(sql.toLowerCase().startsWith("select")){
            return (T)ps.executeQuery();
        }
        return (T)((Boolean)(ps.executeUpdate()>0));
    }

}











