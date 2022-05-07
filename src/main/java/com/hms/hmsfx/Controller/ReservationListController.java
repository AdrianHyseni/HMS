package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.ReservationData;
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
import java.util.ResourceBundle;

public class ReservationListController implements Initializable {

    ObservableList<ReservationData> reservationData =FXCollections.observableArrayList();
    SystemData sd = new SystemData();
    DatabaseConnection connection = new DatabaseConnection();
    Connection con = connection.getConnection();
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;

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
    @FXML
    private Button allReservationBtn;

    //Table
    @FXML
    private TableView<ReservationData> reservationTable;
    @FXML
    private TableColumn<ReservationData,String> nameCol;
    @FXML
    private TableColumn<ReservationData,String> surnameCol;
    @FXML
    private TableColumn<ReservationData,String> clientIdCol;
    @FXML
    private TableColumn<ReservationData,Integer> phoneCol;
    @FXML
    private TableColumn<ReservationData,String> typeCol;
    @FXML
    private TableColumn<ReservationData,String> envNameCol;
    @FXML
    private TableColumn<ReservationData,String> checkInCol;
    @FXML
    private TableColumn<ReservationData,String> checkOutCol;
    @FXML
    private TableColumn<ReservationData,String> primaryPriceCol;
    @FXML
    private TableColumn<ReservationData,String> totalPriceCol;
    @FXML
    private TableColumn<ReservationData,String> referenceCol;
    @FXML
    private TableColumn<ReservationData,String> discountCol;
    @FXML
    private Button costBtn;



    //Search Field
    @FXML
    private TextField searchField;


    SideBar s = new SideBar();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn,costBtn);
        //Type Choice
        filterData();
        showAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    refresh();
                    getData();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        freeRoomsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getActiveReservation();
            }
        });




    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }


    //divide each data on the right column
    private void setCellTable(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("clientSurname"));
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        envNameCol.setCellValueFactory(new PropertyValueFactory<>("envStaying"));
        referenceCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        primaryPriceCol.setCellValueFactory(new PropertyValueFactory<>("primaryPrice"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));



    }
    //Display Room on the table
    private void showData(){
        getData();
        reservationTable.setItems(reservationData);

    }
    public void filterData() {
        try{
            String query = "SELECT * FROM booking";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            getData();
            FilteredList<ReservationData> reservationList = new FilteredList<>(reservationData, b -> true);
            //Search Field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                reservationList.setPredicate(reservationSearchModel -> {
                    String searchKeyword = newValue.toLowerCase();
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    if(reservationSearchModel.getClientName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(reservationSearchModel.getType().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(reservationSearchModel.getClientId().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(reservationSearchModel.getEnvStaying().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else {
                        return false;
                    }

                });
            });
            setCellTable();
            reservationTable.setItems(reservationData);
            SortedList<ReservationData> sortedRoom = new SortedList<ReservationData>(reservationList);
            sortedRoom.comparatorProperty().bind(reservationTable.comparatorProperty());
            reservationTable.setItems(sortedRoom);

        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    private void reservationData() throws SQLException {
        while(resultSet.next()){


            String clientName = resultSet.getString("clientName");
            String surname = resultSet.getString("clientSurname");
            String clientId = resultSet.getString("clientId");
            String checkIn = resultSet.getString("check_in");
            String checkOut = resultSet.getString("check_out");
            double totalPrice = resultSet.getDouble("totalPrice");
            double discount = resultSet.getDouble("dicount");
            double primaryPrice = resultSet.getDouble("primaryPrice");
            String reference = resultSet.getString("reference");
            String type = resultSet.getString("type");
            String phone = resultSet.getString("clientPhone");
            String envName;
            int createdBy = resultSet.getInt("created_by");
            if (type.equals("Rooms") ){
                int id = resultSet.getInt("room_fk");
                envName = getRoomName(id);
            }else if(type.equals("Apartments")){
                int id = resultSet.getInt("apartment_fk");
                envName = getApartmentsName(id);
            }else{
                envName = null;
            }

            reservationData.add(new ReservationData(clientName,surname,clientId,type,envName,reference,checkIn,checkOut,primaryPrice,discount,totalPrice,createdBy,phone));
        }
    }
    public void getData() {
        try{
            String query = "SELECT * FROM booking";
            preparedStatement = con.prepareStatement(query);
            resultSet1 = preparedStatement.executeQuery();
            while(resultSet1.next()){

                String clientName = resultSet1.getString("clientName");
                String surname = resultSet1.getString("clientSurname");
                String clientId = resultSet1.getString("clientId");
                String checkIn = resultSet1.getString("check_in");
                String checkOut = resultSet1.getString("check_out");
                double totalPrice = resultSet1.getDouble("totalPrice");
                double discount = resultSet1.getDouble("dicount");
                double primaryPrice = resultSet1.getDouble("primaryPrice");
                String reference = resultSet1.getString("reference");
                String type = resultSet1.getString("type");
                String phone = resultSet1.getString("clientPhone");
                String envName;
                int createdBy = resultSet1.getInt("created_by");
                if (type.equals("Rooms") ){
                    int id = resultSet1.getInt("room_fk");
                    envName = getRoomName(id);
                }else if(type.equals("Apartments")){
                    int id = resultSet1.getInt("apartment_fk");
                    envName = getApartmentsName(id);
                }else{
                    envName = null;
                }

                reservationData.add(new ReservationData(clientName,surname,clientId,type,envName,reference,checkIn,checkOut,primaryPrice,discount,totalPrice,createdBy,phone));
            }
            System.out.println(resultSet1.getFetchSize());
            reservationData.forEach(e -> System.out.println(e.getClientId()));


        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void getActiveReservation(){
        try{
            String query = "SELECT * FROM booking where check_out >= now()";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            reservationData.clear();
            getReservationData();


        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void getReservationData() throws SQLException {
        reservationData();

        setCellTable();
        reservationTable.setItems(reservationData);
    }
    public void refresh(){
        reservationData.clear();

    }
    public String getRoomName(int id ) throws SQLException{
        String name ="";
        String query = "SELECT name from rooms where id=?";
        preparedStatement1= con.prepareStatement(query);
        preparedStatement1.setInt(1,id);
        resultSet = preparedStatement1.executeQuery();
        while(resultSet.next()){
            name = resultSet.getString("name");
        }
        return name;
    }
    public String getApartmentsName(int id ) throws SQLException{
        String name ="";
        String query = "SELECT name from apartments where id=?";
        preparedStatement2= con.prepareStatement(query);
        preparedStatement2.setInt(1,id);
        resultSet = preparedStatement2.executeQuery();
        while(resultSet.next()){
            name = resultSet.getString("name");
        }
        return name;
    }





}

