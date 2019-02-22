/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Mike
 */
public class HouseRepairsModel {
    public StringProperty HouseNumber;
    public StringProperty TenantName;
    public StringProperty Repairs;
    public StringProperty RepairCost;
    public StringProperty Misc;
    public StringProperty Date;
    
    public HouseRepairsModel(String HouseNo, String TN, String R, String RC, String Miscellaneous, String D){
        this.HouseNumber = new SimpleStringProperty(HouseNo);
        this.TenantName = new SimpleStringProperty(TN);
        this.Repairs = new SimpleStringProperty(R);
        this.RepairCost = new SimpleStringProperty(RC);
        this.Misc = new SimpleStringProperty(Miscellaneous);
        this.Date = new SimpleStringProperty(D);
    }
    
    public String getHouseNumber(){
        return HouseNumber.get();
    }
    
    public void setHouseNumber(String value){
        HouseNumber.set(value);
    }
    
    public StringProperty houseNumberProperty(){
        return HouseNumber;
    }
    
    public String getTenantName(){
        return TenantName.get();
    }
    
    public void setTenantname(String value){
        TenantName.set(value);
    }
    
    public StringProperty tenantNameProperty(){
        return TenantName;
    }
    
    public String getRepairs(){
        return Repairs.get();
    }
    
    public void setRepairs(String value){
        Repairs.set(value);
    }
    
    public StringProperty repairsProperty(){
        return Repairs;
    }
    
    public String getRepairsCost(){
        return RepairCost.get();
    }
    
    public void setRepairCost(String value){
        RepairCost.set(value);
    }
    
    public StringProperty repairCostProperty(){
        return RepairCost;
    }
    
    public String getMisc(){
        return Misc.get();
    }
    
    public void setMisc(String value){
        Misc.set(value);
    }
    
    public StringProperty miscProperty(){
        return Misc;
    }
    
    public String getDate(){
        return Date.get();
    }
    
    public void setDate(String value){
        Date.set(value);
    }
    
    public StringProperty dateProperty(){
        return Date;
    }
}
