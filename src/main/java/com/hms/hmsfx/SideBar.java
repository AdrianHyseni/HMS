package com.hms.hmsfx;

import com.hms.hmsfx.data.SystemData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SideBar {
    SystemData sd = new SystemData();
    HMSFunctions hmsFunctions = new HMSFunctions();
    public void sideBar(Button goToProfile, Button logout, Button goToSettings, Button goToRooms,Button home,Button apartments){
        goToSettings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.goToSettings();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        goToProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.goToProfile();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        goToRooms.setOnAction(new EventHandler<ActionEvent>() {
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
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)   {
                try{
                    hmsFunctions.logout();
                }
                catch (Exception e){
                    e.printStackTrace();                }
            }
        });
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.goToDashboard();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        apartments.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    hmsFunctions.goToApartments();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
}
