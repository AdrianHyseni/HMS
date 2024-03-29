package com.hms.hmsfx;

import com.hms.hmsfx.data.SystemData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.IOException;

public class SideBar {
    SystemData sd = new SystemData();
    HMSFunctions hmsFunctions = new HMSFunctions();
    public void sideBar(Button goToProfile, Button logout, Button goToSettings, Button goToRooms,Button home,Button apartments, Button makeReservation, Button reservationList, Button costList, Button beachBtn){
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
        makeReservation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.makeReservation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        reservationList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.gotoReservationList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        costList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.gotoCostList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        beachBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    hmsFunctions.gotoBeach();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
