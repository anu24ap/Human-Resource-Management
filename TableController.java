/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author yaduv
 */
public class TableController implements Initializable {
    
    public Button add;
    private Main application;
    public Button update;
    int rowsPerPage=10;
    
    int row = 0;
    
    Button button[];
    @FXML
    TextField searchField;
    
    @FXML public TableView<Student> table;
   // @FXML private TableColumn<Student,Integer> id;
    @FXML private TableColumn<Student,String> name;
    @FXML private TableColumn<Student,String> surname;
    @FXML private TableColumn<Student,Integer> age;
    @FXML private TableColumn<Student,Boolean> internship;
    @FXML private TableColumn<Student,Button> action;
   // @FXML public ScrollPane scrollpane;
 
   
    ConnectionStudent connectionClass = new ConnectionStudent();
    Connection connection;
    ResultSet rs;
    @FXML
     private Pagination pagination;
    
    public ObservableList<Student> list = FXCollections.observableArrayList(); 
    
    public TableController() throws IOException
    {    
        
       /*  try{
           this.connection = connectionClass.getConnection();
           rs = connection.createStatement().executeQuery("Select * from table_details");
            while(rs.next())
           {
             row++;
           }
           } 
        catch(Exception e)
       {
           System.out.println(e);
       }
       button = new Button[row];  
*/
    }
    
    public void setApp(Main application)
    {
        this.application = application;
        
    }
      
      
    public void handleAction(ActionEvent event)
    {
    for(int i=0;i<row;i++)
    {
        if(event.getSource()==button[i])
        {
            application.updateDetails(button[i].getId());
        }
    }
    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //   scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
             pagination.setPageFactory(this::createPage);    
             
                FilteredList<Student> filteredData = new FilteredList<>(list, e -> true);
                
                table.setItems(list);
                
        searchField.setOnKeyReleased(e ->{
            searchField.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredData.setPredicate((Predicate<? super Student>) student->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if(student.getName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }else if(student.getSurname().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    
                    return false;
                });
            });
            SortedList<Student> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
           
        });
                
//       id.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
       name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
       surname.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
       age.setCellValueFactory(new PropertyValueFactory<Student,Integer>("age"));
       internship.setCellValueFactory(new PropertyValueFactory<Student, Boolean>("internship"));
       
      
  
        
       /* try {
            /*     int cur_row=0;
            
            try{
            
            for(int i=0;i<row;i++)
            {
            button[i]=new Button("Edit");
            button[i].setOnAction(this::handleAction);
            
            
            }
            rs.beforeFirst();
            while(rs.next()&&cur_row<row)
            {
            list.add(new Student(rs.getInt("id"),rs.getString("name"),rs.getString("surname"),rs.getInt("age"),rs.getBoolean("internship"),button[cur_row]));
            cur_row++;
            }
            }
            catch(Exception e)
            {
            System.out.println(e);
            }
            
       
    }
    */
    }
    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;  
        
      int  toIndex = Math.min(fromIndex + rowsPerPage, list.size());
        this.fetchData((pageIndex+1)*rowsPerPage);
        
        table.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));
        return table;
    }

    public void fetchData(int arr_size)
    {
         String inline = "";
	
		try
		{       
                
			URL url = new URL("http://www.empoweryouth.in/api/v1/students");
			//Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//Set the request to GET or POST as per the requirements
			conn.setRequestMethod("GET");
			//Use the connect method to create the connection bridge
			conn.connect();
			//Get the response status of the Rest API
			int responsecode = conn.getResponseCode();
			System.out.println("Response code is: " +responsecode);
			
			//Iterating condition to if response code is not 200 then throw a runtime exception
			//else continue the actual process of getting the JSON data
			if(responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " +responsecode);
			else
			{
				//Scanner functionality will read the JSON data from the stream
				Scanner sc = new Scanner(url.openStream());
				while(sc.hasNext())
				{
					inline+=sc.nextLine();
				}
				System.out.println("\nJSON Response in String format"); 
				System.out.println(inline);
				//Close the stream when reading the data has been finished
				sc.close();
			}
			
			//JSONParser reads the data from string object and break each data into key value pairs
			JSONParser parse = new JSONParser();
			//Type caste the parsed json data in json object
			JSONObject jobj = (JSONObject)parse.parse(inline);
			//Store the JSON object in JSON array as objects (For level 1 array element i.e Results)

		        JSONArray jsonarr_1 = (JSONArray) jobj.get("students"); 

                        for(int i=0;i<arr_size;i++)
                       {                         

                            JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
                       
                           // int data1 = Integer.parseInt(jsonobj_1.get("id").toString());
                            String data2 = (String) jsonobj_1.get("name");
                            String data3 = (String) jsonobj_1.get("surname");
                            int data4 = Integer.parseInt(jsonobj_1.get("age").toString());
                            boolean data5 = Boolean.valueOf(jsonobj_1.get("internship").toString());
                         //  System.out.println(data1);
                           System.out.println(data2);
                           System.out.println(data3);
                           System.out.println(data4);
                           System.out.println(data5);
                           
                           list.add(new Student(data2,data3,data4,data5));
                          
                        }

                        conn.disconnect();      
			//Disconnect the HttpURLConnection stream
                }
                catch(Exception e)
                        {
                            System.out.println(e);
                        }
                
    }
    
public void add() throws Exception
{   try{
    application.editDetails();
    }
  catch(Exception ex)
{
    System.out.println(ex);
}
}

 public void invite()
    {
        application.inviteStudents();
    }
/*public void update(ActionEvent e)
{   
    UpdateController.Delete=false;
    application.checkUserId();
}

public void delete(ActionEvent e)
{
    UpdateController.Delete=true;
    application.checkUserId();
}
*/
}