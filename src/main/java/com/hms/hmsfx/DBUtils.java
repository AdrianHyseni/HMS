package com.hms.hmsfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class DBUtils {

public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String password){
    Parent root = null;

    if(username != null && password != null) {
        try {
            FXMLLoader loader = new FXMLLoader((DBUtils.class.getResource(fxmlFile)));
            root = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.setUserInformation(username,password);

        } catch (IOException e ){
            e.printStackTrace();
        }
    }
    else {

    }

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(new Scene(root, 1480,800));
    stage.show();


}



    public static void loginUser(ActionEvent event,String username, String password){
        DatabaseConnection connection = new DatabaseConnection();
        Connection con = connection.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        String loginQuery = "SELECT name FROM USER WHERE username = ? AND password = ?";

        try {
                preparedStatement = con.prepareStatement(loginQuery);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2,password);

                resultSet = preparedStatement.executeQuery();


                if(!resultSet.isBeforeFirst()){
                    System.out.println("User not found in database");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Username is incorrect!");
                    alert.show();
                }
                else {
                    while(resultSet.next()){
                        String retrievedPassword = resultSet.getString("password");
                        String retrievedUsername = resultSet.getString("username");

                        if (retrievedPassword.equals(password)){
                            changeScene(event, "com/hms/hmsfx/Dashboard.fxml","Dashboard", username,null);

                        }else{
                            System.out.println("Password did not match");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The Password did not match");
                            alert.show();
                        }

                    }
                }

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(resultSet != null){
                try {
                    resultSet.close();

                }catch (SQLException e ){
                    e.printStackTrace();
                }

            }

            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(con != null){
               try{
                   con.close();
               }catch (SQLException e){
                   e.printStackTrace();
               }
            }
        }

    }


}
