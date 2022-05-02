package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.ApartmentData;
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

public class ApartmentListController implements Initializable {

    ObservableList<ApartmentData> apartmentData =FXCollections.observableArrayList();
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
    private Button reservationBtn;

    //Table
    @FXML
    private TableView<ApartmentData> roomTable;
    @FXML
    private TableColumn<ApartmentData,String> nameCol;
    @FXML
    private TableColumn<ApartmentData,String> descriptionCol;
    @FXML
    private TableColumn<ApartmentData,Integer> priceCol;
    @FXML
    private TableColumn<ApartmentData,String> typeCol;
    @FXML
    private TableColumn<ApartmentData,Boolean> statusCol;
    @FXML
    private TableColumn<ApartmentData,String> specsCol;

    //Search Field
    @FXML
    private TextField searchField;


    SideBar s = new SideBar();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn);
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("apartmentName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apartmentDesc"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("apartmentPrice"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apartmentType"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("apartmentStatus"));
        specsCol.setCellValueFactory(new PropertyValueFactory<>("apartmentSpecs"));
    }
    //Display Room on the table
    private void showData(){

        getData();
        roomTable.setItems(apartmentData);


    }
    public void filterData() {
        try{
            String query = "SELECT * FROM apartments";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                String specs = resultSet.getString("specs");
                Boolean status = resultSet.getBoolean("status");
                apartmentData.add(new ApartmentData(id,name,description,price,type,status,specs));
            }
            FilteredList<ApartmentData> filteredApartment = new FilteredList<>(apartmentData, b -> true);
            //Search Field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredApartment.setPredicate(apartmentSearchModel -> {
                    String searchKeyword = newValue.toLowerCase();
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    if(apartmentSearchModel.getApartmentName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(apartmentSearchModel.getApartmentType().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(apartmentSearchModel.getApartmentSpecs().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(apartmentSearchModel.getApartmentDesc().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else {
                        return false;
                    }

                });
            });
            setCellTable();
            roomTable.setItems(apartmentData);
            SortedList<ApartmentData> sortedRoom = new SortedList<>(filteredApartment);
            sortedRoom.comparatorProperty().bind(roomTable.comparatorProperty());
            roomTable.setItems(sortedRoom);

        }catch(SQLException e){
            e.printStackTrace();
        }


    }
    public void getData() {
        try{
            String query = "SELECT * FROM apartments";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                String specs = resultSet.getString("specs");
                Boolean status = resultSet.getBoolean("status");

                apartmentData.add(new ApartmentData(id,name,description,price,type,status,specs));
            }
            setCellTable();
            roomTable.setItems(apartmentData);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void getFreeRooms(){
        try{
            String query = "SELECT * FROM apartments WHERE status=true ";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            apartmentData.clear();
            while(resultSet.next()){


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String type = resultSet.getString("type");
                String specs = resultSet.getString("specs");
                Boolean status = resultSet.getBoolean("status");
                apartmentData.add(new ApartmentData(id,name,description,price,type,status,specs));
            }

            setCellTable();
            roomTable.setItems(apartmentData);


        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void refresh(){
        apartmentData.clear();
        filterData();
    }





}

