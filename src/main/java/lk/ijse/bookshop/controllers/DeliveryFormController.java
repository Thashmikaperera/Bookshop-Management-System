package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;
import lk.ijse.bookshop.dto.Delivary;
import lk.ijse.bookshop.dto.Driver;
import lk.ijse.bookshop.dto.Vehical;
import lk.ijse.bookshop.model.DelivaryModel;
import lk.ijse.bookshop.model.DriverModel;
import lk.ijse.bookshop.model.VehicalModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DeliveryFormController {

    public JFXComboBox<Vehical> cmbVehicle;
    public JFXComboBox<Driver> cmbDriver;
    public JFXTextField txtDeliveryId;

    public void initialize(){
        loadData();
        setConverters();
    }

    public void btnCreateOnAction(ActionEvent actionEvent) {
        Delivary delivary = collectData();
        try {
            boolean b = DelivaryModel.addDelivery(delivary);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Operation Success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Operation Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadData(){
        try {
            ArrayList<Vehical> all = VehicalModel.getAll();
            cmbVehicle.setItems(FXCollections.observableList(all));

            ArrayList<Driver> all1 = DriverModel.getAll();
            cmbDriver.setItems(FXCollections.observableList(all1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConverters(){
        cmbDriver.setConverter(new StringConverter<Driver>() {
            @Override
            public String toString(Driver driver) {
                return driver.getDriverName();
            }

            @Override
            public Driver fromString(String s) {
                return null;
            }
        });

        cmbVehicle.setConverter(new StringConverter<Vehical>() {
            @Override
            public String toString(Vehical vehical) {
                return vehical.getVehicalId();
            }

            @Override
            public Vehical fromString(String s) {
                return null;
            }
        });
    }

    public Delivary collectData(){
        return new Delivary(txtDeliveryId.getText(), LocalDate.now().toString(),0,
                cmbVehicle.getSelectionModel().getSelectedItem().getVehicalId()
                ,cmbDriver.getSelectionModel().getSelectedItem().getDriverId(),"not delivered");
    }

}
