package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.Export;
import edu.najah.cap.Security.Authorization;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NormalExport implements Export {

    @Override
    public File export(String filename, String category) throws SQLException {
        if(!Authorization.hasAdminPermission()&&!Authorization.hasStaffPermission()){
            return null;
        }
        ResultSet statement = null;
        try{
            String query = "select * from user WHERE name = '"+filename+"' AND  category = '"+category+"'";
            statement = MySQLDatabase.getInstance().execute(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(statement.next()==false){
            System.out.println("there is no files");
            return null;
        }
        System.out.println(statement.getString("path"));

        return ( new File(statement.getString("path")) );
    }
}