package lk.ijse.bookshop.model;

import lk.ijse.bookshop.dto.Employee;
import lk.ijse.bookshop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static boolean addEmployee(Employee employee) throws SQLException {
        String sql="insert into employee values(?,?,?,?,?,?)";
        return CrudUtil.execute(sql,employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmployeeAddress()
           ,employee.getContact(),employee.getEmail(),employee.getNic());

    }
    public static boolean deleteEmployee(String id) throws SQLException {
        String sql="delete from employee where employeeId=?";
        return CrudUtil.execute(sql,id);
    }
    public static boolean updateEmployee(Employee employee) throws SQLException {
        String sql="update employee set EmployeeName=?,employeeAddress=?,contact=?,email=?,nic=? where EmployeeId=?";
        return CrudUtil.execute(sql,employee.getEmployeeName(),employee.getEmployeeAddress(),
                employee.getContact(),employee.getEmail(),employee.getNic(),employee.getEmployeeId());
    }
    public static Employee searchEmployee(String id) throws SQLException {
        String sql="select * from employee where employeeId=?";
        ResultSet rs = CrudUtil.execute(sql, id);
       if(rs.next()){
           String emp_id = rs.getString(1);
           String emp_name = rs.getString(2);
           String emp_address = rs.getString(3);
           String emp_contact = rs.getString(4);
           String emp_email = rs.getString(5);
           String emp_nic = rs.getString(6);

           Employee employee=new Employee(emp_id,emp_name,emp_address
                   ,emp_contact,emp_email,emp_nic);
           return employee;
       }
       return null;
    }

    public static ArrayList<Employee> getAll() throws SQLException {
        ResultSet rs=CrudUtil.execute("select * from employee");
        ArrayList<Employee> list=new ArrayList<>();
        while (rs.next()){
            list.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3)
                    ,rs.getString(4),rs.getString(5),rs.getString(6)));
        }
        return list;
    }

}
