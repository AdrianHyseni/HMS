package com.hms.hmsfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    @FXML
    private Label usernameText;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button profileBtn;

    @FXML
    private Button apartmentBtn;

    @FXML
    private Button roomBtn;

    @FXML
    private Button billBtn;

    @FXML
    private Button costBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button reservationBtn;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "com/hms/hmsfx/Login.fxml", null, null,null);
            }
        });



    }


    public void setUserInformation(String username, String password){
        usernameText.setText(username);
    }


}
