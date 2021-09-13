/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
/**
 * FXML Controller class
 *
 * @author yaduv
 */

public class InviteController implements Initializable {
    
    @FXML
    private JFXTextField from;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXTextField to;
    @FXML
    private JFXTextField subject;
    @FXML
    private JFXTextArea  message;
    @FXML
    private JFXButton send;
    
    private Main application;
    /**
     * Initializes the controller class.
     */
    
    public void setApp(Main application)
    {
        this.application=application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        
        send.setOnAction(event->{
            
        Mailer.send(from.getText(),pass.getText(),to.getText(),subject.getText(),message.getText());

        });
    }  
   
}
