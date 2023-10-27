package lk.ijse.bookshop.model;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lk.ijse.bookshop.db.DbConnection;
import lk.ijse.bookshop.dto.Delivary;
import lk.ijse.bookshop.dto.OrderStockDetails;
import lk.ijse.bookshop.dto.Orders;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean placeOrder(Orders order, List<OrderStockDetails> list) throws SQLException {
        DbConnection.getDbConnection().getConnection().setAutoCommit(false);
        try {
            boolean execute = CrudUtil.execute("insert into orders values(?,?,?,?,?,?,?)",
                    order.getOrderId(), order.getOrderDate(), order.getPrice(),
                    order.getIncome(), order.getUserName(), order.getCustomerId(),
                    order.getDelivaryId());
            if(execute){
                boolean isDataAdded = OrderStockDetailsModel.addDetails(list);
                if(isDataAdded){
                    boolean isQtuUpdated = StockModel.reduceQty(list);
                    if(isQtuUpdated){
                        DbConnection.getDbConnection().getConnection().commit();
                        return true;
                    }
                }
            }
            DbConnection.getDbConnection().getConnection().rollback();
        } catch (SQLException e) {
            DbConnection.getDbConnection().getConnection().rollback();
            e.printStackTrace();
        } finally {
            DbConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
        return false;
    }

    public static List<Orders> findOrdersDyDeliveryId(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM orders WHERE delivaryId = ?", id);
        ArrayList<Orders> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Orders(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7)));
        }
        return list;
    }

    public static String getNewOrderId() throws SQLException, ClassNotFoundException {
        String lastOrderId=getLastOrderId();
        if(lastOrderId==null){
            return "O-001";
        }else{
            String[] split=lastOrderId.split("[O][-]");
            int lastDigits=Integer.parseInt(split[1]);
            lastDigits++;
            String newOrderId=String.format("O-%03d", lastDigits);
            return newOrderId;
        }
    }

    private static String getLastOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT orderId from orders order by orderId DESC limit 1");
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }
    public static Integer[] geOrderValueMonths() throws SQLException {
        Integer[] data = new Integer[12];
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;

        String sql = "SELECT MONTH(orderDate), COUNT(orderId) FROM orders GROUP BY MONTH(orderDate) ";

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            switch (resultSet.getString(1)) {
                case "1":
                    jan = Integer.parseInt(resultSet.getString(2));
                    break;
                case "2":
                    feb = Integer.parseInt(resultSet.getString(2));
                    break;
                case "3":
                    mar = Integer.parseInt(resultSet.getString(2));
                    break;
                case "4":
                    apr = Integer.parseInt(resultSet.getString(2));
                    break;
                case "5":
                    may = Integer.parseInt(resultSet.getString(2));
                    break;
                case "6":
                    jun = Integer.parseInt(resultSet.getString(2));
                    break;
                case "7":
                    jul = Integer.parseInt(resultSet.getString(2));
                    break;
                case "8":
                    aug = Integer.parseInt(resultSet.getString(2));
                    break;
                case "9":
                    sep = Integer.parseInt(resultSet.getString(2));
                    break;
                case "10":
                    oct = Integer.parseInt(resultSet.getString(2));
                    break;
                case "11":
                    nov = Integer.parseInt(resultSet.getString(2));
                    break;
                case "12":
                    dec = Integer.parseInt(resultSet.getString(2));
                    break;

            }

            data[0] = jan;
            data[1] = feb;
            data[2] = mar;
            data[3] = apr;
            data[4] = may;
            data[5] = jun;
            data[6] = jul;
            data[7] = aug;
            data[8] = sep;
            data[9] = oct;
            data[10] = nov;
            data[11] = dec;
        }
        return data;
    }

    public static String getTodaySales() throws SQLException {
        String sql = "SELECT SUM(price) FROM orders WHERE orderDate = ?";
        ResultSet resultSet=CrudUtil.execute(sql, LocalDate.now());

        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return "000.00";
    }

    public static String getTodaySalesCount() throws SQLException {
        String sql = "SELECT COUNT(orderId) FROM orders WHERE orderDate = ?";
        ResultSet resultSet=CrudUtil.execute(sql, LocalDate.now());

        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return "0";
    }
}
