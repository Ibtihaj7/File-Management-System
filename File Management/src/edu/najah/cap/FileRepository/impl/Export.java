package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.FileRepository.intf.ExportBehaviour;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Export implements ExportBehaviour {

    @Override
    public SystemFile export(String filename, String category) throws SQLException {
        ResultSet statement = null;
        try{
//            String query = "select * from files where name = '"+filename+"' AND category = '"+category+"'";
            String query ="SELECT * FROM files WHERE (name LIKE '"+filename+"(%' OR name = '"+filename+"') AND category = '"+category+"'";
            query = category == null ? "SELECT * FROM files WHERE (name LIKE '"+filename+"(%' OR name = '"+filename+"') AND category = '"+category+"'":"SELECT * FROM files WHERE (name LIKE '"+filename+"(%' OR name = '"+filename+"') AND category IS NULL ";
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!statement.next()){
            System.out.println("There is no file with this name or category.");
            return null;
        }
        statement.last();
        String fileName=statement.getString("name"),
                fileType=statement.getString("type"),
                fileCategory=statement.getString("category"),
                filePath=statement.getString("path");
        int fileSize= statement.getInt("size"),
                fileVersion=statement.getInt("version");


        return ( new SystemFile(fileName,fileType, fileSize, fileCategory, filePath, fileVersion));
    }
    public SystemFile export(String filename) throws SQLException {
        return export(filename, "null");
    }
}