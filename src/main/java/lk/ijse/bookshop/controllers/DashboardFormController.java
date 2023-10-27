package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bookshop.util.Navigation;
import lk.ijse.bookshop.util.Routes;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {
    public AnchorPane dashboardPane;
    public JFXButton btnDashboard;
    public JFXButton btnCustomer;
    public JFXButton btnItem;
    public JFXButton btnEmployee;

    public void initialize() throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/ContentForm.fxml"));
        dashboardPane.getChildren().clear();
        dashboardPane.getChildren().add(load);
    }

    public void DashBoardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CONTENT,dashboardPane);
    }



    public void ItemFormOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM,dashboardPane);
    }

    public void EmployeeFormOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE,dashboardPane);
    }

    public void SupplierFormOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,dashboardPane);
    }

    public void OrderFormOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ORDER,dashboardPane);
    }

    public void vehicleFormOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VEHICLE,dashboardPane);
    }

    public void DriverFormOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DRIVER,dashboardPane);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,dashboardPane);

    }
}
