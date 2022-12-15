package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Database.intf.IDatabase;

public class App {
    public static void main(String[] args) {
        IDatabase database = MySQLDatabase.getInstance();
        try{
            database.execute("SELECT * FROM files");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}