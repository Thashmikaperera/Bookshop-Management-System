package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.OrderStockDetails;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderStockDetailsModel {
   public static boolean addDetails(List<OrderStockDetails> list) throws SQLException {
       for(OrderStockDetails ob : list){
           if(!addDetails(ob)){
               return false;
           }
       }
       return true;
   }
   private static boolean addDetails(OrderStockDetails ob) throws SQLException {
       return CrudUtil.execute("insert into orderstockdetails values(?,?,?)", ob.getOrderId()
               , ob.getStockId(), ob.getQty());
   }
}
