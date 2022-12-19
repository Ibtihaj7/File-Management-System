package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.File.SystemFile;
import edu.najah.cap.FileRepository.intf.ExportBehaviour;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Export implements ExportBehaviour {

    @Override
    public SystemFile export(String filename, String category) throws SQLException {
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

        return ( new SystemFile(statement.getString("name"),statement.getString("type"),
                statement.getInt("size"), statement.getString("category"),
                statement.getString("path"), statement.getString("version")));
    }
    public SystemFile export(String filename) throws SQLException {
        return export(filename, "null");
    }
}