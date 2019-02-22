/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeView.EditEvent;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.sqlite.SQLiteException;




/**
 *This is a simple rental schedule recording program that gets input 
 *from the user and saves the data to SQlite database.
 * 
 * @author Mike
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane motherPane;
    @FXML
    private AnchorPane topbar;
    @FXML
    private ImageView b_Payment;
    @FXML
    private ImageView b_House;
    @FXML
    private ImageView b_monthly;
    @FXML
    private AnchorPane h_PaymentDetails;
    @FXML
    private AnchorPane h_HouseDetails;
    @FXML
    private AnchorPane h_MonthlyExpenditure;
    @FXML
    private TitledPane HouseBlocks;
    @FXML
    private TitledPane HouseBlocks2;
    @FXML
    private TitledPane HouseBlocks3;
    @FXML
    private ImageView b_TenantDetails;
    @FXML
    private AnchorPane h_TenantDetails;
    @FXML
    public Label tenantData;
    @FXML
    public Label repairsData;
    @FXML
    public Label paymentData;
    @FXML
    public Label monthlyExpenseData;
    
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getTarget() == b_House || event.getTarget() == repairsData) {
            h_HouseDetails.setVisible(true);
            h_PaymentDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_TenantDetails.setVisible(false);
            repairsData.setTextFill(Paint.valueOf("#5FF8FF"));
            paymentData.setTextFill(Paint.valueOf("#fdfdfd"));
            monthlyExpenseData.setTextFill(Paint.valueOf("#fdfdfd"));
            tenantData.setTextFill(Paint.valueOf("#fdfdfd"));
        } else if (event.getTarget() == b_Payment || event.getTarget() == paymentData) {
            h_PaymentDetails.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_TenantDetails.setVisible(false);
            paymentData.setTextFill(Paint.valueOf("#5FF8FF"));
            repairsData.setTextFill(Paint.valueOf("#fdfdfd"));
            monthlyExpenseData.setTextFill(Paint.valueOf("#fdfdfd"));
            tenantData.setTextFill(Paint.valueOf("#fdfdfd"));
        } else if (event.getTarget() == b_monthly || event.getTarget() == monthlyExpenseData) {
            h_MonthlyExpenditure.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_PaymentDetails.setVisible(false);
            h_TenantDetails.setVisible(false);
            monthlyExpenseData.setTextFill(Paint.valueOf("#5FF8FF"));
            paymentData.setTextFill(Paint.valueOf("#fdfdfd"));
            repairsData.setTextFill(Paint.valueOf("#fdfdfd"));
            tenantData.setTextFill(Paint.valueOf("#fdfdfd"));
        } else if (event.getTarget()== b_TenantDetails || event.getTarget() == tenantData){
            h_TenantDetails.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_PaymentDetails.setVisible(false);
            tenantData.setTextFill(Paint.valueOf("#5FF8FF"));
            monthlyExpenseData.setTextFill(Paint.valueOf("#fdfdfd"));
            paymentData.setTextFill(Paint.valueOf("#fdfdfd"));
            repairsData.setTextFill(Paint.valueOf("#fdfdfd"));
        }
    }
    
    ObservableList<String>HouseNumber2 = FXCollections.observableArrayList("B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12");
    ObservableList<String>HouseNumber = FXCollections.observableArrayList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12");
    ObservableList<String>HouseNumber3 = FXCollections.observableArrayList("C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10");
    ObservableList<String>HouseNumber4 = FXCollections.observableArrayList("Top House", "Bottom House");
    ObservableList<String>MonthPaid = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    
    
    @FXML
    private ComboBox MonthBox;
    @FXML
    private void initializeMonthBox(){
        MonthBox.setItems(MonthPaid);
    }
    
    @FXML
    public ComboBox MonthBox1;
    
    @FXML
    private ComboBox MonthBox2;
    
    @FXML
    private TextField Amount;
    
    @FXML
    private TextField Name;
    
    @FXML
    private TextField TName;
    
    @FXML
    private TextField Repairs;
    
    @FXML
    private TextField repairCost;
    
    @FXML
    private TextField miscellaneous;
   
    @FXML
    private TextField WaterBill;
    
    @FXML
    private TextField ElectricityBill;
    
    @FXML
    private TextField Expenditure;
    
    @FXML
    private TextField ReasonForExpense;
    
    @FXML
    private TextField TNameDetails;
    
    @FXML
    private TextField TenantPhoneNumber;
    
    @FXML
    private TextField RentAmount;
    
    @FXML
    private TextField DueDate;
    
    @FXML
    private TextField RentDeposit;
    
    @FXML
    private DatePicker MoveInDate;
    
    @FXML
    private TextField MoveOutDate;
    
    @FXML
    private DatePicker LeaseStartDate;
    
    @FXML
    private TextField LeaseEndDate;
    
    @FXML
    private DatePicker RentPaymentDate;
    
    @FXML
    private DatePicker RepairsDate;
    
    @FXML
    private Label HouseVacant;
    @FXML
    private Label HouseOccupied;
    
    private Node getByUserData(Parent parent, Object data){
        for(Node n : parent.getChildrenUnmodifiable()){
            if (data.equals(n.getUserData())){
                return n;
            }
        }
        return null;
    }
    
    @FXML
    private void createForeignCascade(String HouseNumber, String PayerName, String Amount, String Month, String PaymentMethod) throws IOException, SQLException {
        List<String> data1 = new ArrayList<>();
        
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql = "CREATE TABLE IF NOT EXISTS TenantDetails(HouseNumber text, Amount text NOT NULL, PayerName text NOT NULL, Month text NOT NULL, Date text, PaymentMethod text, FOREIGN KEY (HouseNumber) REFERENCES JatomTenantDetails(HouseNumber) ON DELETE CASCADE)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (TNameDetails.getText().equalsIgnoreCase("vacant")) {
            //Saving data to file
            Connection conn = DriverManager.getConnection(url);
            String selectarchive = "SELECT * FROM TenantDetails where HouseNumber = ? AND Amount IS NOT NULL AND Month IS NOT NULL";
            try {
                if (checkComboBoxDetails.equals("BlockA")) {
                    PreparedStatement pstmt = conn.prepareStatement(selectarchive);
                    pstmt.setString(1, selectHouseBoxDetails.getSelectionModel().getSelectedItem().toString());
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String HN = rs.getString("HouseNumber");
                        String PN = rs.getString("PayerName");
                        String AM = rs.getString("Amount");
                        String M = rs.getString("Month");
                        String D = rs.getString("Date");
                        data1.add(HN);
                        data1.add(PN);
                        data1.add(AM);
                        data1.add(M);
                        data1.add(D);
                    }
                    pstmt.close();
                    rs.close();
                }else if (checkComboBoxDetails.equals("BlockB")) {
                    PreparedStatement pstmt1 = conn.prepareStatement(selectarchive);
                    pstmt1.setString(1, selectBlockBDetails.getSelectionModel().getSelectedItem().toString());
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs1.next()) {
                        String HN = rs1.getString("HouseNumber");
                        String PN = rs1.getString("PayerName");
                        String AM = rs1.getString("Amount");
                        String M = rs1.getString("Month");
                        String D = rs1.getString("Date");
                        data1.add(HN);
                        data1.add(PN);
                        data1.add(AM);
                        data1.add(M);
                        data1.add(D);
                    }
                    pstmt1.close();
                    rs1.close();
                }else if (checkComboBoxDetails.equals("BlockC")) {
                    PreparedStatement pstmt2 = conn.prepareStatement(selectarchive);
                    pstmt2.setString(1, selectBlockCDetails.getSelectionModel().getSelectedItem().toString());
                    ResultSet rs2 = pstmt2.executeQuery();
                    while (rs2.next()) {
                        String HN = rs2.getString("HouseNumber");
                        String PN = rs2.getString("PayerName");
                        String AM = rs2.getString("Amount");
                        String M = rs2.getString("Month");
                        String D = rs2.getString("Date");
                        data1.add(HN);
                        data1.add(PN);
                        data1.add(AM);
                        data1.add(M);
                        data1.add(D);
                    }
                    pstmt2.close();
                    rs2.close();
                }else if (checkComboBoxDetails.equals("NasraBlock")) {
                    PreparedStatement pstmt3 = conn.prepareStatement(selectarchive);
                    pstmt3.setString(1, selectNasraBlockDetails.getSelectionModel().getSelectedItem().toString());
                    ResultSet rs3 = pstmt3.executeQuery();
                    while (rs3.next()) {
                        String HN = rs3.getString("HouseNumber");
                        String PN = rs3.getString("PayerName");
                        String AM = rs3.getString("Amount");
                        String M = rs3.getString("Month");
                        String D = rs3.getString("Date");
                        data1.add(HN);
                        data1.add(PN);
                        data1.add(AM);
                        data1.add(M);
                        data1.add(D);
                    }
                    pstmt3.close();
                    rs3.close();
                }
                
                for (int i = 0; i < data1.size(); i++){
                    System.out.println(data1.get(i));
                }
                Boolean checkdata = data1.isEmpty();
                System.out.println(checkdata);
                
                //Writing arraylist to file
                File file = new File("C:\\Users\\Mike\\Documents\\NetBeansProjects\\RentalUI\\TenantDetails.txt");
                final String value = System.lineSeparator();
                FileWriter fr = new FileWriter(file, true);
                for (String str: data1) {
                    fr.write(str);
                    fr.write(value);
                }
                fr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String delete = "DELETE FROM TenantDetails where HouseNumber  = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(delete);
                PreparedStatement pstmt1 = conn.prepareStatement(delete);
                PreparedStatement pstmt2 = conn.prepareStatement(delete);
                PreparedStatement pstmt3 = conn.prepareStatement(delete);
                pstmt.setString(1, (String) selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                pstmt1.setString(1, (String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                pstmt2.setString(1, (String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                pstmt3.setString(1, (String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                pstmt.execute();
                pstmt1.execute();
                pstmt2.execute();
                pstmt3.execute();
                pstmt.close();
                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String sql1 = "INSERT INTO TenantDetails (HouseNumber, PayerName) VALUES(?,?)";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql1);
                pstmt.setString(1, HouseNumber);
                pstmt.setString(2, PayerName);
                pstmt.execute();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    //Updating JatomAptDetails table
    private void insertJatomAptDetails(String HNumber, String Tenantname, String RepairsOnHouse, String CostOfRepair, String MiscellaneousExpenses, String Date){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql = "INSERT INTO JatomAptsDetails (HNumber, TenantName, RepairsOnHouse, CostOfRepair, MiscellaneousExpenses, Date) VALUES (?,?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, HNumber);
            pstmt.setString(2, Tenantname);
            pstmt.setString(3, RepairsOnHouse);
            pstmt.setString(4, CostOfRepair);
            pstmt.setString(5, MiscellaneousExpenses);
            pstmt.setString(6, Date);
            pstmt.execute();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Updating JatomApts table
    private void insertJatomApts(String HouseNumber, String Amount, String PayerName, String Month, String Date, String Payment){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql = "INSERT INTO TenantDetails (HouseNumber, Amount, PayerName, Month, Date, PaymentMode) VALUES (?,?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, HouseNumber);
            pstmt.setString(2, Amount);
            pstmt.setString(3, PayerName);
            pstmt.setString(4, Month);
            pstmt.setString(5, Date);
            pstmt.setString(6, Payment);
            pstmt.execute();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTable2(String HNumber, String TenantName) {
        if (TNameDetails.getText().equalsIgnoreCase("vacant")) {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String delete = "DELETE FROM JatomAptsDetails where HNumber  = ?";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(delete);
                PreparedStatement pstmt1 = conn.prepareStatement(delete);
                PreparedStatement pstmt2 = conn.prepareStatement(delete);
                PreparedStatement pstmt3 = conn.prepareStatement(delete);
                pstmt.setString(1, (String) selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                pstmt1.setString(1, (String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                pstmt2.setString(1, (String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                pstmt3.setString(1, (String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                pstmt.execute();
                pstmt1.execute();
                pstmt2.execute();
                pstmt3.execute();
                pstmt.close();
                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String sql2 = "CREATE TABLE IF NOT EXISTS JatomAptsDetails (HNumber text PRIMARY KEY, TenantName text, RepairsOnHouse text, CostOfRepair text, MiscellaneousExpenses text, FOREIGN KEY (TenantName) REFERENCES JatomApts (HouseNumber))";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(sql2);
                stmt.executeUpdate();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql3 = "INSERT INTO JatomAptsDetails (HNumber, TenantName, RepairsOnHouse, CostOfRepair, MiscellaneousExpenses) VALUES(?,?,?,?,?)";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql3);
                pstmt.setString(1, HNumber);
                pstmt.setString(2, TenantName);
                pstmt.execute();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    
    private void createTable3(String Month, String WaterBill, String ElectricityBill){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql4 = "CREATE TABLE IF NOT EXISTS RecurrentVariableMonthlyExpenditure(\n"
                +"Month text, \n"
                +"WaterBill text, \n"
                +"ElectricityBill text \n"
                +");";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql4);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql5 = "INSERT INTO RecurrentVariableMonthlyExpenditure (Month, WaterBill, ElectricityBill) VALUES(?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql5);
            pstmt.setString(1, Month);
            pstmt.setString(2, WaterBill);
            pstmt.setString(3, ElectricityBill);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void createTable4(String Month, String Expenditure, String ReasonForExpense){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String sql6 = "CREATE TABLE IF NOT EXISTS OtherMonthlyExpenditures (\n"
                +"Month text, \n"
                +"Expenditure text, \n"
                +"ReasonForExpense text \n"
                +");";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql6);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql7 = "INSERT INTO OtherMonthlyExpenditures (Month, Expenditure, ReasonForExpense) VALUES(?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql7);
            pstmt.setString(1, Month);
            pstmt.setString(2, Expenditure);
            pstmt.setString(3, ReasonForExpense);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTenantDetailstable(String HouseNumber, String TenantName, String TenantPhoneNumber, String RentAmount, String DueDate, String Deposit, String MoveInDate, String MoveOutDate, String LeaseStartDate, String LeaseEndDate) throws SQLException {
        if (TNameDetails.getText().equalsIgnoreCase("vacant")) {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String delete = "DELETE FROM JatomTenantDetails where HouseNumber  = ?";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(delete);
                PreparedStatement pstmt1 = conn.prepareStatement(delete);
                PreparedStatement pstmt2 = conn.prepareStatement(delete);
                PreparedStatement pstmt3 = conn.prepareStatement(delete);
                pstmt.setString(1, (String) selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                pstmt1.setString(1, (String) selectBlockBDetails.getSelectionModel().getSelectedItem());
                pstmt2.setString(1, (String) selectBlockCDetails.getSelectionModel().getSelectedItem());
                pstmt3.setString(1, (String) selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                pstmt.execute();
                pstmt1.execute();
                pstmt2.execute();
                pstmt3.execute();
                pstmt.close();
                pstmt1.close();
                pstmt2.close();
                pstmt3.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String sql = "CREATE TABLE IF NOT EXISTS JatomTenantDetails (HouseNumber text PRIMARY KEY, TenantName text, TenantPhoneNumber text, RentAmount text, DueDate text, Deposit text, MoveInDate text, MoveOutDate text, LeaseStartDate text, LeaseEndDate text)";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.execute();
                pstmt.close();
                conn.close();
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            String sql1 = "INSERT INTO JatomTenantDetails (HouseNumber, TenantName, TenantPhoneNumber, RentAmount, DueDate, Deposit, MoveInDate, MoveOutDate, LeaseStartDate, LeaseEndDate) VALUES(?,?,?,?,?,?,?,?,?,?)";
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql1);
                pstmt.setString(1, HouseNumber);
                pstmt.setString(2, TenantName);
                pstmt.setString(3, TenantPhoneNumber);
                pstmt.setString(4, RentAmount);
                pstmt.setString(5, DueDate);
                pstmt.setString(6, Deposit);
                pstmt.setString(7, MoveInDate);
                pstmt.setString(8, MoveOutDate);
                pstmt.setString(9, LeaseStartDate);
                pstmt.setString(10, LeaseEndDate);
                pstmt.execute();
                pstmt.close();
                conn.close();
            } catch (SQLiteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error while saving to Database");
                alert.setContentText("The selected house is already occupied. Please confirm the house number and try again");
                alert.showAndWait();
            }
        }
    }

    
    
    @FXML
    private void tableInsertTenantDetails() throws SQLException, IOException{
        LocalDate time = MoveInDate.getValue();
        LocalDate leaseStartTime = LeaseStartDate.getValue();
        
        String moveInString = null;
        if (MoveInDate.getValue() == null){
            moveInString = null;
        }else if (MoveInDate.getValue() != null){
            moveInString = time.format(DateTimeFormatter.ISO_DATE);
        }
        
        String leaseStartString = null;
        if (LeaseStartDate.getValue() == null){
            leaseStartString = null; 
        }else if(LeaseStartDate != null){
            leaseStartString = leaseStartTime.format(DateTimeFormatter.ISO_DATE);
        }
        Optional<LocalDate> LD = Optional.ofNullable(time);
        
        if (checkComboBoxDetails == "BlockA"){
            this.createTenantDetailstable((String) selectHouseBoxDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), moveInString, MoveOutDate.getText(), leaseStartString, LeaseEndDate.getText());
            this.createTable2((String)selectHouseBoxDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText());
            this.createForeignCascade((String)selectHouseBoxDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), Amount.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), paymentMode);
            selectHouseBoxDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setValue(null);
            MoveOutDate.setText("");
            LeaseStartDate.setValue(null);
            LeaseEndDate.setText("");
        }else if (checkComboBoxDetails == "BlockB") {
            this.createTenantDetailstable((String)selectBlockBDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), moveInString, MoveOutDate.getText(), leaseStartString, LeaseEndDate.getText());
            this.createTable2((String)selectBlockBDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText());
            this.createForeignCascade((String)selectBlockBDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), Amount.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), paymentMode);
            selectBlockBDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setValue(null);
            MoveOutDate.setText("");
            LeaseStartDate.setValue(null);
            LeaseEndDate.setText("");
        }else if (checkComboBoxDetails == "BlockC"){
            this.createTenantDetailstable((String) selectBlockCDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), moveInString, MoveOutDate.getText(), leaseStartString, LeaseEndDate.getText());
            this.createTable2((String)selectBlockCDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText());
            this.createForeignCascade((String)selectBlockCDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), Amount.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), paymentMode);
            selectBlockCDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setValue(null);
            MoveOutDate.setText("");
            LeaseStartDate.setValue(null);
            LeaseEndDate.setText("");
        }else if (checkComboBoxDetails == "NasraBlock"){
            this.createTenantDetailstable((String) selectNasraBlockDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), TenantPhoneNumber.getText(), RentAmount.getText(), DueDate.getText(), RentDeposit.getText(), moveInString, MoveOutDate.getText(), leaseStartString, LeaseEndDate.getText());
            this.createTable2((String)selectNasraBlockDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText());
            this.createForeignCascade((String)selectNasraBlockDetails.getSelectionModel().getSelectedItem(), TNameDetails.getText(), Amount.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), paymentMode);
            selectNasraBlockDetails.setValue(null);
            TNameDetails.setText("");
            TenantPhoneNumber.setText("");
            RentAmount.setText("");
            DueDate.setText("");
            RentDeposit.setText("");
            MoveInDate.setValue(null);
            MoveOutDate.setText("");
            LeaseStartDate.setValue(null);
            LeaseEndDate.setText("");
        }
        
    }
    
    @FXML
    private Button submit2;
    @FXML
    private void TableInsertButton4(){
        this.createTable4((String)MonthBox2.getSelectionModel().getSelectedItem(), Expenditure.getText(), ReasonForExpense.getText());
        MonthBox2.setValue(null);
        Expenditure.setText("");
        ReasonForExpense.setText("");
    }
    
    @FXML
    private Button submit1;
    @FXML
    private void TableInsertButton3(){
        this.createTable3((String)MonthBox1.getSelectionModel().getSelectedItem(), WaterBill.getText(), ElectricityBill.getText());
        MonthBox1.setValue(null);
        WaterBill.setText("");
        ElectricityBill.setText("");
    }
    
    @FXML
    private Button Submit1;
    @FXML
    private void TableInsertButton2() {
        LocalDate repairsDateTime = RepairsDate.getValue();
        String repairsDateString = null;
        if (RepairsDate.getValue() == null){
            repairsDateString = null;
        }else if (RepairsDate.getValue() != null){
            repairsDateString = repairsDateTime.format(DateTimeFormatter.ISO_DATE);
        }
        
        if (checkComboBox.equals("BlockA")) {
            this.insertJatomAptDetails((String)SelectHouseBox.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText(), repairsDateString);
            SelectHouseBox.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
            RepairsDate.setValue(null);
        }else if (checkComboBox.equals("BlockB")){
            this.insertJatomAptDetails((String)SelectBlockB.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText(), repairsDateString);
            SelectBlockB.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
            RepairsDate.setValue(null);
        }else if (checkComboBox.equals("BlockC")){
            this.insertJatomAptDetails((String)SelectBlockC.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText(), repairsDateString);
            SelectBlockC.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
            RepairsDate.setValue(null);
        }else if (checkComboBox.equals("NasraBlock")){
            this.insertJatomAptDetails((String)SelectNasraBlock.getSelectionModel().getSelectedItem(), TName.getText(), Repairs.getText(), repairCost.getText(), miscellaneous.getText(), repairsDateString);
            SelectNasraBlock.setValue(null);
            TName.setText("");
            Repairs.setText("");
            repairCost.setText("");
            miscellaneous.setText("");
            RepairsDate.setValue(null);
        }

    }
    
    @FXML
    private Button Submit;
    
    @FXML
    private void TableInsertButton(){
        LocalDate rentDate = RentPaymentDate.getValue();
        String rentPaymentString = null;
        if (RentPaymentDate.getValue() == null){
            rentPaymentString = null;
        }else if (RentPaymentDate.getValue() != null){
            rentPaymentString = rentDate.format(DateTimeFormatter.ISO_DATE);
        }
        if (checkComboBox3 == "BlockA") {
            this.insertJatomApts((String) BlockA3.getSelectionModel().getSelectedItem(), Amount.getText(), Name.getText(), (String) MonthBox.getSelectionModel().getSelectedItem(), rentPaymentString, paymentMode);
            BlockA3.setValue(null);
            Amount.setText("");
            Name.setText("");
            MonthBox.setValue(null);
            RentPaymentDate.setValue(null);
        }else if (checkComboBox3 == "BlockB"){
            this.insertJatomApts((String)BlockB3.getSelectionModel().getSelectedItem(), Amount.getText(), Name.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), rentPaymentString, paymentMode);
            BlockB3.setValue(null);
            Amount.setText("");
            Name.setText("");
            MonthBox.setValue(null);
            RentPaymentDate.setValue(null);
        }else if (checkComboBox3 == "BlockC"){
            this.insertJatomApts((String)BlockC3.getSelectionModel().getSelectedItem(), Amount.getText(), Name.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), rentPaymentString, paymentMode);
            BlockC3.setValue(null);
            Amount.setText("");
            Name.setText("");
            MonthBox.setValue(null);
            RentPaymentDate.setValue(null);
        }else if (checkComboBox3 == "NasraBlock"){
            this.insertJatomApts((String)NasraBlock3.getSelectionModel().getSelectedItem(), Amount.getText(), Name.getText(), (String)MonthBox.getSelectionModel().getSelectedItem(), rentPaymentString, paymentMode);
            NasraBlock3.setValue(null);
            Amount.setText("");
            Name.setText("");
            MonthBox.setValue(null);
            RentPaymentDate.setValue(null);
        }
        
    }
    
    @FXML
    private ComboBox SelectHouseBox;
    @FXML
    private void initializeSelectHouseBox() {
        SelectHouseBox.setItems(HouseNumber);   
    }
    
    @FXML
    private ComboBox SelectBlockB;
    @FXML
    private void initializeSelectBlockB(){
        SelectBlockB.setItems(HouseNumber2);
    }
    
    @FXML
    private ComboBox SelectBlockC;
    @FXML
    private void initializeSelectBlockC(){
        SelectBlockC.setItems(HouseNumber3); 
    }
   
    @FXML
    private ComboBox SelectNasraBlock;
    @FXML
    private void initializeSelectNasraBlock(){
        SelectNasraBlock.setItems(HouseNumber4);
        
    }
    
    @FXML
    private ComboBox selectHouseBoxDetails;
    @FXML
    private void initializeHouseBoxDetails(){
        selectHouseBoxDetails.setItems(HouseNumber);
    }
    
    @FXML
    private ComboBox selectBlockBDetails;
    @FXML
    private void initializeBlockBDetails(){
        selectBlockBDetails.setItems(HouseNumber2);
    }
    
    @FXML
    private ComboBox selectBlockCDetails;
    @FXML
    private void initializeBlockCDetails(){
        selectBlockCDetails.setItems(HouseNumber3);
    }
    
    @FXML
    private ComboBox selectNasraBlockDetails;
    @FXML
    private void initializeNasraBlockDetails(){
        selectNasraBlockDetails.setItems(HouseNumber4);
    }
    
    @FXML
    private ComboBox BlockA3;
    @FXML
    private void initializeHNoBox() {
        BlockA3.setItems(HouseNumber);
    }
    
    
    @FXML
    private ComboBox BlockB3;
    @FXML
    private void initializeBlockB3(){
       BlockB3.setItems(HouseNumber2);
    }
    
    @FXML
    private ComboBox BlockC3;
    @FXML
    private void initializeBlockC3(){
        BlockC3.setItems(HouseNumber3);
    }

    @FXML
    private ComboBox NasraBlock3;
    @FXML
    private void initializeNasraBlock3(){
        NasraBlock3.setItems(HouseNumber4);
    }
    
    @FXML
    private TreeView<String> PaymentTree;
    public String paymentMode = null;
    
    
    @FXML
    private void initiaiizeMonthBox1(){
        MonthBox1.setItems(MonthPaid);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String query = "select * from RecurrentVariableMonthlyExpenditure where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (String)MonthBox1.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                WaterBill.setText(rs.getString("WaterBill"));
                ElectricityBill.setText(rs.getString("ElectricityBill"));
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   
    @FXML
    private void initializeMonthBox2(){
        MonthBox2.setItems(MonthPaid);
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String query = "select * from OtherMonthlyExpenditures where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (String)MonthBox2.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {                
                Expenditure.setText(rs.getString("Expenditure"));
                ReasonForExpense.setText(rs.getString("ReasonForExpense"));
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Button b_AddRepairs;
    
    @FXML
    private Button b_AddRepairCost;
    
    @FXML
    private Button b_AddMiscellaneous;
    
    @FXML
    private Button b_updateWater;
    
    @FXML
    private Button b_updateElectricity;
    
    @FXML
    private Button b_Expenditure;
    
    @FXML
    private Button b_ReasonForExpense;
    
    @FXML
    private void handleRepairButton(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update  = "update JatomAptsDetails set RepairsOnHouse = ? where HNumber = ? and TenantName = ? ";
        try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(update);
                pstmt.setString(1, Repairs.getText());
                pstmt.setString(2, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                pstmt.setString(3, TName.getText());
                pstmt.executeUpdate();
                BlockA3.setValue(null);
                Repairs.setText("");
                int executeUpdate = pstmt.executeUpdate();
                conn.setAutoCommit(true);
                    if (executeUpdate > 0){
                        System.out.println("Updated");
                    }else
                        System.out.println("Not updated");
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    @FXML
    private void handlerepairCostButton(){
        String update  = "update JatomAptsDetails set CostOfRepair = ? where HNumber = ? and TenantName = ?";
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setString(1, repairCost.getText());
                    pstmt.setString(2, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                    pstmt.setString(3, TName.getText());
                    pstmt.executeUpdate();
                    BlockA3.setValue(null);
                    repairCost.setText("");
                    int executeUpdate = pstmt.executeUpdate();
                        if (executeUpdate > 0){
                            System.out.println("Updated");
                        }else
                            System.out.println("Not updated");
                    pstmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    @FXML
    private void handlemiscellaneousButton(){
    String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
    String update = "update JatomAptsDetails set MiscellaneousExpenses = ? where HNumber = ? and TenantName = ?";   
                try {
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement pstmt = conn.prepareStatement(update);
                    pstmt.setString(1, miscellaneous.getText());
                    pstmt.setString(2,(String)SelectHouseBox.getSelectionModel().getSelectedItem() );
                    pstmt.setString(3, TName.getText());
                    int executeUpdate = pstmt.executeUpdate();
                    BlockA3.setValue(null);
                    miscellaneous.setText("");
                    if (executeUpdate > 0){
                        System.out.println("Updated");
                    }else
                        System.out.println("Not updated");
                   pstmt.close();
                   conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    @FXML
    private void updateWater(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update RecurrentVariableMonthlyExpenditure set WaterBill = ? where Month = ? and ElectricityBill = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, WaterBill.getText());
            pstmt.setString(2, (String)MonthBox1.getSelectionModel().getSelectedItem());
            pstmt.setString(3, ElectricityBill.getText());
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0){
                System.out.println("Updated");
            }else
                System.out.println("Not Updated");
            WaterBill.setText("");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateElectricity(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update RecurrentVariableMonthlyExpenditure set ElectricityBill = ? where Month = ? and WaterBill = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, ElectricityBill.getText());
            pstmt.setString(2, (String)MonthBox1.getSelectionModel().getSelectedItem());
            pstmt.setString(3, WaterBill.getText());
            pstmt.executeUpdate();
            ElectricityBill.setText("");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateExpenditure(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update OtherMonthlyExpenditures set Expenditure = ? where Month = ? and ReasonForExpense = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, Expenditure.getText());
            pstmt.setString(2, (String)MonthBox2.getSelectionModel().getSelectedItem());
            pstmt.setString(3, ReasonForExpense.getText());
            pstmt.executeUpdate();
            Expenditure.setText("");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateReasonForExpense(){
        String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
        String update = "update OtherMonthlyExpenditures set ReasonForExpense = ? where Month = ? and Expenditure = ?";
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, ReasonForExpense.getText());
            pstmt.setString(2, (String)MonthBox2.getSelectionModel().getSelectedItem());
            pstmt.setString(3, Expenditure.getText());
            pstmt.executeUpdate();
            ReasonForExpense.setText("");
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }
    
    public ObservableList<RecurrentExpenditure> getData() {
        ObservableList<RecurrentExpenditure> data;
        data = FXCollections.observableArrayList();
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from RecurrentVariableMonthlyExpenditure where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String) MonthBox1.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String M = rs.getString("Month");
                String W = rs.getString("WaterBill");
                String E = rs.getString("ElectricityBill");
                RecurrentExpenditure row = new RecurrentExpenditure(M, W, E);
                data.add(row);
            }
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    @FXML
    private Button viewTableRecurrentExpenditure;
    
    //When this method is called, it passes a recurrent expenditure object to tableview and displays it
    @FXML
    private void tableButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tabView.fxml"));
        TabViewController subcontroller = new TabViewController(this);
        loader.setController(subcontroller);
        Parent root = loader.load();
        Scene tablescene = new Scene(root);
        Stage window = new Stage();
        window.setScene(tablescene);
        window.show();
    }
    
    public ObservableList<OtherExpenditure> getOtherTableData(){
        ObservableList<OtherExpenditure>data;
        data = FXCollections.observableArrayList();
        try {
            String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
            String select = "select * from OtherMonthlyExpenditures where Month = ?";
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setString(1, (String)MonthBox2.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String Mon = rs.getString("Month");
                String Exp = rs.getString("Expenditure");
                String Reas = rs.getString("ReasonForExpense");
                OtherExpenditure other = new OtherExpenditure(Mon, Exp, Reas);
                data.add(other);
            }
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    @FXML
    private Button viewTableOtherExpenditure;
    
    //Method called to pass OtherExpenidture object to tableview and displays it.
    @FXML
    private void otherexpenditureButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("tableOtherExpenditure.fxml"));
        TableOtherExpenditureController controller = new TableOtherExpenditureController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene othertableScene = new Scene(root);
        Stage window  = new Stage();
        window.setScene(othertableScene);
        window.show();
    }
    
    @FXML
    private Button payerDetailsTableButton;
    public ObservableList<PayerDetails> getPayerDetailsData() {
        ObservableList<PayerDetails> data = FXCollections.observableArrayList();
        if (checkComboBox3 == "BlockA") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String query = "select * from TenantDetails where HouseNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, (String) BlockA3.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String HouseNo = rs.getString("HouseNumber");
                    String Paid = rs.getString("Amount");
                    String Payer = rs.getString("PayerName");
                    String Month = rs.getString("Month");
                    String Date = rs.getString("Date");
                    String PaymentMethod = rs.getString("PaymentMode");
                    PayerDetails pay = new PayerDetails(HouseNo, Paid, Payer, Month, Date, PaymentMethod);
                    data.add(pay);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkComboBox3 == "BlockB") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String query = "select * from TenantDetails where HouseNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, (String) BlockB3.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String HouseNo = rs.getString("HouseNumber");
                    String Paid = rs.getString("Amount");
                    String Payer = rs.getString("PayerName");
                    String Month = rs.getString("Month");
                    String Date = rs.getString("Date");
                    String PaymentMethod = rs.getString("PaymentMode");
                    PayerDetails pay = new PayerDetails(HouseNo, Paid, Payer, Month, Date, PaymentMethod);
                    data.add(pay);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkComboBox3 == "BlockC") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String query = "select * from TenantDetails where HouseNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, (String) BlockC3.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String HouseNo = rs.getString("HouseNumber");
                    String Paid = rs.getString("Amount");
                    String Payer = rs.getString("PayerName");
                    String Month = rs.getString("Month");
                    String Date = rs.getString("Date");
                    String PaymentMethod = rs.getString("PaymentMode");
                    PayerDetails pay = new PayerDetails(HouseNo, Paid, Payer, Month, Date, PaymentMethod);
                    data.add(pay);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkComboBox3 == "NasraBlock") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String query = "select * from TenantDetails where HouseNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, (String) NasraBlock3.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String HouseNo = rs.getString("HouseNumber");
                    String Paid = rs.getString("Amount");
                    String Payer = rs.getString("PayerName");
                    String Month = rs.getString("Month");
                    String Date = rs.getString("Date");
                    String PaymentMethod = rs.getString("PaymentMode");
                    PayerDetails pay = new PayerDetails(HouseNo, Paid, Payer, Month, Date, PaymentMethod);
                    data.add(pay);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    
    @FXML
    private void payerDetailsButton()throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PayerDetailsTable.fxml"));
        PayerDetailsTableController controller = new PayerDetailsTableController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene PayerDetailsScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(PayerDetailsScene);
        window.show();
    }
    
    @FXML
    private Button viewHouseTableButton;
    
    String checkComboBox;
    String checkComboBoxDetails;
    String checkComboBox3;
    
    public ObservableList<HouseRepairsModel> getHouseRepairsData() {
        ObservableList<HouseRepairsModel> data;
        data = FXCollections.observableArrayList();
        if (checkComboBox == "BlockA") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectHouseBox.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String H = rs.getString("HNumber");
                    String TN = rs.getString("TenantName");
                    String R = rs.getString("RepairsOnHouse");
                    String RC = rs.getString("CostOfRepair");
                    String M = rs.getString("MiscellaneousExpenses");
                    String D = rs.getString("Date");
                    HouseRepairsModel rm = new HouseRepairsModel(H, TN, R, RC, M, D);
                    data.add(rm);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkComboBox == "BlockB") {
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectBlockB.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String H = rs.getString("HNumber");
                    String TN = rs.getString("TenantName");
                    String R = rs.getString("RepairsOnHouse");
                    String RC = rs.getString("CostOfRepair");
                    String M = rs.getString("MiscellaneousExpenses");
                    String D = rs.getString("Date");
                    HouseRepairsModel rm = new HouseRepairsModel(H, TN, R, RC, M, D);
                    data.add(rm);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (checkComboBox == "BlockC"){
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectBlockC.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String H = rs.getString("HNumber");
                    String TN = rs.getString("TenantName");
                    String R = rs.getString("RepairsOnHouse");
                    String RC = rs.getString("CostOfRepair");
                    String M = rs.getString("MiscellaneousExpenses");
                    String D = rs.getString("Date");
                    HouseRepairsModel rm = new HouseRepairsModel(H, TN, R, RC, M, D);
                    data.add(rm);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (checkComboBox == "NasraBlock"){
            try {
                String url = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                String select = "select * from JatomAptsDetails where HNumber = ?";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(select);
                pstmt.setString(1, (String) SelectNasraBlock.getSelectionModel().getSelectedItem());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String H = rs.getString("HNumber");
                    String TN = rs.getString("TenantName");
                    String R = rs.getString("RepairsOnHouse");
                    String RC = rs.getString("CostOfRepair");
                    String M = rs.getString("MiscellaneousExpenses");
                    String D = rs.getString("Date");
                    HouseRepairsModel rm = new HouseRepairsModel(H, TN, R, RC, M, D);
                    data.add(rm);
                }
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    
    @FXML
    private void houseRepairTableButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HouseRepairsTable.fxml"));
        HouseRepairsTableController controller = new HouseRepairsTableController(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene repairtableScene = new Scene(root);
        Stage window = new Stage();
        window.setScene(repairtableScene);
        window.show();
    }
    
    private void handleTreeItem(Object newValue){
        System.out.println(newValue);
    }
    
    
    
    
         
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        h_HouseDetails.prefWidthProperty().bind(motherPane.widthProperty());
        h_PaymentDetails.prefWidthProperty().bind(motherPane.widthProperty());
        h_MonthlyExpenditure.prefWidthProperty().bind(motherPane.widthProperty());
        h_TenantDetails.prefWidthProperty().bind(motherPane.widthProperty());
        h_HouseDetails.prefHeightProperty().bind(motherPane.heightProperty());
        h_PaymentDetails.prefHeightProperty().bind(motherPane.heightProperty());
        h_MonthlyExpenditure.prefHeightProperty().bind(motherPane.heightProperty());
        h_TenantDetails.prefHeightProperty().bind(motherPane.heightProperty());
       
        
        
        //Handling mouse clicked event on titledpane
        HouseBlocks.setOnMouseClicked((MouseEvent e) -> {
            SelectHouseBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectHouseBox.getSelectionModel().getSelectedItem());
                try {
                    String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String query = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(ur);
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(select);
                    pstmt1.setString(1, (String)SelectHouseBox.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                        Object repairsDateCheck = rs.getObject("Date");
                        if (repairsDateCheck == null){
                            RepairsDate.setValue(null);
                        }else if(repairsDateCheck != null){
                            RepairsDate.setValue(LocalDate.parse(rs.getString("Date"), DateTimeFormatter.ISO_DATE));
                        }
                    }
                    if (!rs1.next()){
                        TName.setText("");
                        Repairs.setText("");
                        repairCost.setText("");
                        miscellaneous.setText("");
                    }
                    HouseBlocks.setGraphic(label);
                    pstmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                checkComboBox = "BlockA";
                HouseBlocks.setExpanded(false);
                Platform.runLater(() -> {
                        
                });
            });
            SelectBlockB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectBlockB.getSelectionModel().getSelectedItem());
                try {
                    String url1 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(url1);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) SelectBlockB.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "select * from JatomAptsDetails where HNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)SelectBlockB.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                        Object repairsDateCheck = rs.getObject("Date");
                        if (repairsDateCheck == null){
                            RepairsDate.setValue(null);
                        }else if(repairsDateCheck != null){
                            RepairsDate.setValue(LocalDate.parse(rs.getString("Date"), DateTimeFormatter.ISO_DATE));
                        }
                    }
                    if (!rs1.next()) {
                        TName.setText("");
                        Repairs.setText("");
                        repairCost.setText("");
                        miscellaneous.setText("");
                    }
                    HouseBlocks.setGraphic(label);
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox = "BlockB";
                HouseBlocks.setExpanded(false);
            });
            SelectBlockC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectBlockC.getSelectionModel().getSelectedItem());
                try {
                    String url2 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(url2);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) SelectBlockC.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "select * from JatomAptsDetails where HNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)SelectBlockC.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();

                    while (rs.next()) {
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                        Object repairsDateCheck = rs.getObject("Date");
                        if (repairsDateCheck == null){
                            RepairsDate.setValue(null);
                        }else if(repairsDateCheck != null){
                            RepairsDate.setValue(LocalDate.parse(rs.getString("Date"), DateTimeFormatter.ISO_DATE));
                        }
                    }
                    if (!rs1.next()) {
                        TName.setText("");
                        Repairs.setText("");
                        repairCost.setText("");
                        miscellaneous.setText("");
                    }
                    HouseBlocks.setGraphic(label);
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox = "BlockC";
               HouseBlocks.setExpanded(false);
            });
            SelectNasraBlock.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) SelectNasraBlock.getSelectionModel().getSelectedItem());
                try {
                    String url3 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "select * from JatomAptsDetails where HNumber = ?";
                    Connection conn = DriverManager.getConnection(url3);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) SelectNasraBlock.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "select * from JatomAptsDetails where HNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)SelectNasraBlock.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();
                    
                    while (rs.next()) {                        
                        TName.setText(rs.getString("TenantName"));
                        Repairs.setText(rs.getString("RepairsOnHouse"));
                        repairCost.setText(rs.getString("CostOfRepair"));
                        miscellaneous.setText(rs.getString("MiscellaneousExpenses"));
                        Object repairsDateCheck = rs.getObject("Date");
                        if (repairsDateCheck == null){
                            RepairsDate.setValue(null);
                        }else if(repairsDateCheck != null){
                            RepairsDate.setValue(LocalDate.parse(rs.getString("Date"), DateTimeFormatter.ISO_DATE));
                        }
                    }
                    if (!rs1.next()) {
                        TName.setText("");
                        Repairs.setText("");
                        repairCost.setText("");
                        miscellaneous.setText("");
                    }
                    HouseBlocks.setGraphic(label);
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox = "NasraBlock";
                HouseBlocks.setExpanded(false);
            });
            
        });
        
        //Handling HouseBlocks2 mouse clicked event
        HouseBlocks2.setOnMouseClicked((MouseEvent e) -> {
            ObservableList<String> data1 = FXCollections.observableArrayList();
            
            selectHouseBoxDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String) selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                try {
                    String url4 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url4);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    String query = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt.setString(1, (String) selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                    pstmt1.setString(1, (String) selectHouseBoxDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        Object moveInCheck = rs.getObject("MoveInDate");
                        if (moveInCheck == null){
                            MoveInDate.setValue(null);
                        }else {
                            MoveInDate.setValue(LocalDate.parse(rs.getString("MoveInDate"), DateTimeFormatter.ISO_DATE));
                        }
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        Object leaseStartCheck = rs.getObject("LeaseStartDate");
                        if (leaseStartCheck == null){
                            LeaseStartDate.setValue(null);
                        }else {
                            LeaseStartDate.setValue(LocalDate.parse(rs.getString("LeaseStartDate"), DateTimeFormatter.ISO_DATE));
                        }
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));
                        data1.addAll(rs.getString("TenantName"), rs.getString("TenantPhoneNumber"), rs.getString("RentAmount"), rs.getString("DueDate"), rs.getString("Deposit"), rs.getString("MoveInDate"), rs.getString("MoveOutDate"), rs.getString("MoveOutDate"), rs.getString("LeaseStartDate"), rs.getString("LeaseEndDate"));
                    }
                    if (rs1.next()) {
                        HouseOccupied.setVisible(true);
                        HouseVacant.setVisible(false);
                    } else if (!rs1.next()) {
                        HouseVacant.setVisible(true);
                        HouseOccupied.setVisible(false);
                        TNameDetails.setText("");
                        TenantPhoneNumber.setText("");
                        RentAmount.setText("");
                        DueDate.setText("");
                        RentDeposit.setText("");
                        MoveInDate.setValue(null);
                        MoveOutDate.setText("");
                        LeaseStartDate.setValue(null);
                        LeaseEndDate.setText(""); 
                    }
                    pstmt.close();
                    pstmt1.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBoxDetails = "BlockA";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            
            selectBlockBDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                try {
                    String url5 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url5);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)selectBlockBDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {                        
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        Object moveInCheck = rs.getObject("MoveInDate");
                        if (moveInCheck == null){
                            MoveInDate.setValue(null);
                        }else {
                            MoveInDate.setValue(LocalDate.parse(rs.getString("MoveInDate"), DateTimeFormatter.ISO_DATE));
                        }
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        Object leaseStartCheck = rs.getObject("LeaseStartDate");
                        if (leaseStartCheck == null){
                            LeaseStartDate.setValue(null);
                        }else {
                            LeaseStartDate.setValue(LocalDate.parse(rs.getString("LeaseStartDate"), DateTimeFormatter.ISO_DATE));
                        }
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));    
                    }
                    if (rs1.next()){
                        HouseOccupied.setVisible(true);
                        HouseVacant.setVisible(false);
                    }else if (!rs1.next()){
                        HouseVacant.setVisible(true);
                        HouseOccupied.setVisible(false);
                        TNameDetails.setText("");
                        TenantPhoneNumber.setText("");
                        RentAmount.setText("");
                        DueDate.setText("");
                        RentDeposit.setText("");
                        MoveInDate.setValue(null);
                        MoveOutDate.setText("");
                        LeaseStartDate.setValue(null);
                        LeaseEndDate.setText("");
                    }
                    pstmt.close();
                    pstmt1.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace(); 
                }
                checkComboBoxDetails = "BlockB";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            selectBlockCDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                try {
                    String url5 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url5);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)selectBlockCDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {                        
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        Object moveInCheck = rs.getObject("MoveInDate");
                        if (moveInCheck == null){
                            MoveInDate.setValue(null);
                        }else {
                            MoveInDate.setValue(LocalDate.parse(rs.getString("MoveInDate"), DateTimeFormatter.ISO_DATE));
                        }
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        Object leaseStartCheck = rs.getObject("LeaseStartDate");
                        if (leaseStartCheck == null){
                            LeaseStartDate.setValue(null);
                        }else {
                            LeaseStartDate.setValue(LocalDate.parse(rs.getString("LeaseStartDate"), DateTimeFormatter.ISO_DATE));
                        }
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));
                    }
                    if (rs1.next()){
                        HouseOccupied.setVisible(true);
                        HouseVacant.setVisible(false);
                    }else if (!rs.next()){
                        HouseVacant.setVisible(true);
                        HouseOccupied.setVisible(false);
                        TNameDetails.setText("");
                        TenantPhoneNumber.setText("");
                        RentAmount.setText("");
                        DueDate.setText("");
                        RentDeposit.setText("");
                        MoveInDate.setValue(null);
                        MoveOutDate.setText("");
                        LeaseStartDate.setValue(null);
                        LeaseEndDate.setText("");
                    }
                    pstmt.close();
                    pstmt1.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBoxDetails = "BlockC";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            selectNasraBlockDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                Label label = new Label();
                label.setText((String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                try {
                    String url6 = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(url6);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "SELECT * FROM JatomTenantDetails where HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)selectNasraBlockDetails.getSelectionModel().getSelectedItem());
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {                        
                        TNameDetails.setText(rs.getString("TenantName"));
                        TenantPhoneNumber.setText(rs.getString("TenantPhoneNumber"));
                        RentAmount.setText(rs.getString("RentAmount"));
                        DueDate.setText(rs.getString("DueDate"));
                        RentDeposit.setText(rs.getString("Deposit"));
                        Object moveInCheck = rs.getObject("MoveInDate");
                        if (moveInCheck == null){
                            MoveInDate.setValue(null);
                        }else {
                            MoveInDate.setValue(LocalDate.parse(rs.getString("MoveInDate"), DateTimeFormatter.ISO_DATE));
                        }
                        MoveOutDate.setText(rs.getString("MoveOutDate"));
                        Object leaseStartCheck = rs.getObject("LeaseStartDate");
                        if (leaseStartCheck == null){
                            LeaseStartDate.setValue(null);
                        }else {
                            LeaseStartDate.setValue(LocalDate.parse(rs.getString("LeaseStartDate"), DateTimeFormatter.ISO_DATE));
                        }
                        LeaseEndDate.setText(rs.getString("LeaseEndDate"));
                    }
                    if (rs1.next()){
                        HouseOccupied.setVisible(true);
                        HouseVacant.setVisible(false);
                    }else if (!rs1.next()){
                        HouseVacant.setVisible(true);
                        HouseOccupied.setVisible(false);
                        TNameDetails.setText("");
                        TenantPhoneNumber.setText("");
                        RentAmount.setText("");
                        DueDate.setText("");
                        RentDeposit.setText("");
                        MoveInDate.setValue(null);
                        MoveOutDate.setText("");
                        LeaseStartDate.setValue(null);
                        LeaseEndDate.setText("");
                    }
                    pstmt.close();
                    pstmt1.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBoxDetails = "NasraBlock";
                HouseBlocks2.setGraphic(label);
                HouseBlocks2.setExpanded(false);
            });
            
        });
        
        TreeItem<String> pseudoroot = new TreeItem<>();
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> root3 = new TreeItem<>("Cash");
        TreeItem<String> cash = new TreeItem<>("Cash recieved by:");
        root3.getChildren().add(cash);
        TreeItem<String> root1 = new TreeItem<>("Mpesa");
        TreeItem<String> mpesa = new TreeItem<>("Enter mpesa transaction code");
        root1.getChildren().add(mpesa);
        TreeItem<String> root2 = new TreeItem<>("Banker's Cheque");
        TreeItem<String> bank = new TreeItem<>("Enter cheque no.");
        root2.getChildren().add(bank);
        root.setExpanded(true);
        root.getChildren().addAll(root3,root1,root2);
        
        String cashreceived = "Cash recieved by:";
        String mpesaCode = "Enter mpesa transaction code";
        String bankCheque = "Enter cheque no.";
        
        //Used to circumvent lambda expressions' requirement of final variable
        AtomicReference<String> payMethodCheck;
        payMethodCheck = new AtomicReference<>();
        
        HouseBlocks3.setOnMouseClicked((MouseEvent e) ->{           
            BlockA3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {     
                String dbPayString = null;
                Label label = new Label();
                label.setText((String)BlockA3.getSelectionModel().getSelectedItem());
                try {
                    String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(ur);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String) BlockA3.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)BlockA3.getSelectionModel().getSelectedItem());                   
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {
                        Amount.setText(rs.getString("Amount"));
                        Name.setText(rs.getString("PayerName"));
                        MonthBox.setValue(rs.getString("Month"));
                        Object rentPayment = rs.getObject("Date");
                        if (rentPayment == null){
                            RentPaymentDate.setValue(null);
                        }else {
                            RentPaymentDate.setValue(LocalDate.parse(rs.getString("Date")));
                        }
                        dbPayString = rs.getString("PaymentMode");
                    }
                    
                    System.out.println(dbPayString);
                    if (dbPayString == null) {
                        cash.setValue("Cash recieved by:");
                        mpesa.setValue("Enter mpesa transaction code");
                        bank.setValue("Enter cheque no.");
                    } else {
                        String[] payString = dbPayString.split(":");
                        System.out.println(payString[0]);
                        if (payString[0].equals("Cash payment received by")) {
                            cash.setValue(dbPayString);
                            if (!root3.isExpanded()){
                                root3.setExpanded(true);
                            }
                        }else if (payString[0].equals("Mpesa transaction code is")){
                            mpesa.setValue(dbPayString);
                            if (!root1.isExpanded()){
                                root1.setExpanded(true);
                            }   
                        }else if (payString[0].equals("Banker's cheque no")){
                            bank.setValue(dbPayString);
                            if (!root2.isExpanded()){
                                root2.setExpanded(true);
                            }
                        }
                    }
                    if (!rs1.next()) {
                        Amount.setText("");
                        Name.setText("");
                        MonthBox.setValue(null);
                        RentPaymentDate.setValue(null);
                    }
                    pstmt.close();
                    pstmt1.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox3 = "BlockA";
                HouseBlocks3.setGraphic(label);
                HouseBlocks3.setExpanded(false);
            });
            BlockB3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                String dbPayString = null;
                Label label = new Label();
                label.setText((String)BlockB3.getSelectionModel().getSelectedItem());
                try {
                    String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(ur);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)BlockB3.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)BlockB3.getSelectionModel().getSelectedItem());                   
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {
                        Amount.setText(rs.getString("Amount"));
                        Name.setText(rs.getString("PayerName"));
                        MonthBox.setValue(rs.getString("Month"));
                        Object rentPayment = rs.getObject("Date");
                        if (rentPayment == null){
                            RentPaymentDate.setValue(null);
                        }else {
                            RentPaymentDate.setValue(LocalDate.parse(rs.getString("Date")));
                        }
                        dbPayString = rs.getString("PaymentMode");
                    }
                    if (dbPayString == null) {
                        cash.setValue("Cash recieved by:");
                        mpesa.setValue("Enter mpesa transaction code");
                        bank.setValue("Enter cheque no.");
                    } else {
                        String[] payString = dbPayString.split(":");
                        System.out.println(payString[0]);
                        if (payString[0].equals("Cash payment received by")) {
                            cash.setValue(dbPayString);
                            root3.setExpanded(true);
                        }else if (payString[0].equals("Mpesa transaction code is")){
                            mpesa.setValue(dbPayString);
                            root1.setExpanded(true);
                        }else if (payString[0].equals("Banker's cheque no")){
                            bank.setValue(dbPayString);
                            root2.setExpanded(true);
                        }
                    }
                    if (!rs1.next()){
                        Amount.setText("");
                        Name.setText("");
                        MonthBox.setValue(null);
                        RentPaymentDate.setValue(null);
                    }
                    pstmt1.close();
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox3 = "BlockB";
                HouseBlocks3.setGraphic(label);
                HouseBlocks3.setExpanded(false);
            });
            BlockC3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                String dbPayString = null;
                Label label = new Label();
                label.setText((String)BlockC3.getSelectionModel().getSelectedItem());
                try {
                    String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                    String select = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                    Connection conn = DriverManager.getConnection(ur);
                    PreparedStatement pstmt = conn.prepareStatement(select);
                    pstmt.setString(1, (String)BlockC3.getSelectionModel().getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
                    String query = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);
                    pstmt1.setString(1, (String)BlockC3.getSelectionModel().getSelectedItem());                   
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs.next()) {
                        Amount.setText(rs.getString("Amount"));
                        Name.setText(rs.getString("PayerName"));
                        MonthBox.setValue(rs.getString("Month"));
                        Object rentPayment = rs.getObject("Date");
                        if (rentPayment == null){
                            RentPaymentDate.setValue(null);
                        }else {
                            RentPaymentDate.setValue(LocalDate.parse(rs.getString("Date")));
                        }
                        dbPayString = rs.getString("PaymentMode");
                    }
                    if (dbPayString == null) {
                        cash.setValue("Cash recieved by:");
                        mpesa.setValue("Enter mpesa transaction code");
                        bank.setValue("Enter cheque no.");
                    } else {
                        String[] payString = dbPayString.split(":");
                        //Remove later. Trying to create stream
                        Stream stream = Arrays.asList(payString).stream();
                        System.out.println(payString[0]);
                        if (payString[0].equals("Cash payment received by")) {
                            cash.setValue(dbPayString);
                        }else if (payString[0].equals("Mpesa transaction code is")){
                            mpesa.setValue(dbPayString);
                        }else if (payString[0].equals("Banker's cheque no")){
                            bank.setValue(dbPayString);
                        }
                    }
                    if (!rs1.next()) {
                        Amount.setText("");
                        Name.setText("");
                        MonthBox.setValue(null);
                        RentPaymentDate.setValue(null);
                    }
                    pstmt1.close();
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox3 = "BlockC";
                HouseBlocks3.setGraphic(label);
                HouseBlocks3.setExpanded(false);
            });
            NasraBlock3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                String dbPayString = null;
                Label label = new Label();
                label.setText((String)NasraBlock3.getSelectionModel().getSelectedItem());
                try {
                   String ur = "jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\ResidentialRentalManagementSoftware.sqlite";
                   String select = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                   Connection conn = DriverManager.getConnection(ur);
                   PreparedStatement pstmt = conn.prepareStatement(select);
                   pstmt.setString(1, (String)NasraBlock3.getSelectionModel().getSelectedItem());
                   ResultSet rs = pstmt.executeQuery();
                   String query = "SELECT * FROM TenantDetails WHERE HouseNumber = ?";
                   PreparedStatement pstmt1 = conn.prepareStatement(query);
                   pstmt1.setString(1, (String) NasraBlock3.getSelectionModel().getSelectedItem());
                   ResultSet rs1 = pstmt1.executeQuery(); 
                    while (rs.next()) {                        
                        Amount.setText(rs.getString("Amount"));
                        Name.setText(rs.getString("PayerName"));
                        MonthBox.setValue(rs.getString("Month"));
                        Object rentPayment = rs.getObject("Date");
                        if (rentPayment == null){
                            RentPaymentDate.setValue(null);
                        }else {
                            RentPaymentDate.setValue(LocalDate.parse(rs.getString("Date")));
                        }
                        dbPayString = rs.getString("PaymentMode");
                    }
                    if (dbPayString == null) {
                        cash.setValue("Cash recieved by:");
                        mpesa.setValue("Enter mpesa transaction code");
                        bank.setValue("Enter cheque no.");
                    } else {
                        String[] payString = dbPayString.split(":");
                        System.out.println(payString[0]);
                        if (payString[0].equals("Cash payment received by")) {
                            cash.setValue(dbPayString);
                        }else if (payString[0].equals("Mpesa transaction code is")){
                            mpesa.setValue(dbPayString);
                        }else if (payString[0].equals("Banker's cheque no")){
                            bank.setValue(dbPayString);
                        }
                    }
                    
                    if (!rs1.next()){
                        Amount.setText("");
                        Name.setText("");
                        MonthBox.setValue(null);
                        RentPaymentDate.setValue(null);
                    }
                    pstmt1.close();
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                checkComboBox3 = "NasraBlock";
                HouseBlocks3.setGraphic(label);
                HouseBlocks3.setExpanded(false);
            });
            
        });
        
        h_TenantDetails.setOnMouseClicked((Event e) -> {
            if (HouseBlocks2.isExpanded()){
                HouseBlocks2.setExpanded(false);
            }
        });
        
        h_HouseDetails.setOnMouseClicked((Event e) -> {
            if (HouseBlocks.isExpanded()){
                HouseBlocks.setExpanded(false);
            }
        });
        topbar.setOnMouseClicked((Event e) ->{
            if (HouseBlocks.isExpanded()){
                HouseBlocks.setExpanded(false);
            }
            if (HouseBlocks2.isExpanded()){
                HouseBlocks2.setExpanded(false);
            }
            if (HouseBlocks3.isExpanded()){
                HouseBlocks3.setExpanded(false);
            }
        });
        
        h_PaymentDetails.setOnMouseClicked((Event e) -> {
            if (HouseBlocks3.isExpanded()){
                HouseBlocks3.setExpanded(false);
            }
        });
        
        Name.setEditable(false);
        TName.setEditable(false);
        HouseBlocks.setExpanded(false);
        HouseBlocks.setAnimated(true);
        HouseBlocks2.setExpanded(false);
        HouseBlocks2.setAnimated(true);
        HouseBlocks3.setExpanded(false);
        HouseBlocks3.setAnimated(true);
        HouseOccupied.setVisible(false);
        HouseVacant.setVisible(false);
        
        
        PaymentTree.setOnEditStart((event) -> {
            System.out.println("Start of edit");
        });
        PaymentTree.setOnEditCommit((event) -> {    
            if (PaymentTree.editingItemProperty().getValue().equals(cash)){
                paymentMode = "Cash payment received by: "+event.getNewValue();
                System.out.println(paymentMode);
            }else if (PaymentTree.editingItemProperty().getValue().equals(mpesa)){
                paymentMode = "Mpesa transaction code is: "+event.getNewValue();
                System.out.println(paymentMode);
            }else if (PaymentTree.editingItemProperty().getValue().equals(bank)){
                paymentMode = "Banker's cheque no: "+event.getNewValue();
                System.out.println(paymentMode);
            }
        });
        
        root3.expandedProperty().addListener((observable, oldValue, newValue) -> {
           if (root3.isExpanded()){
               root1.setExpanded(false);
               root2.setExpanded(false);
           }
        });
        root2.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (root2.isExpanded()){
                root3.setExpanded(false);
                root1.setExpanded(false);
            }
        });
        root1.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (root1.isExpanded()){
                root2.setExpanded(false);
                root3.setExpanded(false);
            }
        });
        
        PaymentTree.setEditable(true);
        PaymentTree.setCellFactory(TextFieldTreeCell.forTreeView());
        PaymentTree.setRoot(root);
        PaymentTree.setShowRoot(false);     
        
        paymentData.setOnMouseClicked((event) -> {
            h_PaymentDetails.setVisible(true);
            h_MonthlyExpenditure.setVisible(false);
            h_HouseDetails.setVisible(false);
            h_TenantDetails.setVisible(false);
            paymentData.setTextFill(Paint.valueOf("#5FF8FF"));
            monthlyExpenseData.setTextFill(Paint.valueOf("#fdfdfd"));
            tenantData.setTextFill(Paint.valueOf("#fdfdfd"));
            repairsData.setTextFill(Paint.valueOf("#fdfdfd"));
        });
        monthlyExpenseData.setOnMouseClicked((event) -> {
            h_MonthlyExpenditure.setVisible(true);
            h_HouseDetails.setVisible(false);
            h_TenantDetails.setVisible(false);
            h_PaymentDetails.setVisible(false);
            monthlyExpenseData.setTextFill(Paint.valueOf("#5FF8FF"));
            paymentData.setTextFill(Paint.valueOf("#fdfdfd"));
            repairsData.setTextFill(Paint.valueOf("#fdfdfd"));
            tenantData.setTextFill(Paint.valueOf("#fdfdfd"));
        });
        tenantData.setOnMouseClicked((event) -> {
            h_TenantDetails.setVisible(true);
            h_MonthlyExpenditure.setVisible(false);
            h_HouseDetails.setVisible(false);
            h_PaymentDetails.setVisible(false);
            tenantData.setTextFill(Paint.valueOf("#5FF8FF"));
            monthlyExpenseData.setTextFill(Paint.valueOf("#fdfdfd"));
            paymentData.setTextFill(Paint.valueOf("#fdfdfd"));
            repairsData.setTextFill(Paint.valueOf("#fdfdfd"));
        });
        repairsData.setOnMouseClicked((event) -> {
            h_HouseDetails.setVisible(true);
            h_TenantDetails.setVisible(false);
            h_MonthlyExpenditure.setVisible(false);
            h_PaymentDetails.setVisible(false);
            repairsData.setTextFill(Paint.valueOf("#5FF8FF"));
            monthlyExpenseData.setTextFill(Paint.valueOf("#fdfdfd"));
            paymentData.setTextFill(Paint.valueOf("#fdfdfd"));
            tenantData.setTextFill(Paint.valueOf("#fdfdfd"));
        });
        
    }
    
}
