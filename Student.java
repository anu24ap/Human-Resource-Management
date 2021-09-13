/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author yaduv
 */
public class Student {
   // private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleIntegerProperty age;
    private final SimpleBooleanProperty internship;
   // private final Button button;
    
    public Student(String name,String surname,Integer age,boolean internship)
    {
        //this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.surname=new SimpleStringProperty(surname);
        this.age=new SimpleIntegerProperty(age);
        this.internship = new SimpleBooleanProperty(internship);
       /* this.button=button;
        
        button.setId(Integer.toString(id));*/
       
    }
 /*   public Integer getId()
    {
        return id.get();
    }*/
     public String getName()
    {
        return name.get();
    }
      public String getSurname()
    {
        return surname.get();
    }
       public Integer getAge()
    {
        return age.get();
    }
       public boolean getInternship()
    {
       return internship.get();              
     }
    /* public Button getButton()
     {
           return button;
     }
   */
}
