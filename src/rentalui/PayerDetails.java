/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *Creating model class for PayerDetails table
 * @author Mike
 */
public class PayerDetails {
    public StringProperty TenantHouseNo;
    public StringProperty PaidAmount;
    public StringProperty PayerName;
    public StringProperty monthCol;
    public StringProperty dateCol;
    public StringProperty paymentMethodCol;
    
    //Constructor
    public PayerDetails(String M, String Paid, String Name, String Month, String Date, String PaymentMethod){
        this.TenantHouseNo = new SimpleStringProperty(M);
        this.PaidAmount = new SimpleStringProperty(Paid);
        this.PayerName = new SimpleStringProperty(Name);
        this.monthCol = new SimpleStringProperty(Month);
        this.dateCol = new SimpleStringProperty(Date);
        this.paymentMethodCol = new SimpleStringProperty(PaymentMethod);
    }
    
    public String getTenantHouse(){
        return TenantHouseNo.get();
    }
    
    public void setTenantHouse(String value){
        TenantHouseNo.set(value);
    }
    
    public StringProperty tenantHouseProperty(){
        return TenantHouseNo;
    }
    
    public String getPaidAmount(){
        return PaidAmount.get();
    }
    
    public void setPaidAmount(String value){
        PaidAmount.set(value);
    }
    
    public StringProperty paidAmountProperty(){
        return PaidAmount;
    }
    
    public String getPayerName(){
        return PayerName.get();
    }
    
    public void setPayerName(String value){
        PayerName.set(value);
    }
    
    public StringProperty payerNameProperty(){
        return PayerName;
    }
    
    public String getMonth(){
        return monthCol.get();
    }
    
    public void setMonthCol(String value){
        monthCol.set(value);
    }
    
    public StringProperty monthColProperty(){
        return monthCol;
    }
    
    public String getDate(){
        return dateCol.get();
    }
    
    public void setDatecol(String value){
        dateCol.set(value);
    }
    
    public StringProperty dateColProperty(){
        return dateCol;
    }
    
    public  String getPaymentMethod(){
        return paymentMethodCol.get();
    }
    
    public void setPaymentMethodCol(String value){
        paymentMethodCol.set(value);
    }
    
    public StringProperty paymentMethodProperty(){
        return paymentMethodCol;
    }
}
