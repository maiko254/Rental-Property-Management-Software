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
public class TableOtherExpenditureController implements Initializable {

    
    @FXML
    public TableView<OtherExpenditure>tableOtherExpenditure;
    @FXML
    public TableColumn<OtherExpenditure, String> monthCol;
    @FXML
    public TableColumn<OtherExpenditure, String>expenditureCol;
    @FXML
    public TableColumn<OtherExpenditure, String>reasonCol;
    
    private  FXMLDocumentController controller = new FXMLDocumentController();
    
    public TableOtherExpenditureController(FXMLDocumentController maincontroller){
        this.controller = maincontroller;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthCol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        expenditureCol.setCellValueFactory(cellData -> cellData.getValue().expenditureProperty());
        reasonCol.setCellValueFactory(cellData -> cellData.getValue().expenditureReasonProperty());
        
        tableOtherExpenditure.setItems(controller.getOtherTableData());
    }    
    
}
