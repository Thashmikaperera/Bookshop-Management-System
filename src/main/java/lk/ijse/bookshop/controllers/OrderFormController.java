package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lk.ijse.bookshop.dto.*;
import lk.ijse.bookshop.dto.tm.DeliveryTM;
import lk.ijse.bookshop.dto.tm.OrderTM;
import lk.ijse.bookshop.model.CustomerModel;
import lk.ijse.bookshop.model.DelivaryModel;
import lk.ijse.bookshop.model.OrderModel;
import lk.ijse.bookshop.model.StockModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderFormController {
    public ComboBox cmbCustomer;
    public ComboBox<Stock> cmbItem;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemDesc;
    public JFXTextField txtIemPrice;
    public JFXTextField txtItemQty;
    public JFXTextField txtQty;
    public Button btnAddToCart;
    public Button btnPlaceOrder;

    public TableView<OrderTM> tblOrder;
    public TableColumn<OrderTM, String> colItemId;
    public TableColumn<OrderTM, String> colItemDesc;
    public TableColumn<OrderTM, Double> colItemPrice;
    public TableColumn<OrderTM, Integer> colItemQty;
    public TableColumn<OrderTM, Double> ColItemTotal;
    public TextField txtOrderId;
    public TextField txtOrderDate;
    public JFXComboBox<Delivary> cbDelivery;
    public TableView<DeliveryTM> tblDelivery;
    public TableColumn<DeliveryTM, String> colOrderId;
    public TableColumn<DeliveryTM, String> colCustomerId;
    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();


    public void initialize() throws SQLException, ClassNotFoundException {
        txtOrderDate.setEditable(false);
        txtOrderDate.setText(LocalDate.now().toString());
        loadCustomers();
        loadItems();
        setItemComboBoxVisual();
        loadDate();
        setComboBox();
        setVisualize();
        identifyDataForColumns();
        loadnewid();
    }

    private void loadnewid() {
        try {
            String newOrderId = OrderModel.getNewOrderId();
            txtOrderId.setText(newOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void identifyDataForColumns() {
        colItemId.setCellValueFactory(new PropertyValueFactory("itemid"));
        colItemDesc.setCellValueFactory(new PropertyValueFactory("desc"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colItemQty.setCellValueFactory(new PropertyValueFactory("qty"));
        ColItemTotal.setCellValueFactory(new PropertyValueFactory("total"));
    }

    public ArrayList<String> getDataForCustomer() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ArrayList<Customer> all = CustomerModel.getAll();
            for (Customer ob : all) {
                list.add((ob.getCustomerId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*public ArrayList<String> getDataForItem(){
        ArrayList<String> list=new ArrayList<>();
        try {
            ArrayList<Stock> all = StockModel.getAll();
            for (Stock ob : all){
                list.add((ob.getStockId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }*/

    private void loadDate() {
        txtOrderDate.setText(new SimpleDateFormat("20yy-MM-dd").format(new Date()));
    }

    private void loadItems() {
        try {
            cmbItem.setItems(FXCollections.observableArrayList(StockModel.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setItemComboBoxVisual() {
        cmbItem.setConverter(new StringConverter<Stock>() {
            @Override
            public String toString(Stock stock) {
                if (stock == null) return null;
                return stock.getStockId() + " : " + stock.getDescription();
            }

            @Override
            public Stock fromString(String s) {
                return null;
            }
        });
    }

    private void loadCustomers() {
        ArrayList<String> dataForCustomer = getDataForCustomer();
        ObservableList<String> customerTMS = FXCollections.observableArrayList(dataForCustomer);
        cmbCustomer.setItems(customerTMS);
    }

    public void CustomerDetailOnAction(ActionEvent actionEvent) {
        String CustomerId = cmbCustomer.getSelectionModel().getSelectedItem().toString();
        try {
            Customer customer = CustomerModel.searchCustomer(CustomerId);
            fillcustomerData(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillcustomerData(Customer customer) {
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerAddress.setText(customer.getAddress());
        txtCustomerContact.setText(customer.getContact());
        txtCustomerId.setText(customer.getCustomerId());
    }


    public void ItemDetailOnAction(ActionEvent actionEvent) {
        Stock stock = cmbItem.getSelectionModel().getSelectedItem();
        fillItemData(stock);
        txtQty.requestFocus();
    }

    private void fillItemData(Stock stock) {
        txtItemCode.setText(stock.getStockId());
        txtItemDesc.setText(stock.getDescription());
        txtIemPrice.setText(String.valueOf(stock.getPrice()));
        txtItemQty.setText(String.valueOf(stock.getQty()));
    }

    public void AddCartOnAction(ActionEvent actionEvent) {
        Stock item = cmbItem.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtQty.getText());
        item.setQty(item.getQty() - qty);
        OrderTM cartTM = new OrderTM(item.getStockId(), item.getDescription(), item.getPrice(), qty, item.getPrice() * qty);

        //tblOrder.getItems().add(cartTM);
        obList.add(cartTM);
        tblOrder.setItems(obList);


    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) {
        double total = findtotal();
        String deliveryId = null;
        Delivary selectedItem = cbDelivery.getSelectionModel().getSelectedItem();
        if (selectedItem != null) deliveryId = selectedItem.getDelivaryId();
        Orders orders = new Orders(txtOrderId.getText(), txtOrderDate.getText(), total, 0.0, null,
                txtCustomerId.getText(), deliveryId);

        ObservableList<OrderTM> items = tblOrder.getItems();
        ArrayList<OrderStockDetails> list = new ArrayList<>();
        for (OrderTM ob : items) {
            list.add(new OrderStockDetails(txtOrderId.getText(), ob.getItemid(), ob.getQty()));
        }
        try {
            boolean isPlaced = OrderModel.placeOrder(orders, list);
            if (isPlaced) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed Success").show();
                generateBill(orders);
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Placing Filed Check Details And Try Again").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double findtotal() {
        double total = 0.0;
        for (OrderTM cartDetail :obList){
            total+=cartDetail.getTotal();
        }
        return total;
    }

    public void btnNewDeliveryOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DelivaryForm.fxml"))));
        stage.setTitle("Delivery Form");
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(txtCustomerId.getScene().getWindow());
        stage.showAndWait();
        try {
            setComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnMakeDeliveredOnAction(ActionEvent actionEvent) throws SQLException {
        Delivary selectedItem = cbDelivery.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        boolean b = DelivaryModel.setDeliveryStatus(selectedItem.getDelivaryId());
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Delivery Placed Success").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Delivery Placing Failed").show();
        }
    }

    public void cbDeliveryOnAction(ActionEvent actionEvent) throws SQLException {
        Delivary selectedItem = cbDelivery.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        List<Orders> ordersDyDeliveryId = OrderModel.findOrdersDyDeliveryId(selectedItem.getDelivaryId());
        ArrayList<DeliveryTM> list = new ArrayList<>();
        for (Orders ob : ordersDyDeliveryId) {
            list.add(new DeliveryTM(ob.getOrderId(), ob.getCustomerId()));
        }

        tblDelivery.setItems(FXCollections.observableArrayList(list));

    }

    public void setVisualize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    public void setComboBox() throws SQLException {
        List<Delivary> notDeliveredDeliveryList = DelivaryModel.getNotDeliveredDeliveryList();
        cbDelivery.setItems(FXCollections.observableArrayList(notDeliveredDeliveryList));
        cbDelivery.setConverter(new StringConverter<Delivary>() {
            @Override
            public String toString(Delivary delivary) {
                if (delivary == null) return null;
                return delivary.getDelivaryId();
            }

            @Override
            public Delivary fromString(String s) {
                return null;
            }
        });
    }

    private void generateBill(Orders orders) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("Cashiername", "Kamal");
        hm.put("itemprice", String.valueOf(orders.getPrice()));
        hm.put("deliveryprice", "0.0");
        String total = String.valueOf(orders.getPrice());
        hm.put("totalprice", total);
        hm.put("customername", txtCustomerName.getText());
        hm.put("address", txtCustomerAddress.getText());

        try {
            JasperReport compileReport = JasperCompileManager
                    .compileReport("E:\\GDSE 65\\Semester 1\\new final project\\Project-Luminex-Bookshop\\src\\main\\resources\\Report\\customerbill.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}
