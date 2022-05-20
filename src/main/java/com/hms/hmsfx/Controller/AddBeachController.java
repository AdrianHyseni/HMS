package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.HMSFunctions;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.BeachData;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddBeachController implements Initializable {

    ObservableList<BeachData> beachData =FXCollections.observableArrayList();
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
    @FXML
    private Button allReservationBtn;
    //Add Room
    @FXML
    private  TextField nameTf;
    @FXML
    private TextField priceTf;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button showAllBtn;
    @FXML
    private Button reservationBtn;

    //Table
    @FXML
    private TableView<BeachData> beachTable;
    @FXML
    private TableColumn<BeachData,Integer> idCol;
    @FXML
    private TableColumn<BeachData,String> nameCol;
    @FXML
    private TableColumn<BeachData,Double> priceCol;
    @FXML
    private TableColumn<BeachData,String> dateCol;

    @FXML
    private DatePicker dateBeach;

    //Search Field
    @FXML
    private TextField searchField;
    @FXML
    private Button costsBtn;

    @FXML
    private Button saveExcelBtn;

    @FXML
    private DatePicker dateExcel;
    @FXML
    private  Button beachBtn;

    SideBar s = new SideBar();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn,costsBtn,beachBtn);
        //Type Choice
        filterData();
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addBeach();
                }catch (Exception e){
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There was a problem please try again!");
                    alert.setTitle("Done");
                    alert.show();
                    e.printStackTrace();
                }
            }
        });
        showAllBtn.setOnAction(event -> {
            try {
                beachData.clear();
                getData();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteData();
            }
        });
saveExcelBtn.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent actionEvent) {

        export_excel();
    }
});




    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }

    public  void addBeach()  {
try {

    String query = "insert into  beach (name, date, total_price, created_by) values (?,?,?,?)";
    if (priceTf.getText().matches("\\b\\d+\\b") && !(dateBeach.getValue() == null)) {
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, nameTf.getText());
        preparedStatement.setString(2, dateBeach.getValue().toString());
        preparedStatement.setObject(3, priceTf.getText());
        preparedStatement.setObject(4, 1);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("The daily report is added");
        alert.setTitle("Confirmation");
        alert.show();

        preparedStatement.executeUpdate();


    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please check the data field or price!");
        alert.setTitle("Error");
        alert.show();
    }
}catch (SQLException e){
    e.printStackTrace();
}

    }

    //divide each data on the right column
    private void setCellTable(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    //Display Room on the table


    public void filterData() {
        try{
            String query = "SELECT * FROM beach";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){

                String name = resultSet.getString("name");
                double totalPrice = resultSet.getDouble("total_price");
                String date = resultSet.getString("date");
                beachData.add(new BeachData(name,date,totalPrice));
            }
            FilteredList<BeachData> filteredData = new FilteredList<>(beachData, b -> true);
            //Search Field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(searchModel -> {
                    String searchKeyword = newValue.toLowerCase();
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    if(searchModel.getName().toLowerCase().indexOf(searchKeyword) > -1){

                        return true;
                    }
                    else if(searchModel.getDate().toLowerCase().indexOf(searchKeyword) > -1){

                        return true;
                    }
                    else {

                        return false;
                    }

                });
            });
            setCellTable();
            beachTable.setItems(beachData);
            SortedList<BeachData> sortedBeach = new SortedList<>(filteredData);
            sortedBeach.comparatorProperty().bind(beachTable.comparatorProperty());
            beachTable.setItems(sortedBeach);

        }catch(SQLException e){
            e.printStackTrace();
        }


    }
    public void getData() {
        try{
            String query = "SELECT * FROM beach";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){

                String name = resultSet.getString("name");
                Double totalPrice = resultSet.getDouble("total_price");
                String date = resultSet.getString("date");
                beachData.add(new BeachData(name,date,totalPrice));
            }
            setCellTable();
            beachTable.setItems(beachData);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void deleteData(){
        BeachData beach;
      try{
            beach = beachTable.getSelectionModel().getSelectedItem();
            String tempItemName = beach.getName();
            String query = "DELETE FROM beach WHERE name=?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,tempItemName);
            preparedStatement.execute();
            refresh();


      }catch (SQLException sqlException){
           sqlException.printStackTrace();
       }
    }
    public void refresh(){
        beachData.clear();
        filterData();
    }

    void export_excel() {
        try {

            Stage window = (Stage) saveExcelBtn.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("Beach" + LocalDate.now());
            //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (.csv)", ".csv");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(window);
            FileWriter fileWriter = new FileWriter(file);
            String text = "";

            List<BeachData> result;
            if(dateExcel.getValue() == null){
                result = beachData;
            }else{
                result = sortListByDate(dateExcel.getValue().toString(), beachData);
            }

            String header = "Name" + "," + "Total Earnings" + "," + "Date"+"\n";
            fileWriter.write(header);


            for (int i = 0; i < result.size(); i++) {
                text = result.get(i).getName() + "," + result.get(i).getTotalPrice() + "," + result.get(i).getDate() + ","+"\n";
                fileWriter.write(text);
            }

            fileWriter.close();
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
    }

    public List<BeachData> sortListByDate(String date1, List<BeachData> data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate1 = LocalDate.parse(date1, formatter);

        return data
                .stream()
                .filter(e -> LocalDate.parse(e.getDate(), formatter).isAfter(localDate1))
                .collect(Collectors.toList());
    }






}
