package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bookshop.Regex.PatternRegex;
import lk.ijse.bookshop.dto.Driver;
import lk.ijse.bookshop.dto.Suplier;
import lk.ijse.bookshop.dto.tm.DriverTM;
import lk.ijse.bookshop.dto.tm.SupplierTM;
import lk.ijse.bookshop.model.DriverModel;
import lk.ijse.bookshop.model.SuplierModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierFormController {

    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public TableView<SupplierTM> tblSupplier;
    public TableColumn<SupplierTM,String> colSupplierId;
    public TableColumn<SupplierTM,String>  colSupplierName;
    public TableColumn<SupplierTM,String>  colSupplierContact;
    public JFXButton btnClear;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public AnchorPane ancTextBackground;
    public Label lblMain;
    public AnchorPane mainAnc;
    public JFXButton btnSearch;


    public void initialize(){
        visualize();
        setTableData();
    }

    public void visualize(){
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("suplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("suplierName"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void setTableData(){
        List<SupplierTM> dataToTable = getDataToTable();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList(dataToTable);
        tblSupplier.setItems(supplierTMS);
    }

    public List<SupplierTM> getDataToTable(){
        ArrayList<SupplierTM> list=new ArrayList<>();
        try {
            ArrayList<Suplier> all = SuplierModel.getAll();
            for (Suplier ob : all){
                list.add(new SupplierTM(ob.getSuplierId(),ob.getSuplierName(),ob.getContact(),ob.getEmail()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Suplier collectData(){
        return new Suplier(txtSupplierId.getText(),txtSupplierName.getText(),txtContact.getText(),txtEmail.getText());
    }

    public void btnSaveOnAction(ActionEvent actionEvent){
        try {
            boolean b = SuplierModel.addSupplier(collectData());
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Added Success").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Supplier Adding Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Suplier suplier = collectData();
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "DO You Want To Delete This Supplier" +
                " From The System", ButtonType.NO, ButtonType.YES).showAndWait();

        if(buttonType.isPresent()){
            if(buttonType.get().getText().equalsIgnoreCase("yes")){
                try {
                    boolean b = SuplierModel.deleteSupplier(txtSupplierId.getText());

                    if(b){
                        new Alert(Alert.AlertType.INFORMATION,"Supplier deleted Success").show();
                        setTableData();
                        clear();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Supplier Deleting Failed").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Suplier suplier = collectData();
        try {
            boolean b = SuplierModel.updateSupplier(suplier);
            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Updated Success").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Supplier Updating Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void clear(){
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtContact.clear();
        txtEmail.clear();
    }
    public ArrayList<SupplierTM> getDataForTable(){
        ArrayList<SupplierTM> list=new ArrayList<>();
        try {
            ArrayList<Suplier> all = SuplierModel.getAll();
            for (Suplier ob : all){
                list.add(new SupplierTM(ob.getSuplierId(), ob.getSuplierName(), ob.getContact()
                        , ob.getEmail()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Suplier suplier = SuplierModel.searchSupplier(txtSupplierId.getText());
            if(suplier==null){
                new Alert(Alert.AlertType.ERROR,"Supplier not found!",ButtonType.CANCEL).show();
            }else {
                filldata(suplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void filldata(Suplier suplier){
        txtSupplierName.setText(suplier.getSuplierName());
        txtContact.setText(suplier.getContact());
        txtEmail.setText(suplier.getEmail());
    }

    public void tblSupplierClickAction(MouseEvent mouseEvent) {
        if (tblSupplier.getSelectionModel().getSelectedIndex()==-1)return;
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        txtSupplierId.setText(selectedItem.getSuplierId());
    }

    public void txtSupplierNameOnKeyReleased(KeyEvent keyEvent) {
        if (!txtSupplierName.getText().matches(PatternRegex.getNamePattern().pattern())) {
            txtSupplierName.setStyle("-fx-text-fill: Red;");
        } else txtSupplierName.setStyle("-fx-text-fill: Black;");
    }

    public void txtSupplierContactOnKeyReleased(KeyEvent keyEvent) {
        if (!txtContact.getText().matches(PatternRegex.getMobilePattern().pattern())) {
            txtContact.setStyle("-fx-text-fill: Red;");
        } else txtContact.setStyle("-fx-text-fill: Black;");
    }

    public void txtSupplierEmailOnKeyReleased(KeyEvent keyEvent) {
        if (!txtEmail.getText().matches(PatternRegex.getEmailPattern().pattern())) {
            txtEmail.setStyle("-fx-text-fill: Red;");
        } else txtEmail.setStyle("-fx-text-fill: Black;");
    }
}
