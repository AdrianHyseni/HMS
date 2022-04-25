package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionController {

    @FXML
    private Label text;



    public void connectButton(ActionEvent event){
        DatabaseConnection connection = new DatabaseConnection();
        Connection con  = connection.getConnection();
        String connectionQuery = "SELECT name FROM user";

        try {
            Statement statement = con.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectionQuery);

            while(queryOutput.next()){
                text.setText(queryOutput.getString("name"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
