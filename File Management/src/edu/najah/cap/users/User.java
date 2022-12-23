package edu.najah.cap.users;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.Security.Authorization;


import java.sql.ResultSet;
import java.sql.SQLException;


public class User {
private String email;
    private String password;
    private String name;
    private String role;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    private void setRole(){
        this.role = Authentication.getUserRole();
    }



    public String getEmail() {
        return email;
    }

    public String getRole() {

        return role;
    }




   // public void PrintClassifiedFiles(){
       // classification.displaySizeClassification();


    public void viewFiles() throws SQLException  {
        String query="select * from files";
        ResultSet statement = null;
        try{
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!statement.next()){
            System.out.println("there is no file in the system.");
            return;
        }
        for (int i = 1; statement.next(); i++) {
            System.out.println("File " + i + " name: "+statement.getString("name")+" \t path : "+statement.getString("path")+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }

}
