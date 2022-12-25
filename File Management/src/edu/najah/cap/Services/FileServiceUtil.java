package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Decryption;
import edu.najah.cap.Security.Encryption;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public abstract class FileServiceUtil {
    public static SystemFile getFileInformation(String url){
        File file = new File(url);
        int lastIndex = file.getName().lastIndexOf(".");
        int firstIndex = file.getName().lastIndexOf("\\");
        String name = file.getName().substring(firstIndex+1,lastIndex);
        String type = file.getName().substring(lastIndex + 1);
        int size = (int) file.length();
        String encryptedFileName= Encryption.encodeBase64(name);
        String encryptedFilePath= Encryption.encodeBase64(url);
        return new SystemFile(encryptedFileName,type,size,encryptedFilePath,0);
    }
    public static void insertToDB(SystemFile file){
            String query = "INSERT INTO files VALUES (?, ?, ?, ?, ?)";
            try {
                MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(file.getName(),file.getType(),file.getSize(),file.getPath(),0));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("The file has been imported successfully");
    }
    public static boolean isEmpty(ResultSet resultSet){
        try {
            if (resultSet==null || !resultSet.first()) {
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static void addVersion(SystemFile encryptedFile,ResultSet result){
        try {
            int newVersion = result.getInt("version");
            while (result.next()) {
                newVersion = result.getInt("version");
            }
            newVersion += 1;
            String query = "INSERT INTO files VALUES (?, ?, ?, ?, ?)";
            MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(encryptedFile.getName(),encryptedFile.getType()
                    ,encryptedFile.getSize(),encryptedFile.getPath(),newVersion));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("The file has been imported successfully");
    }
    public static ResultSet findExistFile(SystemFile encryptedFile){
        ResultSet statement = null;
        String query = "SELECT * FROM files WHERE name = ? AND type = ?";
        try {
            statement = MySQLDatabase.getInstance().executeQuery(query, Arrays.asList(encryptedFile.getName(),encryptedFile.getType()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return statement;
    }
    public static void Overwrite(SystemFile encryptedFile, ResultSet result){
        String query = "Update files SET type = ?, size = ?, path = ? WHERE name = ?";
        try {
            if (!isEmpty(result)) {
                MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(encryptedFile.getType(), encryptedFile.getSize()
                        , encryptedFile.getPath(), encryptedFile.getName()));

                System.out.println("The file has been imported successfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void printAvailableFiles(ResultSet statement){
        int i = 1;
        try {
            System.out.println("File " + i++ + " {name: " + Decryption.decodeBase64(statement.getString("name")) + ", path: " + Decryption.decodeBase64(statement.getString("path")) + ", type: " + statement.getString("type") + ", size: " + statement.getInt("size") + ", version: " + statement.getInt("version")+"}");
            while (statement.next()) {
                System.out.println("File " + i++ + " {name: " + Decryption.decodeBase64(statement.getString("name")) + ", path: " + Decryption.decodeBase64(statement.getString("path")) + ", type: " + statement.getString("type") + ", size: " + statement.getInt("size") + ", version: " + statement.getInt("version")+"}");
            }
            System.out.println();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static ResultSet getAvailableFiles(){
        String query= "select name,type,size,path,MAX(version) AS version from files GROUP BY name";        ResultSet statement = null;
        try {
            statement = MySQLDatabase.getInstance().executeQuery(query, null);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return statement;
    }
    public static ResultSet findFileByName(String name, String type){
        ResultSet statement = null;
        String query =  "SELECT * FROM files WHERE name = ? AND type = ?";
        try{
            statement = MySQLDatabase.getInstance().executeQuery(query,Arrays.asList(name,type));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return statement;
    }
    public static SystemFile findCurrentFile(ResultSet statement){
        String fileName = null, fileType = null, filePath = null;
        int fileSize = -1, fileVersion = -1;
        try {
            statement.last();
             fileName = Decryption.decodeBase64(statement.getString("name"));
                    fileType = statement.getString("type");
                    filePath = Decryption.decodeBase64(statement.getString("path"));
                    fileSize = statement.getInt("size");
                    fileVersion = statement.getInt("version");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        if(fileName != null && fileType != null && filePath != null && fileSize != -1 && fileVersion != -1) {
            return new SystemFile(fileName, fileType, fileSize, filePath,fileVersion);
        }
        return null;
    }
}
