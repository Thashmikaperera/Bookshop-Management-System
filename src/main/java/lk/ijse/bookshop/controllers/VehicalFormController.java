package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bookshop.dto.Vehical;
import lk.ijse.bookshop.dto.tm.VehicleTM;
import lk.ijse.bookshop.model.VehicalModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class VehicalFormController {
    public JFXTextField txtVehicleId;
    public JFXTextField txtVehicleType;
    public TableView<VehicleTM> tblVehicle;
    public TableColumn<VehicleTM,String> colVehicleId;
    public TableColumn<VehicleTM,String> colVehicleType;
    public Button btnSearch;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnSave;
    public Button btnClear;
    public AnchorPane ancTextBackground;
    public AnchorPane mainAnc;

    public void initialize(){
        showAll();
        loadAll();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Vehical vehical = collectData();
        try {
            boolean b = VehicalModel.addVehical(vehical);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Operation Success").show();
                loadAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Failed").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ? ", ButtonType.YES
                , ButtonType.NO).showAndWait();
        if(buttonType.isPresent()){
            if(buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean b = VehicalModel.deleteVehical(txtVehicleId.getText());
                    if(b){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                        loadAll();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Failed").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Vehical vehical = collectData();
        try {
            boolean b = VehicalModel.updateVehical(vehical);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
                loadAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Updating Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Vehical collectData(){
        return new Vehical(txtVehicleId.getText(),txtVehicleType.getText());
    }

    public void loadAll(){
        try {
            ArrayList<Vehical> all = VehicalModel.getAll();
            ObservableList<VehicleTM> objects = FXCollections.observableArrayList();
            for (Vehical vehical : all){
                objects.add(new VehicleTM(vehical.getVehicalId(),vehical.getVehicalType()));
            }
            tblVehicle.setItems(objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAll(){
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Vehical vehical = VehicalModel.searchVehical(txtVehicleId.getText());
            if (vehical == null){
                new Alert(Alert.AlertType.ERROR,"Vehicle not Found!", ButtonType.CANCEL).show();
            }else {
                filldata(vehical);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void filldata(Vehical vehical){
        txtVehicleType.setText(vehical.getVehicalType());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void clear(){
        txtVehicleId.clear();
        txtVehicleType.clear();
    }

    public void tblVehicalClickAction(MouseEvent mouseEvent) {
        if (tblVehicle.getSelectionModel().getSelectedIndex()==-1)return;
        VehicleTM selectedItem = tblVehicle.getSelectionModel().getSelectedItem();
        txtVehicleId.setText(selectedItem.getId());
    }
}
