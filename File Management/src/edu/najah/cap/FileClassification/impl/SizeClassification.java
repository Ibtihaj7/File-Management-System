package edu.najah.cap.FileClassification.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileClassification.ClassificationType;
import edu.najah.cap.FileClassification.Interface.IClassification;
import edu.najah.cap.FileRepository.SystemFile;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class SizeClassification implements IClassification {

    @Override
    public void addClassification(String fileNama) throws SQLException {
        ResultSet statement = null;
        try {
            String query ="SELECT * FROM files WHERE name = '"+fileNama+"'";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        }catch (Exception e){
            e.printStackTrace();
        }

        if (!statement.next()) {
            //throw exeption
            return;
        }

        int sizeFile = statement.getInt("size");

        String name = statement.getString("name");
        String type = statement.getString("type");
        int size = statement.getInt("size");
        String category = statement.getString("category");
        String path = statement.getString("path");
        int version = statement.getInt("version");
        SystemFile file = new SystemFile(name,type,size,category,path,version);

        if(!ClassificationType.sizeClasification.containsKey(sizeFile)){
            ClassificationType.sizeClasification.put(sizeFile,new ArrayList<SystemFile>());
        }
        ClassificationType.sizeClasification.get(sizeFile).add(file);
        System.out.println("The file has been compiled successfully.");
    }
}
