/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

/**
 *PayerDetailsTable FXML Controller class
 *
 * @author Mike
 */
public class PayerDetailsTableController extends HBox implements Initializable {
    
    @FXML
    public TableView<PayerDetails>payerDetailsTable;
    @FXML
    public TableColumn<PayerDetails, String>houseNoCol;
    @FXML
    public TableColumn<PayerDetails, String>paidCol;
    @FXML
    public TableColumn<PayerDetails, String>payerNameCol;
    @FXML
    public TableColumn<PayerDetails, String>rentMonthCol;
    @FXML
    public TableColumn<PayerDetails, String>rentDateCol;
    @FXML
    public TableColumn<PayerDetails, String>paymentMethodCol;
    
    private final FXMLDocumentController controller;
    
    
    /**
     * Passing reference to FXMLDOcumentController into PayerDetailsTableController
     * to allow invoking getPayerDetailsData() method.
     */
    public PayerDetailsTableController(FXMLDocumentController mainController){
        this.controller = mainController;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        houseNoCol.setCellValueFactory(cellData -> cellData.getValue().tenantHouseProperty());
        paidCol.setCellValueFactory(cellData -> cellData.getValue().paidAmountProperty());
        payerNameCol.setCellValueFactory(cellData -> cellData.getValue().payerNameProperty());
        rentMonthCol.setCellValueFactory(cellData -> cellData.getValue().monthColProperty());
        rentDateCol.setCellValueFactory(cellData -> cellData.getValue().dateColProperty());
        paymentMethodCol.setCellValueFactory(cellData -> cellData.getValue().paymentMethodProperty());
        
        payerDetailsTable.setItems(controller.getPayerDetailsData());
    }    
    
}
