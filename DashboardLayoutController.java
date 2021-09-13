package demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class DashboardLayoutController extends AnchorPane implements Initializable{

    private Main application;
   // @FXML
    //public Button logout;
    @FXML
    public Label user;
    @FXML
    public HBox students;
    
    @FXML
    public  BorderPane contentPane;
    @FXML
    public HBox logout;
    @FXML
    public ScrollPane scrollpane;
    @FXML
    public MenuItem mlogout;
   
    public void setApp(Main application,Preference preference){
        user.setText(preference.prefs.get(preference.firstName,""));
        this.application = application;
        /*LoginController lc= new LoginController();
        fname.lc.getfirst();*/
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
          scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
   
    public void processLogout()
    {  
        application.userLogout();
    
    }
    
   public void seeStudents() 
   {  
        application.studentsDetails();
   }
   
   public void home()
   {  
        application.dashboard.contentPane.setCenter(null);
   }

}
