/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;



public class SignUpController extends AnchorPane implements Initializable {

    public TextField firstName;
    public TextField lastName;
    public TextField userName;
    public TextField password;
    public TextField email_id;
    public TextField confirmPass;
    public Button save;
    private Main application;
    @FXML
    public Label warning;
    public void setApp(Main application)
    {
        this.application=application;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }   
    public void saveProfile(ActionEvent event) throws ClassNotFoundException
    {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
     
        PreparedStatement pst=null;
        
        if(firstName.getText().equals("")||lastName.getText().equals("")||userName.getText().equals("")
                ||password.getText().equals("")||email_id.getText().equals("")
                )
        {
            warning.setText("Fill all the fields.");
           /* Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Fill all the fields");
            alert.setContentText("Don't leave any field empty");
            alert.show();*/
            return;
        }
        
        if(!(password.getText().equals(confirmPass.getText())))
        {
            warning.setText("Please re-enter the same password.");
            /*Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Password don't match");
            alert.setContentText("Please enter same password in Re-enter password field also");
            alert.show();*/
            return;
        }
        
        try{
            String sql="Insert into login_info (Username,Password,firstName,lastName,email_ID) value(?,?,?,?,?)";
            pst= connection.prepareStatement(sql);
            pst.setString(1,userName.getText());
            pst.setString(2,password.getText());
            pst.setString(3,firstName.getText());
            pst.setString(4,lastName.getText());
            pst.setString(5,email_id.getText());
         
            
             pst.executeUpdate();
            
            connection.close();
          try{  
              
              
              warning.setText("You have successfully ceated a new account!");
            /*Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Congratulation!");
            alert.setContentText("You have created a new Account");
            alert.show();*/
            
            application.SignUpStage.hide();
           application.userLogout();
          }
          catch(Exception e)
          {
              System.out.println(e);
          }
          
           
    }
       catch(Exception e)
           {
               System.out.println(e);
           } 
    }
    
    public void resetDetails(ActionEvent event)
    {
        firstName.setText("");
        lastName.setText("");
        userName.setText("");
        password.setText("");
        email_id.setText("");
        confirmPass.setText("");
    }
    public void back()
    {
        application.userLogout();
    }
    public void close()
    {
        application.SignUpStage.hide();
    }
}
