package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.ReservationData;
import com.hms.hmsfx.data.SystemData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

   //Data
  SystemData sd = new SystemData();
  SideBar s = new SideBar();
  ReservationData rd = new ReservationData();
  //Database
  DatabaseConnection connection = new DatabaseConnection();
  Connection con = connection.getConnection();
  ResultSet resultSet = null;
  PreparedStatement preparedStatement = null;
 PreparedStatement preparedStatement1 = null;
   //Reservation
    @FXML
    private TextField nameTf;
    @FXML
    private TextField surnameTf;
    @FXML
    private TextField clientIdTf;
    @FXML
    private TextField phoneTf;
    @FXML
    private ChoiceBox typeCb;
    @FXML
    private ChoiceBox envCb;
    @FXML
    private ChoiceBox referenceCb;
    @FXML
    private DatePicker checkInDc;
    @FXML
    private DatePicker checkOutDc;
    @FXML
    private TextField totalPriceTf;
    @FXML
    private TextField discountTf;
    @FXML
    private TextField primaryPriceTf;
    @FXML
    private Button reservBtn;
    //Side
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


 @Override
 public void initialize(URL url, ResourceBundle resourceBundle)  {

  setUserInformation(sd.getUsername());
  usernameText.setText("test");
  s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn);
  ArrayList<String> type =new ArrayList<>();
  type.add("Apartments");
  type.add("Rooms");
  ArrayList<String> refType =new ArrayList<>();
  refType.add("Booking");
  refType.add("Web");
  refType.add("AirBnB");
  refType.add("Telephone");
  refType.add("On site");
  ObservableList<String> list = FXCollections.observableArrayList(type);
  ObservableList<String> refList = FXCollections.observableArrayList(refType);
  typeCb.setItems(list);
  referenceCb.setItems(refList);
  envCb.setItems(freeSpaces("Rooms"));
  typeCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
   @Override
   public void changed(ObservableValue observableValue, Object o, Object t1) {
    ObservableList list2 = FXCollections.observableArrayList(freeSpaces(t1.toString()));
    envCb.setItems(list2);
   }
  });
  reservBtn.setOnAction(new EventHandler<ActionEvent>() {
   @Override
   public void handle(ActionEvent event) {
    try {
     System.out.println(checkStartDate(checkInDc,checkOutDc));

     makeReservation();
    }catch (Exception e){
     e.printStackTrace();
    }
   }
  });
 }


 public int checkDiscount(String text){
  if(text.matches("\\b\\d+\\b")){
    if(Integer.valueOf(text)<1){
     return Integer.valueOf(text);
    }
    else{
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setContentText("There was a problem please Check discount field, it must be  a number between 0-1!");
     alert.setTitle("Discount Error");
     alert.show();
    }
  }else{
   Alert alert = new Alert(Alert.AlertType.ERROR);
   alert.setContentText("There was a problem! Discount Must be a number!");
   alert.setTitle("Discount Error");
   alert.show();
  }
  return 0;
 }
 public String checkNull(String text){
  if(text.equals("") || text.isBlank() || text.isEmpty() || text==null){
   Alert alert = new Alert(Alert.AlertType.ERROR);
   alert.setContentText("There was a problem! Fields must not be empty!");
   alert.setTitle("Fields Error");
   alert.show();
   return null;
  }
  else {
   return text;
  }
 }
 public int getUserId(String text) throws SQLException {
  int id = 0;
    String getFkUser = "SELECT * FROM user WHERE username=?";
    preparedStatement = con.prepareStatement(getFkUser);
    preparedStatement.setString(1,text);
    resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){id = resultSet.getInt("id");}
    return id;

 }
 public int getApartmentId(String text) throws SQLException{
  int id = 0;
    String getFkApartments = "SELECT * FROM apartments WHERE name=?";
    preparedStatement = con.prepareStatement(getFkApartments);
    preparedStatement.setString(1,text);
    resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){id = resultSet.getInt("id");}
    return id;

 }
 public int getRoomId(String text) throws SQLException{
    int id = 0;
    String getFkRoom = "SELECT * FROM rooms WHERE name=?";
    preparedStatement = con.prepareStatement(getFkRoom);
    preparedStatement.setString(1,text);
    resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){id = resultSet.getInt("id");}
    return id;
 }
 public void makeReservation() throws SQLException {

   reserveData();
   String insertRoomsQuery = "INSERT INTO   booking(type, room_fk, apartment_fk, check_in, check_out, price, created_by, clientName, clientSurname, clientId, clientPhone)VALUES (?,?,null,?,?,?,?,?,?,?,?)";
  String insertAptQuery = "INSERT INTO  booking(type, room_fk, apartment_fk, check_in, check_out, price, created_by, clientName, clientSurname, clientId, clientPhone) VALUES (?,null,?,?,?,?,?,?,?,?,?)";


   ArrayList<ReservationData> list = reserveData();
   for(ReservationData r: list){
    try{


     if(typeCb.getSelectionModel().getSelectedItem().equals("Rooms")) {
      preparedStatement = con.prepareStatement(insertRoomsQuery);
      preparedStatement.setString(1,r.getType());
      preparedStatement.setInt(2, getRoomId(r.getEnvStaying()));
      test(r, preparedStatement);
     }else if(typeCb.getSelectionModel().getSelectedItem().equals("Apartments")) {
      preparedStatement1 = con.prepareStatement(insertAptQuery);
      preparedStatement1.setString(1,r.getType());
      preparedStatement1.setInt(2, getApartmentId(r.getEnvStaying()));
      test(r, preparedStatement1);
     }
     else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("The reservation is not passed through "+typeCb.getSelectionModel().getSelectedItem()+" "+envCb.getSelectionModel().getSelectedItem());
      alert.setTitle("Error");
      alert.show();
     }

     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     alert.setContentText("You have reserved the "+typeCb.getSelectionModel().getSelectedItem()+" "+envCb.getSelectionModel().getSelectedItem());
     alert.setTitle("Done");
     alert.show();

   }
    catch (SQLException sqlException){
     sqlException.printStackTrace();
    }

   }






 }

 private void test(ReservationData r, PreparedStatement preparedStatement1) throws SQLException {
  preparedStatement1.setString(3,r.getCheckIn());
  preparedStatement1.setString(4, r.getCheckOut());
  preparedStatement1.setInt(5, r.getPrimaryPrice());
  preparedStatement1.setInt(6, getUserId(usernameText.getText()));
  preparedStatement1.setString(7, r.getClientName());
  preparedStatement1.setString(8, r.getClientSurname());
  preparedStatement1.setString(9, r.getClientId());
  preparedStatement1.setString(10, phoneTf.getText());
  preparedStatement1.executeUpdate();
 }

 public ArrayList<ReservationData> reserveData() throws SQLException {
  ArrayList<ReservationData> rList = new ArrayList<>();
  rList.add(new ReservationData(checkNull(nameTf.getText()),
                                checkNull(surnameTf.getText()),
                                checkNull(clientIdTf.getText()),
                                typeCb.getSelectionModel().getSelectedItem().toString(),
                                envCb.getSelectionModel().getSelectedItem().toString(),
                                referenceCb.getSelectionModel().getSelectedItem().toString(),
                                checkStartDate(checkInDc,checkOutDc),
                                checkEndDate(checkInDc,checkOutDc),
                                Integer.valueOf(checkNull(primaryPriceTf.getText())),
                                Integer.valueOf(checkNull(discountTf.getText())),
                                Integer.valueOf(checkNull(totalPriceTf.getText())),
                                Integer.valueOf(getUserId(usernameText.getText()))
                                ));
  rList.forEach(e -> System.out.println(e.getCreatedBy()));
  return rList;
 }

 public String checkStartDate(DatePicker startDate, DatePicker endDate){
  LocalDateTime now = LocalDateTime.now();

  if(startDate.getValue().getDayOfYear() > now.getDayOfYear()){
    return startDate.getValue().toString();
  }else if(startDate.getValue().getDayOfYear()>endDate.getValue().getDayOfYear()){
   return startDate.getValue().toString();
  }else {
   Alert alert = new Alert(Alert.AlertType.ERROR);
   alert.setContentText("There was a problem in Date! Check in date must be more than current time and less than checkout time");
   alert.setTitle("Date Error");
   alert.show();
   return null;
  }

 }

 public String checkEndDate(DatePicker startDate, DatePicker endDate){
  LocalDateTime now = LocalDateTime.now();

  if(startDate.getValue().getDayOfYear() > now.getDayOfYear()){
   return endDate.getValue().toString();
  }else if(startDate.getValue().getDayOfYear()>endDate.getValue().getDayOfYear()){
   return endDate.getValue().toString();
  }else {
   return null;
  }

 }


 //Check for the free spaces on Hotel
 public ObservableList freeSpaces(String env){
  ObservableList<String> list = null;
  try {
   if (env.equals("Rooms")) {
    String freeRooms = "SELECT name FROM ROOMS WHERE status=true";
    preparedStatement = con.prepareStatement(freeRooms);
    resultSet = preparedStatement.executeQuery();
    ArrayList<String> type = new ArrayList<>();
    type.clear();
    while(resultSet.next()){
      type.add(resultSet.getString("name"));
    }
    list = FXCollections.observableArrayList(type);

   } else if (env.equals("Apartments")) {
    String freeApartments = "SELECT name FROM APARTMENTS WHERE status=true";
    preparedStatement = con.prepareStatement(freeApartments);
    resultSet = preparedStatement.executeQuery();
    ArrayList<String> type = new ArrayList<>();
    type.clear();
    while(resultSet.next()){
     type.add(resultSet.getString("name"));
    }
    list = FXCollections.observableArrayList(type);
   } else {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText("There was a problem please Check Type field, it must be  an Apartment or a Room ");
    alert.setTitle("Type Error");
    alert.show();
   }
  }catch (SQLException sqle){
    sqle.printStackTrace();
  }
  return list;

 }


 public void setUserInformation(String username){
  usernameText.setText(username);
 }


}
