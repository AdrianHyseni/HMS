package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.ApartmentData;
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

public class AddApartmentController implements Initializable {

    ObservableList<ApartmentData> apartmentData =FXCollections.observableArrayList();
    SystemData sd = new SystemData();
    //Database
    DatabaseConnection connection = new DatabaseConnection();
    Connection con = connection.getConnection();
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    //Sidebar
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
    @FXML
    private Button allReservationBtn;
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
    private TextArea specsTa;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button showAllBtn;

    //Table
    @FXML
    private TableView<ApartmentData> apartmentTable;
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
    private TableColumn<RoomData,String> specsCol;
    @FXML
    private TableColumn<RoomData,Boolean> statusCol;
    @FXML
    private  Button reservationBtn;

    //Search Field
    @FXML
    private TextField searchField;


    SideBar s = new SideBar();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn);
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
                    addApartment();
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
                    refresh();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteData();
            }
        });





    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }

    public  void addApartment() {

        int retrievedId = 0;

        String query = "INSERT INTO apartments(name, description,price, type, status, specs, created_by) VALUES"+
                "(?,?,?,?,?,?,?)";

        String getKey = "SELECT id FROM user where username =?";
        if(priceTf.getText().matches("\\b\\d+\\b")){
            try{
                preparedStatement = con.prepareStatement(getKey);
                preparedStatement.setString(1, sd.getUsername());
                ResultSet resultSet1 = preparedStatement.executeQuery();

                if(resultSet1.next()){
                    retrievedId = resultSet1.getInt(1);
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
                preparedStatement.setString(6, specsTa.getText());
                preparedStatement.setInt(7,retrievedId);
                preparedStatement.executeUpdate();
                refresh();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You have added a room");
                alert.setTitle("Done");
                alert.show();
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not a number");
            alert.setTitle("Error On Price Field");
            alert.show();
        }

    }

    //divide each data on the right column
    private void setCellTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("apartmentName"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("apartmentDesc"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("apartmentPrice"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apartmentType"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("apartmentStatus"));
        specsCol.setCellValueFactory(new PropertyValueFactory<>("apartmentStatus"));
    }
    //Display Room on the table
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
                Boolean status = resultSet.getBoolean("status");
                String specs = resultSet.getString("specs");
                apartmentData.add(new ApartmentData(id,name,description,price,type,status, specs));
            }
            FilteredList<ApartmentData> filteredRoom = new FilteredList<>(apartmentData, b -> true);
            //Search Field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredRoom.setPredicate(apartmentSearchModel -> {
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
                    else {
                        return false;
                    }

                });
            });
            setCellTable();
            apartmentTable.setItems(apartmentData);
            SortedList<ApartmentData> sortedApartment = new SortedList<>(filteredRoom);
            sortedApartment.comparatorProperty().bind(apartmentTable.comparatorProperty());
            apartmentTable.setItems(sortedApartment);

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
                String specs = resultSet.getString("specs");
                Boolean status = resultSet.getBoolean("status");
                apartmentData.add(new ApartmentData(id,name,description,price,type,status,specs));
            }
            setCellTable();
            apartmentTable.setItems(apartmentData);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void deleteData(){
        ApartmentData apartment;
        try{
            int selectedIndex = apartmentTable.getSelectionModel().getSelectedIndex();
            apartment = apartmentTable.getSelectionModel().getSelectedItem();
            int tempItemId = apartment.getApartmentID();
            System.out.println(tempItemId);
            String query = "DELETE FROM apartments WHERE id=?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,tempItemId);
            preparedStatement.execute();
            refresh();


        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    public void refresh(){
        apartmentData.clear();
        filterData();
    }

    public void checkPrice(){

    }



}
