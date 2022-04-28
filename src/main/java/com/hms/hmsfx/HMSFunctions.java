package com.hms.hmsfx;

import com.hms.hmsfx.Controller.LoginController;

import java.io.IOException;

public class HMSFunctions {

    public  void logout() throws IOException {
        Main m = new Main();
        LoginController l = new LoginController();
        m.changeScene("Login.fxml");
    }

    public  void goToRooms() throws  IOException {
        Main m = new Main();
        m.changeScene("RoomList.fxml");

    }
    public  void goToApartments() throws  IOException {
        Main m = new Main();
        m.changeScene("ApartmentList.fxml");

    }

    public  void newApartment() throws  IOException {
        Main m = new Main();
        m.changeScene("AddApartment.fxml");


    }

    public  void newRoom() throws  IOException {
        Main m = new Main();
        m.changeScene("AddRoom.fxml");

    }

    public void goToSettings() throws  IOException{
        Main m = new Main();
        m.changeScene("Settings.fxml");


    }



    public void goToProfile() throws IOException{
        Main m = new Main();
        m.changeScene("MyProfile.fxml");
    }

    public void goToDashboard() throws IOException{
        Main m = new Main();
        m.changeScene("Dashboard.fxml");
    }

    public void makeReservation() throws IOException{
        Main m = new Main();
        m.changeScene("Reservations.fxml");
    }






}
