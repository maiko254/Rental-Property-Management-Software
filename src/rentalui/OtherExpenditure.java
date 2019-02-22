/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Creating other expenditure model class
 * @author Mike
 */
public class OtherExpenditure {
    public StringProperty Month;
    public StringProperty Expenditure;
    public StringProperty ExpenditureReason;
    
    //Create default constructor
    public OtherExpenditure(String MonthOther, String ExpenditureOther, String ReasonOther){
        this.Month = new SimpleStringProperty(MonthOther);
        this.Expenditure = new SimpleStringProperty(ExpenditureOther);
        this.ExpenditureReason = new SimpleStringProperty(ReasonOther);
    }
    
    public String getMonth(){
        return Month.get();
    }
    
    public void setMonth(String value){
        Month.set(value);
    }
    
    public StringProperty monthProperty(){
        return Month;
    }
    
    public String getExpenditure(){
        return Expenditure.get();
    }
    
    public void setExpenditure(String value){
        Expenditure.set(value);
    }
    
    public StringProperty expenditureProperty(){
        return Expenditure;
    }
    
    public String getExpenditureReason(){
        return ExpenditureReason.get();
    }
    
    public void setExpenditureReason(String vslue){
        ExpenditureReason.set(vslue);
    }
    
    public StringProperty expenditureReasonProperty(){
        return ExpenditureReason;
    }
}
