/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package demo;

import com.sun.javafx.application.LauncherImpl;
import demo.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
//impor

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {
    private Stage LoginStage = new Stage();
    private Stage ProfileStage = new Stage();
    protected Stage SignUpStage = new Stage();
    private Stage StudentsStage = new Stage();
    protected Stage EditStage = new Stage();
    protected Stage StudentUpdateStage = new Stage();
    private Stage TableStage = new Stage();

    Preference preference = new Preference();
    DashboardLayoutController dashboard = new DashboardLayoutController();
    User user = new User();
    // private login_info loggedUser;
    //  private final double MINIMUM_WINDOW_WIDTH = 390.0;
    //private final double MINIMUM_WINDOW_HEIGHT = 500.0;
    TableController table;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);  
    }

    
    @Override
    public void start(Stage primaryStage){
      try{     
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        
         primaryStage.setScene(new Scene(pane));
         primaryStage.initStyle(StageStyle.TRANSPARENT);
         primaryStage.show();
          FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
               
        try {
          primaryStage.hide();
           
          preference.initialize();
         
         if(!preference.isPreferenceStored())
           {  
              gotoLogin();
               
          } 
           else
            {
                
               gotoProfile(); 
            }
         
        } catch (Exception ex) {
            //  Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);\
               
        }
  
            });
    }
      catch(Exception e)
      {    
          System.out.println(e);
      }
        
    }

    //public login_info getLoggedUser() {
    //  return loggedUser;
    //}
    public boolean userLogging() {
       LoginStage.hide();
        gotoProfile();
        return true;
    }
    
   public boolean userLogout() {
       ProfileStage.hide();
       SignUpStage.hide();
       preference.removePreference();
        gotoLogin();
        return true;
    }
    
    // void userLogout(){
    //   loggedUser = null;
    // gotoLogin();
    //}
    private void gotoProfile() {
        try {
          
            AnchorPane pane = FXMLLoader.load(getClass().getResource("connectingServer.fxml"));
        Stage stage = new Stage();
         stage.setScene(new Scene(pane));
         stage.initStyle(StageStyle.TRANSPARENT);
         stage.show();
          FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
             try{ 
                 stage.hide();
            dashboard = (DashboardLayoutController) replaceSceneContent2("dashboardlayout.fxml");
      
            dashboard.setApp(this,preference);
           
            }
             
            catch (Exception ex) {         
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            });
           /*SignUpController profile = (SignUpController) replaceSceneContent("profile.fxml");
            profile.setApp(this);*/
        } catch (Exception ex) {
            
            System.out.println(ex);
        }
    }

    private void gotoLogin() {
        try {
           // Session session= new Session() {};
            LoginController login = (LoginController) replaceSceneContent1("login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void studentsDetails()
    {  
        try {
           table = (TableController)replaceScene("Table.fxml");
           table.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
    
    public void editDetails()
    {  
         StudentsStage.hide();
        try {
          
             StudentsDataController student = (StudentsDataController) replaceSceneContent4("studentsData.fxml");
            student.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateDetails(String ID)
    {
        try{
            StudentUpdateController student = (StudentUpdateController)replaceSceneContent4("studentUpdate.fxml");
            student.setApp(this,ID);
        }
        catch(Exception e)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e); 
        }
    }
    
    public void checkUserId()
    {  //TableStage.hide();
        try{
            UpdateController update = (UpdateController)replaceSceneContent4("Update.fxml");
            update.setApp(this);
        }
        catch(Exception e)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e); 
        }
    }
    
    public void inviteStudents()
    {
        try{
            InviteController inviteController = (InviteController) replaceSceneContent5("invite.fxml");
            inviteController.setApp(this);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    private Initializable replaceSceneContent1(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        try
        {      
            LoginStage.initStyle(StageStyle.UNDECORATED);
        }
        catch(Exception e)
        {
            
        }
        Scene scene = new Scene(page);
        LoginStage.setScene(scene);
        LoginStage.show();
        
         return (Initializable) loader.getController();
    }
    
     private Initializable replaceSceneContent2(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        /*page.prefWidthProperty().bind(page.widthProperty());
         page.prefHeightProperty().bind(page.heightProperty());*/
       
       try { ProfileStage.initStyle(StageStyle.DECORATED);
       }
       catch(Exception e)
       {
           
       }
        Scene scene = new Scene(page);
      //  ProfileStage.setTitle("Dashboard");
        ProfileStage.setScene(scene);
       // stage.setFullScreen(true);
        ProfileStage.show();
        
        return (Initializable) loader.getController();
        
    }
      private Initializable replaceSceneContent3(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        try{
             SignUpStage.initStyle(StageStyle.UNDECORATED);
        }
        catch(Exception e)
        {
            
        }
        Scene scene = new Scene(page);
        SignUpStage.setScene(scene);
        SignUpStage.setResizable(false);
      SignUpStage.show();
        
        return (Initializable) loader.getController();
        
    }
     
       private Initializable replaceSceneContent4(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        try{
        EditStage.initStyle(StageStyle.UNDECORATED);
        }
        catch(Exception e)
        {
            
        }
        Scene scene = new Scene(page);
        EditStage.setScene(scene);
      //  EditStage.setTitle("Edit Details");
        EditStage.setResizable(true);
        EditStage.show();
        
        return (Initializable) loader.getController();
        
    }
       private Initializable replaceSceneContent5(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        /*page.prefWidthProperty().bind(page.widthProperty());
         page.prefHeightProperty().bind(page.heightProperty());*/
       
       try { StudentsStage.initStyle(StageStyle.UTILITY);
       }
       catch(Exception e)
       {
           
       }
        Scene scene = new Scene(page);
        StudentsStage.setScene(scene);
       // StudentsStage.setTitle("List of Students");
       // stage.setFullScreen(true);
        StudentsStage.show();
        
        return (Initializable) loader.getController();
        
    }
       
       private Initializable replaceScene(String fxml) throws IOException
       {
         FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
       dashboard.contentPane.setCenter(page);
        
        return (Initializable) loader.getController();
       }

       
     public void openSignUp()
     {   try  {
         LoginStage.hide();
        SignUpController signup = (SignUpController) replaceSceneContent3("signup.fxml");
        signup.setApp(this);
     }
     catch(Exception e)
     {
         System.out.println(e);
     }
     }
}
