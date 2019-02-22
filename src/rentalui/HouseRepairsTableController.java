/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * HouseRepairsTable FXML Controller class
 *
 * @author Mike
 */
public class HouseRepairsTableController implements Initializable {
    @FXML
    private TableView<HouseRepairsModel>houseTable;
    @FXML
    private TableColumn<HouseRepairsModel, String>houseCol;
    @FXML
    private TableColumn<HouseRepairsModel, String>tenantCol;
    @FXML
    private TableColumn<HouseRepairsModel, String>repairsCol;
    @FXML
    private TableColumn<HouseRepairsModel, String>repairCostCol;
    @FXML
    private TableColumn<HouseRepairsModel, String>miscCost;
    @FXML 
    private TableColumn<HouseRepairsModel, String>dateCol;
    
    private final FXMLDocumentController controller;
    
    public HouseRepairsTableController(FXMLDocumentController maincontroller){
        this.controller = maincontroller;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        houseCol.setCellValueFactory(cellData -> cellData.getValue().houseNumberProperty());
        tenantCol.setCellValueFactory(cellData -> cellData.getValue().tenantNameProperty());
        repairsCol.setCellValueFactory(cellData -> cellData.getValue().repairsProperty());
        repairCostCol.setCellValueFactory(cellData -> cellData.getValue().repairCostProperty());
        miscCost.setCellValueFactory(cellData -> cellData.getValue().miscProperty());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        
        houseTable.setItems(controller.getHouseRepairsData());
    }    
    
}
