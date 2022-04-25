package com.hms.hmsfx;

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

import java.net.FileNameMap;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AddRoomController implements Initializable {

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
    private Button addBtn;

    //Add Room
    @FXML
    private  TextField nameTf;
    @FXML
    private  ChoiceBox typeCb;
    @FXML
    private TextField priceTf;
    @FXML
    private CheckBox status;
    @FXML
    private TextArea descriptionTa;

    @FXML
    private Button showAllBtn;

    //Table
    @FXML
    private TableView<RoomData> roomTable;
    @FXML
    private TableColumn<RoomData,Integer> idCol;
    @FXML
    private TableColumn<RoomData,String> nameCol;
    @FXML
    private TableColumn<RoomData,String> descCol;
    @FXML
    private TableColumn<RoomData,Integer> priceCol;
    @FXML
    private TableColumn<RoomData,String> typeCol;
    @FXML
    private TableColumn<RoomData,Boolean> statusCol;

    //Search Field
    @FXML
    private TextField searchField;


    SideBar s = new SideBar();
    HMSFunctions hmsFunctions = new HMSFunctions();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn);
        //Type Choice
        ArrayList<String> type =new ArrayList<>();
        type.add("single");
        type.add("double");
        type.add("suite");
        ObservableList<String> list = FXCollections.observableArrayList(type);
        typeCb.setItems(list);
        filterData();
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addRoom();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("You have added a room");
                    alert.setTitle("Done");
                    alert.show();
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There was a problem please try again!");
                    alert.setTitle("Done");
                    alert.show();
                    e.printStackTrace();
                }
            }
        });
        showAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    for ( int i = 0; i<roomTable.getItems().size(); i++) {
                        roomTable.getItems().clear();
                    }
                    showData();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });




    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }

    public  void addRoom() {

        int retrievedId = 0;

        String query = "INSERT INTO rooms(name, description,price, type, status, created_by) VALUES"+
                        "(?,?,?,?,?,?)";

        String getKey = "SELECT id FROM user where username =?";

        try{
            preparedStatement = con.prepareStatement(getKey);
            preparedStatement.setString(1, sd.getUsername());
            ResultSet resultSet1 = preparedStatement.executeQuery();

            if(resultSet1.next()){
                retrievedId = resultSet1.getInt(1);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Result Empty");
                alert.setTitle("Done");
                alert.show();

            }


        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        try {

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, nameTf.getText());
            preparedStatement.setString(2, descriptionTa.getText());
            preparedStatement.setInt(3, Integer.valueOf(priceTf.getText()));
            preparedStatement.setString(4, typeCb.getSelectionModel().getSelectedItem().toString());
            preparedStatement.setBoolean(5, status.isSelected());
            preparedStatement.setInt(6,retrievedId);
            preparedStatement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

    }

    //divide each data on the right column
    private void setCellTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("roomDesc"));
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
    private void getData() {
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




}
