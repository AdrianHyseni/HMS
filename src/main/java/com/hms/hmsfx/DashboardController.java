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
    private Button billsBtn;

    @FXML
    private Button costsBtn;

    @FXML
    private Button settingsBtn;



    @FXML
    private Button reservationBtn;


    @FXML
    private Button beachBtn;

    @FXML
    private Button homeBtn;
    @FXML
    private Button allReservationBtn;

    @FXML
    private Button addReservationBtn;

    @FXML
    private Button goToCostsBtn;
    @FXML
    private Button apartmentListBtn;



    HMSFunctions hmsFunctions = new HMSFunctions();
    SideBar s = new SideBar();
    SystemData sd  = new SystemData();





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn, allReservationBtn,costsBtn,beachBtn);
        roomBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.goToRooms();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        apartmentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.goToApartments();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        costsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.gotoCosts();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        setUserInformation(sd.getUsername(),null);
        settingsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.goToSettings();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        addReservationBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    hmsFunctions.gotoReservationList();
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        });

        goToCostsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    hmsFunctions.gotoCostList();
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        });

        apartmentListBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    hmsFunctions.goToApartments();
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });



    }


    public void setUserInformation(String username, String password){
        usernameText.setText(username);
    }


}
