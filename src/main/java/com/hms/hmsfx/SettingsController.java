package com.hms.hmsfx;

import com.hms.hmsfx.data.SystemData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class SettingsController implements Initializable {
    SystemData sd = new SystemData();


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
    private Button editProfileBtn;
    @FXML
    private Button newApartmentBtn;
    @FXML
    private Button addNewCost;
    @FXML
    private Button addNewRoom;
    @FXML
    private Button reservationBtn;
    @FXML
    private Button allReservationBtn;
    @FXML
    private Button costBtn;

    SideBar s = new SideBar();
    HMSFunctions hmsFunctions = new HMSFunctions();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn,costBtn);
        editProfileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.goToProfile();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        newApartmentBtn.setOnAction (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.newApartment();
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });
        addNewRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.newRoom();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        addNewCost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.gotoCosts();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }

}
