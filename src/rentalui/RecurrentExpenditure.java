/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Creating recurrent expenditure model class
 * @author Mike
 */
public class RecurrentExpenditure {

    public StringProperty MonthChoose;
    public StringProperty Water1;
    public StringProperty Electricity1;

    //Default constructor
    public RecurrentExpenditure(String MChoose, String Water, String Electricity) {
        this.MonthChoose = new SimpleStringProperty(MChoose);
        this.Water1 = new SimpleStringProperty(Water);
        this.Electricity1 = new SimpleStringProperty(Electricity);
    }

    //Getters and Setters
    public String getMonthChoose() {
        return MonthChoose.get();
    }
    
    public void setMonthChoose(String value) {
        MonthChoose.set(value);
    }

    public String getWater() {
        return Water1.get();
    }

    public void setWater(String value) {
        Water1.set(value);
    }

    public String getElectricity() {
        return Electricity1.get();
    }

    public void setElectricity(String value) {
        Electricity1.set(value);
    }

    //Property Values
    public StringProperty monthchooseProperty() {
        return MonthChoose;
    }

    public StringProperty waterProperty() {
        return Water1;
    }

    public StringProperty electricityProperty() {
        return Electricity1;
    }
}
