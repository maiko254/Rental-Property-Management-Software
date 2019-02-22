/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalui;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Mike
 */
public class sqlconnection {
    public static Connection connector(){
        try {
            Class.forName("Org:sqlite:JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Mike\\Documents\\NetBeansProjects\\SQLite\\Residential Rental Management Software");
            return conn;
        } catch (Exception e) {
            return null;
        }
    }
}
