package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private TextField usernameF;
    @FXML
    private PasswordField passwordF;
    @FXML
    private Button loginBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.loginUser(event, usernameF.getText(),passwordF.getText());
            }
        });
    }
}
