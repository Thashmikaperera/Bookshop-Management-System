package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.bookshop.Regex.PatternRegex;
import lk.ijse.bookshop.dto.Customer;
import lk.ijse.bookshop.dto.Employee;
import lk.ijse.bookshop.dto.tm.CustomerTM;
import lk.ijse.bookshop.dto.tm.EmployeeTM;
import lk.ijse.bookshop.model.CustomerModel;
import lk.ijse.bookshop.model.EmployeeModel;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeFormController {
    public JFXTextField txtEmployeeId;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmployeeAddress;
    public JFXTextField txtEmployeeContact;
    public JFXTextField txtEmployeeEmail;
    public JFXTextField txtEmployeeNic;

    public TableView<EmployeeTM> tblEmployee;
    public TableColumn<EmployeeTM,String> colEmployeeName;
    public TableColumn<EmployeeTM,String> colEmployeeAddress;
    public TableColumn<EmployeeTM,String> colContact;
    public JFXButton btnClear;
    public JFXButton btnSearch;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;

    public void initialize(){
        setTableData();
        identifyDataForColumns();
    }

    public void btnSaveOnAction(ActionEvent actionEvent){
        Employee employee=collectData();
        try {
            boolean b = EmployeeModel.addEmployee(employee);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Employee Added").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee adding failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        Employee employee=collectData();
        boolean isUpdated=EmployeeModel.updateEmployee(employee);
        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Employee Update Successful").show();
            setTableData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Employee Update Failed").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Do You want to delete this Employee from the system",
                ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType=alert.showAndWait();
        if (!buttonType.get().equals(ButtonType.YES)){
            return;
        }
        String id=txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Employee Deleted").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee Delete Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void btnClearOnAction(ActionEvent actionEvent){
        clear();
    }
    public void clear(){
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeContact.clear();
        txtEmployeeEmail.clear();
        txtEmployeeNic.clear();
    }
    public Employee collectData(){
        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtEmployeeContact.getText();
        String email = txtEmployeeEmail.getText();
        String nic = txtEmployeeNic.getText();

        Employee employee=new Employee(id,name,address,contact,email,nic);
        return employee;
    }

    public ArrayList<EmployeeTM>getDataForTable(){
        ArrayList<EmployeeTM> list=new ArrayList<>();
        try {
            ArrayList<Employee> all = EmployeeModel.getAll();
            for(Employee ob:all){
                list.add(new EmployeeTM(ob.getEmployeeId(), ob.getEmployeeName(), ob.getEmployeeAddress(),
                        ob.getContact(),ob.getEmail(),ob.getNic()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void identifyDataForColumns(){
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }

    public  void setTableData(){
        ArrayList<EmployeeTM> dataForTable =getDataForTable();
        ObservableList<EmployeeTM> employeeTMS= FXCollections.observableArrayList(dataForTable);
        tblEmployee.setItems(employeeTMS);
    }

    public void tblEmployeeClickAction(MouseEvent mouseEvent){
        if (tblEmployee.getSelectionModel().getSelectedIndex()==-1)return;
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        txtEmployeeId.setText(selectedItem.getEmployeeId());

    }

    public void btnSearchOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            Employee employee = EmployeeModel.searchEmployee(txtEmployeeId.getText());
            if (employee == null){
                new Alert(Alert.AlertType.ERROR,"Employee not found!",ButtonType.CANCEL).show();
            }else {
                filldata(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filldata(Employee employee) {
        txtEmployeeName.setText(employee.getEmployeeName());
        txtEmployeeAddress.setText(employee.getEmployeeAddress());
        txtEmployeeContact.setText(employee.getContact());
        txtEmployeeEmail.setText(employee.getEmail());
        txtEmployeeNic.setText(employee.getNic());
    }

    public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {
        if (!txtEmployeeName.getText().matches(PatternRegex.getNamePattern().pattern())) {
            txtEmployeeName.setStyle("-fx-text-fill: Red;");
        } else txtEmployeeName.setStyle("-fx-text-fill: Black;");
    }

    public void txtEmployeeNicOnKeyReleased(KeyEvent keyEvent) {
        if (!txtEmployeeNic.getText().matches(PatternRegex.getIdPattern().pattern())) {
            txtEmployeeNic.setStyle("-fx-text-fill: Red;");
        } else txtEmployeeNic.setStyle("-fx-text-fill: Black;");
    }

    public void txtEmployeeAddressOnKeyReleased(KeyEvent keyEvent) {
        if (!txtEmployeeAddress.getText().matches(PatternRegex.getAddressPattern().pattern())) {
            txtEmployeeAddress.setStyle("-fx-text-fill: Red;");
        } else txtEmployeeAddress.setStyle("-fx-text-fill: Black;");
    }

    public void txtEmployeeContactOnKeyReleased(KeyEvent keyEvent) {
        if (!txtEmployeeContact.getText().matches(PatternRegex.getMobilePattern().pattern())) {
            txtEmployeeContact.setStyle("-fx-text-fill: Red;");
        } else txtEmployeeContact.setStyle("-fx-text-fill: Black;");
    }

    public void txtEmployeeEmailOnKeyReleased(KeyEvent keyEvent) {
        if (!txtEmployeeEmail.getText().matches(PatternRegex.getEmailPattern().pattern())) {
            txtEmployeeEmail.setStyle("-fx-text-fill: Red;");
        } else txtEmployeeEmail.setStyle("-fx-text-fill: Black;");
    }


}
