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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yaduv
 */
public class StudentsDataController implements Initializable {
    
     public TextField id;
    public TextField name;
    public TextField surname;
    public TextField age;
    public TextField internship;
    
    private Main application;
    
    public void setApp(Main application)
    {
        this.application = application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    
    
    public void Save(ActionEvent event) throws ClassNotFoundException
    {
         ConnectionStudent connectionClass = new ConnectionStudent();
       Connection connection = connectionClass.getConnection();
     
        PreparedStatement pst=null;
        
        try{
            
          String sql="Insert into table_details (ID,Name,Surname,Age,Internship) value(?,?,?,?,?)";
            pst= connection.prepareStatement(sql);
            pst.setString(1,id.getText());
            pst.setString(2,name.getText());
            pst.setString(3,surname.getText());
            pst.setString(4,age.getText());
            pst.setString(5,internship.getText());
         
             pst.executeUpdate();
            
            connection.close();  
           
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Details Successfully added");
        //alert.setContentText("");
            alert.show();
        }
        
        catch(Exception e){  
        }
        application.EditStage.hide();

    }
     public void close()
    {
        application.EditStage.hide();
    }
}
