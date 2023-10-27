package lk.ijse.bookshop.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bookshop.dto.User;
import lk.ijse.bookshop.model.UserModel;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    public CheckBox ckbShow;
    public Button btnSignin;
    public Label lblforgetPassword;
    public Button btnSignUp;
    public JFXTextField txtUserName1;
    public JFXTextField txtUserName;
    public JFXTextField txtpassword;
    public JFXTextField txtHint;
    public JFXTextField txtName;
    public JFXTextField txtpassowrd;
    public Label lblHint;
    public JFXPasswordField txtUserPassword;
    public ImageView imgShow;
    public ImageView imghide;
    public AnchorPane pane;

    public void initialize(){
        imghide.setVisible(false);
        txtpassowrd.setVisible(false);
    }


    public void SigninOnAction(ActionEvent actionEvent) {
        String username = txtUserName1.getText();
        try {
            User search = UserModel.search(username);
            String password = txtUserPassword.getText();
            if (password.equals(search.getUserPassword())){
                Parent load = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));
                pane.getChildren().clear();
                pane.getChildren().add(load);
            }else{
                new Alert(Alert.AlertType.ERROR,"User not founed",ButtonType.OK).show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void hintOnmouseClicked(MouseEvent mouseEvent) {
        String userName = txtUserName1.getText();
        try {
            User search = UserModel.search(userName);
            lblHint.setText(search.getHint());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void SignUpOnAction(ActionEvent actionEvent) {
        User user =makeObject();
        try {
            boolean save = UserModel.save(user);
            if (save){
                new Alert(Alert.AlertType.CONFIRMATION,"Save New User Sucessfully.", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Error.", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User makeObject() {
        return new User(txtUserName.getText(),txtpassword.getText(),txtHint.getText(),txtName.getText());
    }

    public void ShowPasswordOnMouseClicked(MouseEvent mouseEvent) {
        String password = txtUserPassword.getText();
        txtpassowrd.setVisible(true);
        txtUserPassword.setVisible(false);
        txtpassowrd.setText(password);
        imghide.setVisible(true);
        imgShow.setVisible(false);
    }

    public void HidePasswordOnMouseClicked(MouseEvent mouseEvent) {
        String password = txtpassowrd.getText();
        txtUserPassword.setVisible(true);
        txtpassowrd.setVisible(false);
        txtUserPassword.setText(password);
        imgShow.setVisible(true);
        imghide.setVisible(false);
    }
}
