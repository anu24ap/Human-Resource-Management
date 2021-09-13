/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class StudentUpdateController implements Initializable {
    
    public String ID;
    @FXML
   public TextField name;
    @FXML
   public TextField surname;
    @FXML
   public TextField age;
    @FXML
   public TextField internship;
    
   private Main application;

   ConnectionStudent connectionClass = new ConnectionStudent();
   Connection connection;
     
   PreparedStatement pst=null;

    public StudentUpdateController() throws ClassNotFoundException {
       
        this.connection = connectionClass.getConnection();
        
    }
   
   public void setApp(Main application,String ID) 
   {
       this.application=application;
       this.ID=ID;
       
        try{
        Statement statement = connection.createStatement();
            String sql = "SELECT * FROM table_details WHERE ID='" + ID + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            
        if(resultSet.next())    
        {    
            name.setText(resultSet.getString("Name"));
            surname.setText(resultSet.getString("Surname"));
            age.setText(resultSet.getString("Age"));
            internship.setText(resultSet.getString("Internship"));
        }
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
   }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void Update(ActionEvent event) throws ClassNotFoundException
    {
        
        try{
           
          String sql="UPDATE table_details SET Name=?, Surname=?, Age=?, Internship=? where ID="+ID;
        
            pst= connection.prepareStatement(sql);
            
            pst.setString(1,name.getText());
            pst.setString(2,surname.getText());
            pst.setString(3,age.getText());
            pst.setString(4,internship.getText());
            
             pst.executeUpdate();
            
            connection.close();   
    }
        catch(Exception e)
        {
            System.out.println(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Details Successfully Updated");
           // alert.setContentText("You have created a new Account");
            alert.show();
            
           application.EditStage.hide();
         
           application.studentsDetails();
    }
  
}
