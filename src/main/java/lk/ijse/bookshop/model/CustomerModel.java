package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Customer;
import lk.ijse.bookshop.dto.tm.bestCustomer;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public static boolean addCustomer(Customer customer) throws SQLException {
        String sql= "insert into customer values(?,?,?,?,?)";
        return CrudUtil.execute(sql,customer.getCustomerId(),customer.getCustomerName()
            ,customer.getAddress(),customer.getContact(),customer.getEmail());
    }
    public static boolean deleteCustomer(String id) throws SQLException {
        String sql="delete from customer where customerId=?";
        return CrudUtil.execute(sql,id);
    }
    public static boolean updateCustomer(Customer customer) throws SQLException {
        String sql="update customer set customerName=?,address=?,contact=?,email=? where customerId=?";
        return CrudUtil.execute(sql,customer.getCustomerName()
           ,customer.getAddress(),customer.getContact(),customer.getEmail(),customer.getCustomerId());
    }
    public static Customer searchCustomer(String id) throws SQLException {
        String sql="select * from customer where customerId=?";
        ResultSet rs = CrudUtil.execute(sql,id);
        if(rs.next()){
            String cus_id = rs.getString(1);
            String cus_name = rs.getString(2);
            String cus_address = rs.getString(3);
            String cus_contact = rs.getString(4);
            String cus_email = rs.getString(5);

            Customer customer=new Customer(cus_id,cus_name,cus_address,cus_contact,cus_email);
            return customer;
        }
        return null;
    }
    public static ArrayList<Customer> getAll() throws SQLException {
        ResultSet rs = CrudUtil.execute("select * from customer");
        ArrayList<Customer> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Customer(rs.getString(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5)));
        }
        return list;
    }

    public static bestCustomer findBestCustomer() throws SQLException {
        String sql="SELECT customerId, num_orders FROM (SELECT customer.customerId, COUNT(orders.orderId) as num_orders\n" +
                "         FROM customer LEFT JOIN orders ON customer.customerId = orders.customerId\n" +
                "         GROUP BY customer.customerId) as order_counts WHERE num_orders = (SELECT MAX(num_orders)\n" +
                "         FROM (SELECT COUNT(orders.orderId) as num_orders FROM customer LEFT JOIN orders\n" +
                "         ON customer.customerId = orders.customerId GROUP BY customer.customerId) as order_counts);";
        ResultSet rs = CrudUtil.execute(sql);
        if(rs.next()){
            String id = rs.getString(1);
            int count = rs.getInt(2);

            bestCustomer customer=new bestCustomer(id,count);
            return customer;
        }
        return null;
    }
}
