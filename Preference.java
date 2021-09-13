/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.util.prefs.Preferences;

public class Preference{
   public Preferences prefs;
   String Username = "Username";
   String Password = "Password";
   String firstName = "firstName";
   String lastName = "lastName";
   
   private Main application;
   
   public void initialize()
   {
       prefs = Preferences.userRoot().node(this.getClass().getName());
   }
    
  public void setPreference(LoginController loginController) {
    // This will define a node in which the preferences can be stored
      
    // First we will get the values
    // Define a boolean value
    try{
       prefs.get(Username,"");
       prefs.get(Password,"");
       prefs.get(firstName,"");
    }
    catch(Exception e)
    {  
        System.out.println(e);
    }
    // Define a string with default "Hello World
   // prefs.get(ID2,null);
   
    // now set the values
   try {
     prefs.put(Username,application.user.getUsername());
     prefs.put(Password,application.user.getPassword());
     prefs.put(firstName,application.user.getFirstname());
     prefs.put(lastName,application.user.getLastname());
      }
       catch(Exception e)
       { System.out.println("Anurag2");
           System.out.println(e);
       }
    
    

    // Delete the preference settings for the first value
   
  }
  public boolean isPreferenceStored()
  {    
    if(prefs.get(Username,"").equals("")&&prefs.get(Password,"").equals(""))
      { 
          return false;
      }   
      else
      {
       return true;
      }
     
  }
  public void removePreference()
  {
      prefs.remove(Username);
      prefs.remove(Password);
      prefs.remove(firstName);
      prefs.remove(lastName);
  }
}
   
