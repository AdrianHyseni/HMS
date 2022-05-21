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
  PreparedStatement preparedStatement2 = null;
  PreparedStatement preparedStatement3 = null;
 PreparedStatement preparedStatement4 = null;



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
    private Label totalPriceLabel;
    @FXML
    private TextField discountTf;
    @FXML
    private Label primaryPriceTf;
    @FXML
    private Button reservBtn;
    @FXML
    private Button reservationBtn;
    @FXML
    private Button allReservationBtn;
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
    @FXML
    private Button costsBtn;
    @FXML
    private Button beachBtn;


 @Override
 public void initialize(URL url, ResourceBundle resourceBundle)  {

  setUserInformation(sd.getUsername());
  usernameText.setText("test");
  s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn,costsBtn,beachBtn);
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
  envCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
   @Override
   public void changed(ObservableValue observableValue, Object o, Object t1) {
    try{
     ArrayList<Double> list = new ArrayList<>();
     if(typeCb.getSelectionModel().getSelectedItem().equals("Rooms")){
      list.add(getRoomPrice(t1.toString()));
     }else if(typeCb.getSelectionModel().getSelectedItem().equals("Apartments")){
      list.add(getApartmentPrice(t1.toString()));
     }

    ObservableList list2 = FXCollections.observableArrayList(list);
    list2.forEach(e -> primaryPriceTf.setText(e.toString()));}
    catch (SQLException e ){
     e.printStackTrace();
    }
   }
  });

  discountTf.textProperty().addListener(((observableValue, s1, t1) -> {
   if(checkInDc.getValue()==null || checkOutDc.getValue() == null){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error discount");
    alert.setContentText("Please the check in and check out fields can't be empty  ");
    alert.show();
   } else if (!primaryPriceTf.getText().matches("[0-9]{1,13}(\\\\.[0-9]*)?")) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error discount");
    alert.setContentText("Please the room/apartment fields can't be empty  ");
    alert.show();
   } else {
    if (t1.matches("[0-9]{1,13}(\\\\.[0-9]*)?")) {
     int days = checkOutDc.getValue().getDayOfYear() - checkInDc.getValue().getDayOfYear();
     double price = Double.parseDouble(calculateTotalPrice(primaryPriceTf.getText(), t1, days));
     totalPriceLabel.setText(String.valueOf(price));

    } else {
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setTitle("Error discount");
     alert.setContentText("Please enter a number ");
     alert.show();
    }
   }//end of the if for check in and check out


  }));

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
 public String calculateTotalPrice(String primaryPrice, String discount,int days){
  double totalPrice =0.0;
  if (discountTf.getText().isEmpty() || discountTf.getText().equals("") || discountTf.getText().isBlank()){
   totalPrice = Double.valueOf(primaryPrice)*days;
  }else {
   totalPrice = (Double.valueOf(primaryPrice)*days) - ((Double.valueOf(primaryPrice) * days) *( Double.valueOf(discount)/100));
  }
  return  String.valueOf(totalPrice);
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
   String insertRoomsQuery = "INSERT INTO  booking(type, room_fk, check_in, check_out, totalPrice, created_by, clientName, clientSurname, clientId, clientPhone, dicount, primaryPrice,reference)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
   String insertAptQuery =   "INSERT INTO  booking(type, apartment_fk, check_in, check_out, totalPrice, created_by, clientName, clientSurname, clientId, clientPhone, dicount, primaryPrice,reference) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";


   ArrayList<ReservationData> list = reserveData();
   for(ReservationData r: list){
    try{
     if(typeCb.getSelectionModel().getSelectedItem().equals("Rooms")) {
      updateRooms(r.getEnvStaying());
      preparedStatement4 = con.prepareStatement(insertRoomsQuery);
      preparedStatement4.setString(1,r.getType());
      preparedStatement4.setInt(2, getRoomId(r.getEnvStaying()));
      test(r,preparedStatement4);


     }
     else if(typeCb.getSelectionModel().getSelectedItem().equals("Apartments")) {
      preparedStatement1 = con.prepareStatement(insertAptQuery);
      preparedStatement1.setString(1,r.getType());
      preparedStatement1.setInt(2, getApartmentId(r.getEnvStaying()));
      test(r, preparedStatement1);
      updateApartments(r.getEnvStaying());
     }
     else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("The reservation is not passed through "+typeCb.getSelectionModel().getSelectedItem()+" "+envCb.getSelectionModel().getSelectedItem());
      alert.setTitle("Error");
      alert.show();
     }


   }
    catch (SQLException sqlException){
     sqlException.printStackTrace();
    }

   }
  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
  alert.setContentText("You have reserved the "+typeCb.getSelectionModel().getSelectedItem()+" "+envCb.getSelectionModel().getSelectedItem());
  alert.setTitle("Done");
  alert.show();

 }
 private void test(ReservationData r, PreparedStatement p) throws SQLException {
  p.setObject(3,r.getCheckIn());
  p.setObject(4, r.getCheckOut());
  p.setObject(5, r.getTotalPrice());
  p.setObject(6, getUserId(usernameText.getText()));
  p.setObject(7, r.getClientName());
  p.setObject(8, r.getClientSurname());
  p.setObject(9, r.getClientId());
  p.setObject(10,r.getPhone());
  p.setObject(11, r.getDiscount());
  p.setObject(12, r.getPrimaryPrice());
  p.setObject(13,r.getReference());
  p.executeUpdate();
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
                                Double.valueOf(checkNull(primaryPriceTf.getText())),
                                Double.valueOf(checkNull(discountTf.getText())),
                                Double.valueOf(calculateTotalPrice(totalPriceLabel.getText(),discountTf.getText(),Integer.valueOf(discountTf.getText()))),
                                Double.valueOf(getUserId(usernameText.getText())),
                                checkNull(phoneTf.getText())

                                ));
  rList.forEach(e -> System.out.println(e.getCreatedBy()));
  return rList;
 }
 public String checkStartDate(DatePicker startDate, DatePicker endDate){
  LocalDateTime now = LocalDateTime.now();

  if(startDate.getValue().getDayOfYear() >= now.getDayOfYear()){
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

  if(startDate.getValue().getDayOfYear() >= now.getDayOfYear()){
   return endDate.getValue().toString();
  }else if(startDate.getValue().getDayOfYear()>endDate.getValue().getDayOfYear()){
   return endDate.getValue().toString();
  }else {
   return null;
  }

 }
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
 public double getRoomPrice(String name) throws SQLException{
  double price = 0.0;
  String priceQuery = "SELECT price FROM rooms where name=?";
  preparedStatement =con.prepareStatement(priceQuery);
  preparedStatement.setString(1,name);
  resultSet = preparedStatement.executeQuery();
  while(resultSet.next()){

   price = resultSet.getDouble("price");
 }
  return price;
}
 public double getApartmentPrice(String name) throws SQLException{
  double price = 0.0;
  String priceQuery = "SELECT price FROM apartments where name=?";
  preparedStatement =con.prepareStatement(priceQuery);
  preparedStatement.setString(1,name);
  resultSet = preparedStatement.executeQuery();
  while(resultSet.next()){

   price = resultSet.getDouble("price");
  }
  return price;
 }
 public void setUserInformation(String username){
  usernameText.setText(username);
 }
 public void updateRooms(String name) throws  SQLException{
  String query1 = "update rooms set status=false where name=?";
   preparedStatement2 = con.prepareStatement(query1);
   preparedStatement2.setString(1, name);
   preparedStatement2.executeUpdate();

 }
 public void updateApartments(String name) throws  SQLException{
  String query = "UPDATE APARTMENTS SET status=false WHERE name=?";
  preparedStatement3 = con.prepareStatement(query);
  preparedStatement3.setString(1, name);
  preparedStatement3.executeUpdate();

 }



}
