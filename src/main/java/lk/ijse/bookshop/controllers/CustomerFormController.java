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
import lk.ijse.bookshop.dto.tm.CustomerTM;
import lk.ijse.bookshop.model.CustomerModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CustomerFormController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtCustomerEmail;

    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM,String> colCustomerName;
    public TableColumn<CustomerTM,String> colCustomerAddress;
    public TableColumn<CustomerTM,String> colContact;
    public TableColumn<CustomerTM,String> colEmail;


    public void initialize(){
        setTableData();
        identifyDataForColumns();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Customer customer = collectData();
        try {
            boolean b = CustomerModel.addCustomer(customer);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Customer Added").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Customer Adding failed").show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
//        if (!txtCustomerId.isDisabled()) {
//            new Alert(Alert.AlertType.WARNING, "First Search Customer").show();
//            return;
//        }
        Customer customer = collectData();
        boolean isUpdated = CustomerModel.updateCustomer(customer);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer updated Successful").show();
            setTableData();
            clear();
        } else {
            new Alert(Alert.AlertType.WARNING, "Customer Update Failed");
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
//        if (!txtCustomerId.isDisabled()) {
//            new Alert(Alert.AlertType.WARNING, "First Search Customer").show();
//            return;
//        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to delete this Customer from the system",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (!buttonType.get().equals(ButtonType.YES)) {
            return;
        }
        String id = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Success").show();
                setTableData();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Delete Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void clear() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerContact.clear();
        txtCustomerEmail.clear();
    }

    public Customer collectData(){
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();
        String email = txtCustomerEmail.getText();

        Customer customer=new Customer(id,name,address,contact,email);
        return customer;
    }

    public ArrayList<CustomerTM> getDataForTable(){
        ArrayList<CustomerTM> list=new ArrayList<>();
        try {
            ArrayList<Customer> all = CustomerModel.getAll();
            for (Customer ob : all){
                list.add(new CustomerTM(ob.getCustomerId(), ob.getCustomerName(), ob.getAddress(),
                        ob.getContact(),ob.getEmail()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void identifyDataForColumns(){
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void setTableData(){
        ArrayList<CustomerTM> dataForTable = getDataForTable();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList(dataForTable);
        tblCustomer.setItems(customerTMS);
    }

    public void tblCustomerClickAction(MouseEvent mouseEvent) {
        if(tblCustomer.getSelectionModel().getSelectedIndex()==-1)return;
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        txtCustomerId.setText(selectedItem.getCustomerId());
//        try {
//            Customer customer = CustomerModel.searchCustomer(selectedItem.getCustomerId());
//            if (customer == null){
//                new Alert(Alert.AlertType.ERROR,"Customer is not found!").show();
//            }else {
//                filldata(customer);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Customer customer = CustomerModel.searchCustomer(txtCustomerId.getText());
            if (customer == null){
                new Alert(Alert.AlertType.ERROR,"Customer not found!",ButtonType.CANCEL).show();
            }else {
                filldata(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filldata(Customer customer) {
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerAddress.setText(customer.getAddress());
        txtCustomerContact.setText(customer.getContact());
        txtCustomerEmail.setText(customer.getEmail());
    }
    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        if (!txtCustomerName.getText().matches(PatternRegex.getNamePattern().pattern())) {
            txtCustomerName.setStyle("-fx-text-fill: Red;");
        } else txtCustomerName.setStyle("-fx-text-fill: Black;");
    }

    public void txtCustomerAddressOnKeyReleased(KeyEvent keyEvent) {
        if (!txtCustomerAddress.getText().matches(PatternRegex.getAddressPattern().pattern())) {
            txtCustomerAddress.setStyle("-fx-text-fill: Red;");
        } else txtCustomerAddress.setStyle("-fx-text-fill: Black;");
    }

    public void txtCustomerContactOnKeyReleased(KeyEvent keyEvent) {
        if (!txtCustomerContact.getText().matches(PatternRegex.getMobilePattern().pattern())) {
            txtCustomerContact.setStyle("-fx-text-fill: Red;");
        } else txtCustomerContact.setStyle("-fx-text-fill: Black;");
    }

    public void txtCustomerEmailOnKeyReleased(KeyEvent keyEvent) {
        if (!txtCustomerEmail.getText().matches(PatternRegex.getEmailPattern().pattern())) {
            txtCustomerEmail.setStyle("-fx-text-fill: Red;");
        } else txtCustomerEmail.setStyle("-fx-text-fill: Black;");
    }
}
