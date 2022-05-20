package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.HMSFunctions;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.RoomData;
import com.hms.hmsfx.data.SystemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoomListController implements Initializable {

    ObservableList<RoomData> roomData =FXCollections.observableArrayList();
    SystemData sd = new SystemData();
    DatabaseConnection connection = new DatabaseConnection();
    Connection con = connection.getConnection();
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

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
    private Button freeRoomsBtn;
    @FXML
    private Button showAllBtn;
    @FXML
    private Button allReservationBtn;
    @FXML
    private Button beachBtn;

    //Table
    @FXML
    private TableView<RoomData> roomTable;
    @FXML
    private TableColumn<RoomData,String> nameCol;
    @FXML
    private TableColumn<RoomData,String> descriptionCol;
    @FXML
    private TableColumn<RoomData,Integer> priceCol;
    @FXML
    private TableColumn<RoomData,String> typeCol;
    @FXML
    private TableColumn<RoomData,Boolean> statusCol;
    @FXML
    private Button reservationBtn;

    //Search Field
    @FXML
    private TextField searchField;
    @FXML
    private Button costsBtn;


    SideBar s = new SideBar();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn,costsBtn,beachBtn);
        //Type Choice
        filterData();
        showAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    refresh();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        freeRoomsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getFreeRooms();
            }
        });




    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }


    //divide each data on the right column
    private void setCellTable(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("roomDesc"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));
    }
    //Display Room on the table
    private void showData(){

        getData();
        roomTable.setItems(roomData);


    }
    public void filterData() {
        try{
            String query = "SELECT * FROM ROOMS";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                Boolean status = resultSet.getBoolean("status");
                roomData.add(new RoomData(id,name,description,price,type,status));
            }
            FilteredList<RoomData> filteredRoom = new FilteredList<>(roomData, b -> true);
            //Search Field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredRoom.setPredicate(roomSearchModel -> {
                    String searchKeyword = newValue.toLowerCase();
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    if(roomSearchModel.getRoomName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(roomSearchModel.getRoomType().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else {
                        return false;
                    }

                });
            });
            setCellTable();
            roomTable.setItems(roomData);
            SortedList<RoomData> sortedRoom = new SortedList<>(filteredRoom);
            sortedRoom.comparatorProperty().bind(roomTable.comparatorProperty());
            roomTable.setItems(sortedRoom);

        }catch(SQLException e){
            e.printStackTrace();
        }


    }
    public void getData() {
        try{
            String query = "SELECT * FROM ROOMS";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                Boolean status = resultSet.getBoolean("status");
                roomData.add(new RoomData(id,name,description,price,type,status));
            }
            setCellTable();
            roomTable.setItems(roomData);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void getFreeRooms(){
        try{
            String query = "SELECT * FROM ROOMS WHERE status=true ";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            roomData.clear();
            while(resultSet.next()){


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                Boolean status = resultSet.getBoolean("status");
                roomData.add(new RoomData(id,name,description,price,type,status));
            }

            setCellTable();
            roomTable.setItems(roomData);


        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void refresh(){
        roomData.clear();
        filterData();
    }





}

