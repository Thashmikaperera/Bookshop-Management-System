package lk.ijse.bookshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;

    private final Connection connection;

    public DbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/bookshop","root","1234");
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException("Failed to load the database");
        }

    }

    public static DbConnection getDbConnection() {
        return dbConnection==null?dbConnection=new DbConnection():dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }


}
