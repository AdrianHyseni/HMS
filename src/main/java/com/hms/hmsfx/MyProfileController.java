package com.hms.hmsfx;

import com.hms.hmsfx.data.SystemData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileController implements Initializable {
    @FXML
    private Label usernameText;
    @FXML
    private Button profileBtn;
    @FXML
    private Button  logoutBtn;
    @FXML
    private Button  roomBtn;
    @FXML
    private Button  apartmentBtn;
    @FXML
    private Button  settingsBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button reservationBtn;

    SystemData sd = new SystemData();
    SideBar s = new SideBar();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn);
    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }
}
