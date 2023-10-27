package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.ijse.bookshop.dto.Customer;
import lk.ijse.bookshop.dto.Stock;
import lk.ijse.bookshop.dto.tm.bestCustomer;
import lk.ijse.bookshop.dto.tm.bestItem;
import lk.ijse.bookshop.model.CustomerModel;
import lk.ijse.bookshop.model.OrderModel;
import lk.ijse.bookshop.model.StockModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ContentFormController {
    public JFXTextField txtTime;
    public JFXTextField txtDate;
    public Label lblCustname;
    public Label lblcustodercount;
    public Label lblItemname;
    public Label lblitemOrdercount;
    public Label lblprice;
    public Label lblordercount;
    public BarChart barGraph;

    public void initialize() {
        try {
            loadBarchart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadbest();
        Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        txtTime.setText(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":"
                                + LocalTime.now().getSecond());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }catch (NullPointerException e){
                        e.printStackTrace();
                        break;
                    }

                }
            }
        };
        t1.start();
        LocalDate now = LocalDate.now();
        txtDate.setText(now.toString());

    }

    private void loadbest() {
        try {
            bestCustomer bestCustomer = CustomerModel.findBestCustomer();
            Customer customer = CustomerModel.searchCustomer(bestCustomer.getCustid());
            lblCustname.setText(customer.getCustomerName());
            lblcustodercount.setText(String.valueOf(bestCustomer.getCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            bestItem bestItem = StockModel.findBestItem();
            Stock stock = StockModel.searchItem(bestItem.getItemid());
            lblItemname.setText(stock.getDescription());
            lblitemOrdercount.setText(String.valueOf(bestItem.getCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String todaySales = OrderModel.getTodaySales();
            String todaySalesCount = OrderModel.getTodaySalesCount();
            lblprice.setText(todaySales);
            lblordercount.setText(todaySalesCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    private void loadBarchart() throws SQLException {
            Integer[] data = OrderModel.geOrderValueMonths();
            XYChart.Series<String, Integer> series = new XYChart.Series();
            series.setName("No. of Order");
            series.getData().add(new XYChart.Data("JAN", data[0]));
            series.getData().add(new XYChart.Data("FEB", data[1]));
            series.getData().add(new XYChart.Data("MAR", data[2]));
            series.getData().add(new XYChart.Data("APR", data[3]));
            series.getData().add(new XYChart.Data("MAY", data[4]));
            series.getData().add(new XYChart.Data("JUN", data[5]));
            series.getData().add(new XYChart.Data("JUL", data[6]));
            series.getData().add(new XYChart.Data("AUG", data[7]));
            series.getData().add(new XYChart.Data("SEP", data[8]));
            series.getData().add(new XYChart.Data("OCT", data[9]));
            series.getData().add(new XYChart.Data("NOV", data[10]));
            series.getData().add(new XYChart.Data("DEC", data[11]));

            barGraph.getData().addAll(series);


    }
}
