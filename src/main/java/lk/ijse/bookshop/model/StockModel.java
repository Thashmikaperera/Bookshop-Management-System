package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.OrderStockDetails;
import lk.ijse.bookshop.dto.Stock;
import lk.ijse.bookshop.dto.tm.bestCustomer;
import lk.ijse.bookshop.dto.tm.bestItem;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {
    public static boolean addItem(Stock stock) throws SQLException {
        String sql= "insert into stock values(?,?,?,?,?)";
        return CrudUtil.execute(sql,stock.getStockId(),stock.getDescription()
                ,stock.getPrice(),stock.getQty(),stock.getSuplierId());
    }
    public static boolean deleteItem(String id) throws SQLException {
        String sql="delete from stock where stockId=?";
        return CrudUtil.execute(sql,id);
    }
    public static boolean updateItem(Stock stock) throws SQLException {
        String sql="update stock set description=?,price=?,qty=?,suplierId=? where stockId=?";
        return CrudUtil.execute(sql,stock.getDescription(),stock.getPrice(),
                stock.getQty(),stock.getSuplierId(),stock.getStockId());
    }
    public static Stock searchItem(String id) throws SQLException {
        String sql="select * from stock where stockId=?";
        ResultSet rs = CrudUtil.execute(sql,id);
        if(rs.next()){
            String stockID = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            int qty = rs.getInt(4);
            String supplierId = rs.getString(5);
            return new Stock(stockID,description,price,qty,supplierId);
        }
        return null;
    }
    public static ArrayList<Stock> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("select * from stock");
        ArrayList<Stock> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Stock(rs.getString(1),rs.getString(2),rs.getDouble(3),
                    rs.getInt(4),rs.getString(5)));
        }
        return list;
    }

    public static boolean reduceQty(List<OrderStockDetails> list) throws SQLException {
        for(OrderStockDetails ob : list){
            if(!reduceQty(ob)){
                return false;
            }
        }
        return true;
    }

    private static boolean reduceQty(OrderStockDetails ob) throws SQLException {
        String sql = "update stock set qty = qty-? where stockId = ?";
        return CrudUtil.execute(sql,ob.getQty(),ob.getStockId());
    }

    public static bestItem findBestItem() throws SQLException {
        String sql="SELECT order_counts.stockId, order_counts.num_orders\n" +
                "FROM (SELECT bookshop.orderstockdetails.stockId, SUM(qty) as num_orders\n" +
                "FROM bookshop.orderstockdetails GROUP BY stockId) as order_counts\n" +
                "WHERE order_counts.num_orders = (\n" +
                "SELECT MAX(num_orders)FROM (SELECT SUM(qty) as num_orders\n" +
                "FROM bookshop.orderstockdetails GROUP BY stockId) as subquery);";
        ResultSet rs = CrudUtil.execute(sql);
        if(rs.next()){
            String id = rs.getString(1);
            int count = rs.getInt(2);

            bestItem item=new bestItem(id,count);
            return item;
        }
        return null;
    }
}
