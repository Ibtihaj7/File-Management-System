package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Decryption;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.classification.FileClassifier;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static edu.najah.cap.Constant.Constant.*;
import static edu.najah.cap.Constant.Constant.CATEGORY_TYPE_NOT_FOUND;

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

    public static void checkCategoryNameAndPrint(String categoryName, String categoryType) throws CategoryNotFoundExeption {
        if(categoryName.equals(FILE_SIZE_CATEGORY))
            if(FileClassifier.getFileSizeRanges().containsKey(categoryType)) {
                FileService.viewFilesCategorizedBySize(categoryType);
                return;
            } else throw new CategoryNotFoundExeption(CATEGORY_TYPE_NOT_FOUND);
        if(categoryName.equals(FILE_TYPE_CATEGORY))
            if (FileClassifier.getFileTypeRuler().containsKey(categoryType)) {
                FileService.viewFilesCategorizedByType(categoryType);
                return;
            } else throw new CategoryNotFoundExeption(CATEGORY_TYPE_NOT_FOUND);
        if(categoryName.equals(FILE_NEW_CATEGORY))
            if(FileClassifier.getFileCategoryRulers().containsKey(categoryType)) {
                FileService.viewFilesWithCustomCategory(categoryType);
                return;
            }else throw new CategoryNotFoundExeption(CATEGORY_TYPE_NOT_FOUND);

        throw new CategoryNotFoundExeption(CATEGORY_TYPE_NOT_FOUND);
    }
}
