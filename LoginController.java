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

import demo.ConnectionClass;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.effect.Reflection;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import demo.model.*;


/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    Button Login;
    //public TextField textField;
    @FXML
    public Label result;
    
    private Main application;
    
    
 
    public void setApp(Main application) {
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
      Password.setOnKeyPressed((KeyEvent event) -> {
          if(event.getCode().equals(ENTER))
              try {
                  processLogin();
              } catch (SQLException ex) {
                  Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
              } catch (ClassNotFoundException ex) {
                  Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
              }
      });
        
    }

    public void close(ActionEvent event) {
       Platform.exit();
        System.exit(0);
    }

    public void Link(ActionEvent event) {
        //open url
    }

    public void checkBox(ActionEvent event) {

    }

  
    public void processLogin() throws SQLException, ClassNotFoundException {
        
       if(Username.getText().equals("")||Password.getText().equals(""))
       {
           result.setText("! Please fill all the fields");
           return;
       }
       ConnectionClass connectionClass = new ConnectionClass();

        Connection connection = connectionClass.getConnection();
        
        try {
            Statement statement = connection.createStatement();

           String sql = "SELECT * FROM login_info WHERE Username='" + Username.getText() + "' AND Password='" + Password.getText() + "' ";
          
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                application.user.setFirstname(resultSet.getString("firstName"));
                application.user.setLastname(resultSet.getString("lastName"));
                application.user.setUsername(resultSet.getString("Username"));
               // application.user.setFirstname(resultSet.getString("email_ID");
                 application.user.setPassword(resultSet.getString("Password"));
                
               Preference preference = new Preference();
               preference.initialize();
                preference.setPreference(this);

                application.userLogging();

            }
            else
            {
                result.setText("! Invalid Username or Password");
                
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
       
     
      /*try{
         URL url = new URL("http://www.empoweryouth.in/api/v1/account/login");
	    Map <String,Object> params = new LinkedHashMap<>();
	    params.put("username", Username.getText());
	    params.put("password", Password.getText());
	    params.put("user_type", "Super Admin");
	   // params.put("message", "Hello Post Test success");
	    StringBuilder postData = new StringBuilder();
            
	    for (Map.Entry <String,Object> param : params.entrySet()) {
	        if (postData.length() != 0) postData.append('&');
	        postData.append(java.net.URLEncoder.encode((String) param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(java.net.URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	    }
            
	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	    conn.setDoOutput(true);
            
	    conn.getOutputStream().write(postDataBytes);
	    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    StringBuilder sb = new StringBuilder();
	    for (int c; (c = in.read()) >= 0;)
	        sb.append((char)c);
	    String response = sb.toString();
	    System.out.println(response);
            
	   /* org.json.JSONObject myResponse = new org.json.JSONObject(response);
	    System.out.println("result after Reading JSON Response");
	    System.out.println("username- "+myResponse.get("username"));
	    System.out.println("first_name- "+myResponse.get("first_name"));
	    JSONObject form_data = (JSONObject) myResponse.get("form");
	    System.out.println("CODE- "+form_data.get("CODE"));
	    System.out.println("email- "+form_data.get("email"));
	    System.out.println("message- "+form_data.get("message"));
	    System.out.println("name"+form_data.get("name"));
       
           JSONParser parse = new JSONParser();
           JSONObject jobj = (JSONObject)parse.parse(response);
  
           JSONObject jsonobj = (JSONObject)jobj.get("data");
           
            application.user.setUsername((String)jsonobj.get("username"));
            application.user.setFirstname((String)jsonobj.get("first_name"));
            application.user.setLastname((String)jsonobj.get("last_name"));
            application.user.setPassword((String)jsonobj.get("password"));

             Preference preference = new Preference();
             preference.initialize();
             preference.setPreference(this);
       
             application.userLogging();
            
           }
           catch (Exception e) {
   			System.out.println(e);
   		}*/
    }
    public void openSignUpPage(ActionEvent event) throws Exception
    {
        application.openSignUp();
    }

}

