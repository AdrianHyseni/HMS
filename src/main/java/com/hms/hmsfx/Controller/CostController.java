package com.hms.hmsfx.Controller;

import com.hms.hmsfx.DatabaseConnection;
import com.hms.hmsfx.SideBar;
import com.hms.hmsfx.data.ApartmentData;
import com.hms.hmsfx.data.CostData;
import com.hms.hmsfx.data.SystemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class CostController implements Initializable {
    ObservableList<ApartmentData> apartmentData = FXCollections.observableArrayList();
    SystemData sd = new SystemData();
    //Database
    DatabaseConnection connection = new DatabaseConnection();
    Connection con = connection.getConnection();
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement4 = null;



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
    private Button reservationBtn;
    @FXML
    private Button allReservationBtn;
    //Add Cost
    @FXML
    private TextField nameTf;
    @FXML
    private ChoiceBox typeCb;
    @FXML
    private TextField costTf;
    @FXML
    private DatePicker date;
    @FXML
    private Button addCostBtn;
    @FXML
    private Label costPriceLabel;
    @FXML
    private Button removeBtn;
    @FXML
    private Button saveBtn;

    //Table
    @FXML
    private TableView<CostData> costDataTableView;
    @FXML
    private TableColumn<CostData,Integer> typeCol;
    @FXML
    private TableColumn<CostData,String> nameCol;
    @FXML
    private TableColumn<CostData,String> dateCol;
    @FXML
    private TableColumn<CostData,Integer> priceCostCol;
    @FXML
    private  Button printBtn;
    @FXML
    private TextField titleTf;
    //Search Field



    SideBar s = new SideBar();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        setUserInformation(sd.getUsername());
        s.sideBar(profileBtn,logoutBtn,settingsBtn,roomBtn,homeBtn,apartmentBtn,reservationBtn,allReservationBtn);
        //Type Choice
        ArrayList<String> type =new ArrayList<>();
        type.add("Rent costs and property taxes");
        type.add("Human resources cost");
        type.add("Employee healthcare");
        type.add("Contracted services costs");
        type.add("Internet, phone and television ");
        type.add("Hotel management software costs");
        type.add("Food and beverage costs");
        type.add("Utilities");
        type.add("Housekeeping supplies costs");
        type.add("Marketing costs");
        type.add("Decor and floral arrangement costs");
        type.add("Linen and laundry operations costs");
        type.add("Hourly wages cost");
        ObservableList<String> list = FXCollections.observableArrayList(type);
        typeCb.setItems(list);
        addCostBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addToCostModel();
                calculateTotalCost();
            }
        });
        removeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                removeFromTable();
                calculateTotalCost();
            }
        });
        setTableCells();
        costDataTableView.setItems(costModel);
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveData();
            }
        });


    }

    public void setUserInformation(String username){
        usernameText.setText(username);
    }

    public void addToTable(){}
    public void setTableCells(){
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            priceCostCol.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
    }
    private ObservableList<CostData> costModel = FXCollections.observableArrayList();
    private void addToCostModel(){
        costModel.add( new CostData(typeCb.getSelectionModel().getSelectedItem().toString(),
                nameTf.getText(),
                date.getValue().toString(),
                Double.valueOf(costTf.getText())));
    }
    private void removeFromTable(){
            int selectedIndex = costDataTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                costDataTableView.getItems().remove(selectedIndex);
            } else {
                // Nothing selected.
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Person Selected");
                alert.setContentText("Please select a person in the table.");
                alert.showAndWait();
            }
    }
    private void calculateTotalCost(){
        AtomicReference<Double> sum= new AtomicReference<>((double) 0);
        costModel.forEach(e -> {
            sum.updateAndGet(v -> new Double((double) (v + e.getCostPrice())));
            costPriceLabel.setText(String.valueOf(sum.get()));
        });
    }
    private void saveData() {
        String insertIntoBills ="insert into costbill(title) VALUES (?)";
        try {
            preparedStatement2 = con.prepareStatement(insertIntoBills);
            preparedStatement2.setObject(1, nameTf.getText());
            preparedStatement2.executeUpdate();


        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

        String insertIntoCosts ="insert into costs(title, type,costInEu,created_by,date,cost_bill_fk) values(?,?,?,?,?,?)";
        costModel.forEach(e ->{
            try{
                        preparedStatement = con.prepareStatement(insertIntoCosts);
                        preparedStatement.setObject(1,e.getName());
                        preparedStatement.setObject(2,e.getType());
                        preparedStatement.setObject(3,e.getCostPrice());
                        preparedStatement.setObject(4,1);
                        preparedStatement.setObject(5,e.getDate());
                        preparedStatement.setObject(6,getBillId());
                        preparedStatement.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Data Added on Database");
                        alert.setContentText("Success data created on the database.");
                        alert.showAndWait();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });


    }
    private int getId() throws SQLException {
        String getId = "select id from user where name=?";
        int id=0;
        preparedStatement3 = con.prepareStatement(getId);
        preparedStatement3.setObject(1,usernameText.getText());
        resultSet = preparedStatement3.executeQuery();
        while (resultSet.next()){
            id = resultSet.getInt("id");
        }
        return id;
    }
    private int getBillId() throws SQLException {
        String getId = "select id from costbill where title=?";
        int id=0;
        preparedStatement3 = con.prepareStatement(getId);
        preparedStatement3.setObject(1,titleTf.getText());
        resultSet = preparedStatement3.executeQuery();
        while (resultSet.next()){
            id = resultSet.getInt("id");
        }
        return id;
    }

}
