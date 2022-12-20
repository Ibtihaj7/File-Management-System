package edu.najah.cap.FileClassification.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SizeClassification {
    public SizeClassification() {
    }

    public void addClassification(){

        try {
            String query ="SELECT* FROM files ORDER BY size ASC ";
          ResultSet statement = MySQLDatabase.getInstance().selectQuery(query);
          while(statement.next()){
              System.out.println(statement.getString("name")+" "+statement.getString("type")+" "
                    +  statement.getInt("size")+" "+statement.getString("category")+" "+
                      statement.getString("path")+" "+statement.getInt("version"));
          }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
