package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bookshop.dto.Customer;
import lk.ijse.bookshop.dto.Driver;
import lk.ijse.bookshop.dto.tm.CustomerTM;
import lk.ijse.bookshop.dto.tm.DriverTM;
import lk.ijse.bookshop.model.CustomerModel;
import lk.ijse.bookshop.model.DriverModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class DriverFormController {
    public AnchorPane mainAnc;
    public Label lblMain;
    public AnchorPane ancTextBackground;
    public JFXTextField txtDriverId;
    public JFXTextField txtDriverName;
    public JFXTextField txtDriverAddress;
    public JFXTextField txtDriverContact;
    public JFXTextField txtDriverEmail;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableColumn<DriverTM,String> colDriverId;
    public TableView<DriverTM> tblDrivers;
    public TableColumn <DriverTM,String>colDriverName;
    public TableColumn <DriverTM,String>colContact;
    public TableColumn <DriverTM,String>colEmail;
    public Button btnSearch;

    public void initialize(){
        setTable();
        visualize();
    }

    public void setTable(){
        ArrayList<DriverTM> dataForTable = getDataForTable();
        ObservableList<DriverTM> driverTMS = FXCollections.observableArrayList(dataForTable);
        tblDrivers.setItems(driverTMS);
    }

    public void visualize(){
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Driver driver=collectData();
        try {
            boolean b = DriverModel.addDriver(driver);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Driver add successful").show();
                setTable();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Driver not added").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
//        if(!txtDriverId.isDisable()){
//            new Alert(Alert.AlertType.ERROR,"First Search Driver").show();
//            return ;
//        }
        Driver  driver= collectData();
        try {
            boolean isUpdate = DriverModel.updateDriver(driver);
            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"driver update Successful").show();
                setTable();
                clear();
            }else{
                new Alert(Alert.AlertType.ERROR,"Driver Update Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
//        if(!txtDriverId.isDisable()){
//            new Alert(Alert.AlertType.ERROR,"Driver First Search").show();
//            return;
//        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete"
                ,ButtonType.YES,ButtonType.NO).showAndWait();
        if(!buttonType.get().equals(ButtonType.YES)){
            return;
        }
        String id = txtDriverId.getText();

        try {
            boolean isDelete = DriverModel.deleteDriver(id);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Driver Delete Successful").show();
                setTable();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Driver Delete Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Driver collectData(){
        String dri_id = txtDriverId.getText();
        String dri_name = txtDriverName.getText();
        String dri_add = txtDriverAddress.getText();
        String dri_con = txtDriverContact.getText();
        String dri_email = txtDriverEmail.getText();

        Driver driver=new Driver(dri_id,dri_name,dri_add,dri_con,dri_email);
        return driver;
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }
    public void clear(){
        txtDriverId.clear();
        txtDriverName.clear();
        txtDriverAddress.clear();
        txtDriverContact.clear();
        txtDriverEmail.clear();
    }

    public ArrayList<DriverTM> getDataForTable(){
        ArrayList<DriverTM> list=new ArrayList<>();
        try {
            ArrayList<Driver> all = DriverModel.getAll();
            for (Driver ob : all){
                list.add(new DriverTM(ob.getDriverId(), ob.getDriverName(), ob.getAddress()
                        , ob.getContact(), ob.getEmail()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Driver driver = DriverModel.searchDriver(txtDriverId.getText());
            if (driver == null){
                new Alert(Alert.AlertType.ERROR,"Driver Not Found!",ButtonType.CANCEL).show();
            }else {
                filldata(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void filldata(Driver driver){
        txtDriverName.setText(driver.getDriverName());
        txtDriverAddress.setText(driver.getAddress());
        txtDriverContact.setText(driver.getContact());
        txtDriverEmail.setText(driver.getEmail());
    }

    public void tblDriverClickAction(MouseEvent mouseEvent) {
        if (tblDrivers.getSelectionModel().getSelectedIndex()==-1)return;
        DriverTM selectedItem = tblDrivers.getSelectionModel().getSelectedItem();
        txtDriverId.setText(selectedItem.getDriverId());
    }
}
