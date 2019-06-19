/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
import java.sql.*;
import javax.swing.*;
public class javaconnect {
  
    Connection conn= null;
   public static Connection ConnercrDb(){
   
   try{
   Class.forName("com.mysql.jdbc.Driver");
   Connection conn =DriverManager.getConnection("jdbc:mysql://localhost/authenticationsys","root","root");
   //JOptionPane.showMessageDialog(null, "Connection Established");javaandmysql
   return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
         return null;
            }
   
   }
    
  
    
}
