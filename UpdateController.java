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
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
                                                                                


public class UpdateController implements Initializable {
    
    @FXML
    public TextField userId;
    public String ID;
    public static Boolean Delete;
    
    public Button ok;
    private Main application;
    
    public void setApp(Main application)
    {
        this.application=application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userId.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
            @Override
            public void handle(KeyEvent event) {
                
                if(event.getCode().equals(ENTER))
                {
                    try {
                        gotoUpdate();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
                    
                });
           
    }    
    public void gotoUpdate() throws ClassNotFoundException
    {  
         ConnectionStudent connectionClass = new ConnectionStudent();
       Connection connection = connectionClass.getConnection(); 
       PreparedStatement pst=null;
               
        if(!Delete)
    { 
        try{
           Statement statement = connection.createStatement();
            String sql = "SELECT * FROM table_details WHERE ID='" + userId.getText()  + "'";
            ResultSet resultSet = statement.executeQuery(sql);
         
        if (resultSet.next()) {
              ID = resultSet.getString("ID"); 
              application.updateDetails(ID);
            }
        else
        {
            Alert alertDialog=new Alert(Alert.AlertType.ERROR);
			alertDialog.setTitle("Error");
			alertDialog.setHeaderText("     Invalid UserID!!!");
			alertDialog.show(); 
        }
        } 
        catch(Exception e)
        {
            System.out.println(e);
        } 
    }
    else
    {
        try{
            Statement statement = connection.createStatement();
            String sqlCheck = "SELECT * FROM table_details WHERE ID='" + userId.getText()  + "'";
            ResultSet resultSet = statement.executeQuery(sqlCheck);
              
        if (resultSet.next()) {
              ID = resultSet.getString("ID"); 
              
               String sqlDelete = "DELETE FROM table_details where ID="+ID;
               
                pst= connection.prepareStatement(sqlDelete);
                pst.executeUpdate();
            connection.close();   
               
            application.EditStage.hide();
            
               Alert alertDialog=new Alert(Alert.AlertType.INFORMATION);
		//	alertDialog.setTitle("");
			alertDialog.setHeaderText("    Record Deleted");
			alertDialog.show(); 
            }
        else
        {
            Alert alertDialog=new Alert(Alert.AlertType.ERROR);
			alertDialog.setTitle("Error");
			alertDialog.setHeaderText("     Invalid UserID!!!");
			alertDialog.show(); 
        }
        }
        catch(Exception e)
        {
            System.out.println();
        }
    }
 
}
 
 }
   
