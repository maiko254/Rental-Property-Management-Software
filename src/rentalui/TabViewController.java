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
 * FXML Controller class
 *
 * @author Mike
 */
public class TabViewController implements Initializable {
    
    @FXML
    public TableView<RecurrentExpenditure> tableView;
    @FXML
    public TableColumn<RecurrentExpenditure, String> monthCol;
    @FXML
    public TableColumn<RecurrentExpenditure, String> waterCol;
    @FXML
    public TableColumn<RecurrentExpenditure,String> electricityCol;
    
    private final FXMLDocumentController controller;
    /**
     * Passing reference to FXMLDocumentController into TabViewController to
     * allow invoking getData() method from FXMLDocumentController.
     * @param mainController 
     */
   
    public TabViewController(FXMLDocumentController mainController){
        this.controller = mainController;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthCol.setCellValueFactory(cellData -> cellData.getValue().monthchooseProperty());
        waterCol.setCellValueFactory(cellData -> cellData.getValue().waterProperty());
        electricityCol.setCellValueFactory(cellData -> cellData.getValue().electricityProperty());
        
        tableView.setItems(controller.getData());
    }    
    
}
