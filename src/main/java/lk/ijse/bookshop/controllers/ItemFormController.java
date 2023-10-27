package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.util.StringConverter;
import lk.ijse.bookshop.Regex.PatternRegex;
import lk.ijse.bookshop.dto.Customer;
import lk.ijse.bookshop.dto.Stock;
import lk.ijse.bookshop.dto.Suplier;
import lk.ijse.bookshop.dto.tm.CustomerTM;
import lk.ijse.bookshop.dto.tm.ItemTM;
import lk.ijse.bookshop.dto.tm.SupplierTM;
import lk.ijse.bookshop.model.CustomerModel;
import lk.ijse.bookshop.model.StockModel;
import lk.ijse.bookshop.model.SuplierModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ItemFormController {
    public JFXTextField txtItemDescription;
    public JFXTextField txtItemPrice;
    public JFXTextField txtItemQty;
    public JFXTextField txtItemId;
    public JFXTextField txtSupplier;

    public TableView<ItemTM> tblItems;
    public TableColumn<ItemTM,String> colItemDescription;
    public TableColumn<ItemTM,Double> colItemPrice;
    public TableColumn<ItemTM,Integer> colItemQty;
    public JFXComboBox<Suplier> cmbSupplier;

    public void initialize(){
        setTableData();
        identifyDataForColumns();
        getData();
        visualizeSupplierComboBox();
    }

    private void visualizeSupplierComboBox() {
        cmbSupplier.setConverter(new StringConverter<Suplier>() {
            @Override
            public String toString(Suplier suplier) {
                return suplier.getSuplierName();
            }

            @Override
            public Suplier fromString(String s) {
                return null;
            }
        });
    }

    public void getData(){
        try {
            ArrayList<Suplier> all = SuplierModel.getAll();
            ObservableList<Suplier> supliers = FXCollections.observableArrayList(all);
            cmbSupplier.setItems(supliers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void identifyDataForColumns() {
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void setTableData() {
        ArrayList<ItemTM> dataForTable = getDataForTable();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList(dataForTable);
        tblItems.setItems(itemTMS);
    }
    public ArrayList<ItemTM> getDataForTable(){
        ArrayList<ItemTM> list=new ArrayList<>();
        try {
            ArrayList<Stock> all = StockModel.getAll();
            for (Stock ob : all){
                list.add(new ItemTM(ob.getStockId(),ob.getDescription(), ob.getPrice(), ob.getQty(),ob.getSuplierId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Stock collectData() {
        String itemid = txtItemId.getText();
        String desc = txtItemDescription.getText();
        double price = Double.parseDouble(txtItemPrice.getText());
        int qty = Integer.parseInt(txtItemQty.getText());
        String suplierid = cmbSupplier.getSelectionModel().getSelectedItem().getSuplierId();

        return new Stock(itemid,desc,price,qty,suplierid);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Stock stock =collectData();
        try {
            boolean addItem = StockModel.addItem(stock);
            if (addItem){
                new Alert(Alert.AlertType.INFORMATION,"Item Added").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Item Adding Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to delete this Item from the system",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (!buttonType.get().equals(ButtonType.YES)) {
            return;
        }
        String id = txtItemId.getText();

        try {
            boolean isDeleted = StockModel.deleteItem(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted Success").show();
                setTableData();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item Delete Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Stock stock = collectData();
        try {
            boolean isUpdated = StockModel.updateItem(stock);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated").show();
                setTableData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Item updating Failed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Stock stock = StockModel.searchItem(txtItemId.getText());
            if (stock == null){
                new Alert(Alert.AlertType.ERROR,"This Item is not found!").show();
            }else {
                fillData(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillData(Stock stock) {
        txtItemDescription.setText(stock.getDescription());
        txtItemPrice.setText(String.valueOf(stock.getPrice()));
        txtItemQty.setText(String.valueOf(stock.getQty()));
        txtSupplier.setText(stock.getSuplierId());
    }

    public void clear() {
        txtItemId.clear();
        txtItemDescription.clear();
        txtItemPrice.clear();
        txtItemQty.clear();
        txtSupplier.clear();
    }

    public void tblItemClickAction(MouseEvent mouseEvent) {
        if (tblItems.getSelectionModel().getSelectedIndex()==-1)return;
        ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
        txtItemDescription.setText(selectedItem.getDescription());
        txtItemId.setText(selectedItem.getId());
        txtItemPrice.setText(String.valueOf(selectedItem.getPrice()));
        txtItemQty.setText(String.valueOf(selectedItem.getQty()));
        setSupplier(selectedItem.getSupplierId());
    }

    public void setSupplier(String id){
        for(Suplier ob : cmbSupplier.getItems()){
            if(ob.getSuplierId().equals(id)){
                cmbSupplier.getSelectionModel().select(ob);
            }
        }
    }
    public void txtItemDescOnKeyReleased(KeyEvent keyEvent) {
        if (!txtItemDescription.getText().matches(PatternRegex.getStringPattern().pattern())) {
            txtItemDescription.setStyle("-fx-text-fill: Red;");
        } else txtItemDescription.setStyle("-fx-text-fill: Black;");
    }

    public void txtItemPriceOnKeyReleased(KeyEvent keyEvent) {
        if (!txtItemPrice.getText().matches(PatternRegex.getDoublePattern().pattern())) {
            txtItemPrice.setStyle("-fx-text-fill: Red;");
        } else txtItemPrice.setStyle("-fx-text-fill: Black;");
    }

    public void txtItemQtyOnKeyReleased(KeyEvent keyEvent) {
        if (!txtItemQty.getText().matches(PatternRegex.getIntPattern().pattern())) {
            txtItemQty.setStyle("-fx-text-fill: Red;");
        } else txtItemQty.setStyle("-fx-text-fill: Black;");
    }
}

