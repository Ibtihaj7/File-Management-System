package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.ExportBehaviour;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Export implements ExportBehaviour {

    @Override
    public File export(String filename, String category) throws SQLException {
        ResultSet statement = null;
        try{
            String query = "select * from files where name = '"+filename+"' AND category = '"+category+"'";
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!statement.next()){
            System.out.println("There is no file with this name or category.");
            return null;
        }

        return ( new File(statement.getString("path")) );
    }
    public File export(String filename) throws SQLException {
        return export(filename, "null");
    }
}