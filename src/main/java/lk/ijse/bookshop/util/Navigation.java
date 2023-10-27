package lk.ijse.bookshop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    private static AnchorPane subpane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case LOGIN:
                window.setTitle("LoginForm");
                initUI("LoginForm.fxml");
                window.centerOnScreen();
                break;
            case DASHBOARD:
                window.setTitle("DashboardForm");
                initUI("ContentForm.fxml");
                window.centerOnScreen();
                break;
            case CONTENT:
                window.setTitle("Dashboard");
                initUI("ContentForm.fxml");
                break;
            case CUSTOMER:
                window.setTitle("Manage Customer");
                initUI("CustomerForm.fxml");
                break;
            case EMPLOYEE:
                window.setTitle("Manage Employee");
                initUI("EmployeeForm.fxml");
                break;
            case ITEM:
                window.setTitle("Manage Item");
                initUI("ItemForm.fxml");
                break;
            case ORDER:
                window.setTitle("Place Order");
                initUI("OrderForm.fxml");
                break;
            case VEHICLE:
                window.setTitle("Manage Vehicles");
                initUI("VehicalForm.fxml");
                break;
            case DRIVER:
                window.setTitle("Manage Drivers");
                initUI("DriverForm.fxml");
                break;
            case SUPPLIER:
                window.setTitle("Manage Supplier");
                initUI("SupplierForm.fxml");
        }
    }

    public static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/view/" + location)));
    }

    public static void init(String location) throws IOException {
        Stage window = (Stage) pane.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/view/" + location))));
    }

}