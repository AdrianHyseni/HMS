package com.hms.hmsfx;

import com.hms.hmsfx.Controller.LoginController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try{
        update();
        m.changeScene("Reservations.fxml");
        }catch (Exception sql){
            sql.printStackTrace();
        }
    }

    public void gotoReservationList() throws IOException{
        Main m = new Main();
        m.changeScene("ReservationList.fxml");
    }

    public void gotoCosts() throws IOException{
        Main m = new Main();
        m.changeScene("Costs.fxml");
    }

    public void gotoCostList() throws IOException{
        Main m = new Main();
        m.changeScene("CostList.fxml");
    }

    public void update() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection con = connection.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        String queryRooms = "UPDATE rooms SET status = true WHERE id IN (SELECT room_fk from booking where  check_out < NOW())";
        String queryApartments = "UPDATE apartments SET status = true WHERE id IN (SELECT apartment_fk from booking where  check_out < NOW())";
        preparedStatement = con.prepareStatement(queryRooms);
        preparedStatement.executeUpdate();
        preparedStatement2 = con.prepareStatement(queryApartments);
        preparedStatement2.executeUpdate();

    }







}
