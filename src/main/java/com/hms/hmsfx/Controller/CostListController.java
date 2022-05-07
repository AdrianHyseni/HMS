package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.CostData;
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
import java.util.concurrent.atomic.AtomicReference;

public class CostListController implements Initializable {

    ObservableList<CostData> costData =FXCollections.observableArrayList();
    SystemData sd = new SystemData();
    DatabaseConnection connection = new DatabaseConnection();
    Connection con = connection.getConnection();
    ResultSet resultSet = null;
    ResultSet resultSet1 = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;

    @FXML
    private Label usernameText;
    @FXML
    private Label costLabel;
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
    private Button showAllBtn;
    @FXML
    private Button reservationBtn;
    @FXML
    private Button allReservationBtn;

    //Table
    @FXML
    private TableView<CostData> costTable;
    @FXML
    private TableColumn<CostData,?> titleCol;
    @FXML
    private TableColumn<CostData,?> billNameCol;
    @FXML
    private TableColumn<CostData,?> priceCostCol;
    @FXML
    private TableColumn<CostData,?> typeCol;
    @FXML
    private TableColumn<CostData,?> dateCol;
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
        calculateTotalCost();
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





    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }


    //divide each data on the right column
    private void setCellTable(){
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCostCol.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        billNameCol.setCellValueFactory(new PropertyValueFactory<>("billName"));

    }
    //Display Room on the table
    private void showData(){

        getData();
        costTable.setItems(costData);


    }
    public void filterData() {
        try{
            String query = "SELECT * FROM costs";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){


                String title = resultSet.getString("title");
                String type = resultSet.getString("type");
                Double costInEu = resultSet.getDouble("costInEu");
                String date = resultSet.getString("date");
                int costBillFk = resultSet.getInt("cost_bill_fk");

                costData.add(new CostData(type,title,date,costInEu,getBillName(costBillFk)));
            }
            FilteredList<CostData> filteredApartment = new FilteredList<>(costData, b -> true);
            //Search Field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredApartment.setPredicate(apartmentSearchModel -> {
                    String searchKeyword = newValue.toLowerCase();
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    if(apartmentSearchModel.getBillName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(apartmentSearchModel.getDate().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(apartmentSearchModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else if(apartmentSearchModel.getType().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else {
                        return false;
                    }

                });
            });
            setCellTable();
            costTable.setItems(costData);
            SortedList<CostData> sortedCostLis = new SortedList<>(filteredApartment);
            sortedCostLis.comparatorProperty().bind(costTable.comparatorProperty());
            costTable.setItems(sortedCostLis);

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

                String title = resultSet.getString("title");
                String type = resultSet.getString("type");
                Double costInEu = resultSet.getDouble("costInEu");
                String date = resultSet.getString("date");
                int costBillFk = resultSet.getInt("cost_bill_fk");

                costData.add(new CostData(type,title,date,costInEu,getBillName(costBillFk)));
            }
            setCellTable();
            costTable.setItems(costData);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void refresh(){
        costData.clear();
        filterData();
    }
    public void  calculateTotalCost(){
        AtomicReference<Double> tc = new AtomicReference<>((double) 0);
        costData.forEach(e -> {
            tc.updateAndGet(v -> new Double((double) (v + e.getCostPrice())));
        });
        costLabel.setText(String.valueOf(tc));
    }
    public String getBillName(int i) throws SQLException {
        String query = "Select title FROM costbill where id=?";
        String name ="";
        preparedStatement2 = con.prepareStatement(query);
        preparedStatement2.setObject(1,i);
        resultSet1 = preparedStatement2.executeQuery();
        while(resultSet1.next()){
            name =  resultSet1.getString("title");
        }
        return name;
    }





}

