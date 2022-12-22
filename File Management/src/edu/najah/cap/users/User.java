package edu.najah.cap.users;

import edu.najah.cap.Database.impl.MySQLDatabase;


import java.sql.ResultSet;
import java.sql.SQLException;


public class User {



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
