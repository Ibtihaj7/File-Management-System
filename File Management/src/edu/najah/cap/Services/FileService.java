package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.AES;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.VersionControl.VersionControl;
import edu.najah.cap.classification.FileClassifier;
import edu.najah.cap.users.User;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileService {
    static Scanner input = new Scanner(System.in);


    public static void doImport(String pathName, User createdBy) throws SQLException {

        if(!Authorization.hasAdminPermission(createdBy) && !Authorization.hasStaffPermission(createdBy)){
            return;
        }
        File file = new File(pathName);
        int index = file.getName().lastIndexOf(".");
        String name = file.getName().substring(0,index);
        String type = file.getName().substring(index + 1);
        int size = (int) file.length();
        String encryptedFileName= AES.encodeBase64(name);
        String encryptedFilePath= AES.encodeBase64(pathName);
        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name = '" +  encryptedFileName + "' ";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet statement1 = statement;
        if (!statement1.next()) {
            try {
                query = "INSERT INTO files VALUES ('" +  encryptedFileName + "','" + type + "'," + size + ",'" + encryptedFilePath+ "',0);";
                MySQLDatabase.getInstance().insertDeleteQuery(query);
                System.out.println("The file has been imported successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
              if(!Authorization.hasAdminPermission(createdBy)){
                VersionControl.Enable(encryptedFileName,type,size,encryptedFilePath,statement);
               return;
          }

            System.out.println("Do you want to disable Version Control ?");
            System.out.print("your choice : ");
            String choice = input.next();

            if (choice.equals("no")) {
                VersionControl.Enable(encryptedFileName,type,size,encryptedFilePath,statement);
                return;
            }
            if(choice.equals("yes")) {
                VersionControl.Disable(encryptedFileName,type, size, encryptedFilePath, statement);
            }

    }

    public static SystemFile doExportByName(String filename, User createdBy) throws SQLException {
        if(!Authorization.hasAdminPermission(createdBy)){
            return null;
        }
        ResultSet statement = null;
        String encryptedFileName= AES.encodeBase64(filename);

        try{
            String query =  "SELECT * FROM files WHERE name = '"+encryptedFileName+"'";
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!statement.next()){
            System.out.println("There is no file with this name.");
            return null;
        }
        statement.last();
        String fileName=AES.decodeBase64(statement.getString("name")),
                fileType=statement.getString("type"),
                filePath=AES.decodeBase64(statement.getString("path"));
        int fileSize= statement.getInt("size"),
                fileVersion=statement.getInt("version");


        return ( new SystemFile(fileName,fileType, fileSize, filePath, fileVersion));
    }
    public static ArrayList<SystemFile> doExportByCategory(String categoryName, String categoryType ) {
        if (categoryType.equals("size"))
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName))
                return FileClassifier.getFileSizeRanges().get(categoryName);
        if(categoryType.equals("type"))
            if(FileClassifier.getFileTypeRuler().containsKey(categoryName))
                return FileClassifier.getFileTypeRuler().get(categoryName);

        if(categoryType.equals("category"))
            if(FileClassifier.getFileCategoryRulers().containsKey(categoryName))
                return FileClassifier.getFileCategoryRulers().get(categoryName);

        System.out.println("Error in entering category name or category type .");
        return null;
    }

    public static void doDeleteByName(String filename, User createdBy) {
        if(!Authorization.hasAdminPermission(createdBy)){
            return;
        }
        String fileNameEncoded = AES.encodeBase64(filename);
        try {
            String query = "DELETE FROM files WHERE (name = "+fileNameEncoded + ");";
            MySQLDatabase.getInstance().insertDeleteQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("delete successfully");
    }
    public static void doDeleteByCategory(String categoryName, String categoryType) {
        if(categoryType.equals("size")){
            // karam

        }
        if(categoryType.equals("type")){
            // karam

        }
        if(categoryType.equals("category")){
            // karam

        }
    }

    public static void view() throws SQLException  {

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
            System.out.println("File " + i + " name: "+AES.decodeBase64(statement.getString("name"))+" \t path : "+AES.decodeBase64(statement.getString("path"))+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }

    public static void viewSizeRating(){
        // karam

    }
    public static void viewTypeRating(){
      // karam
    }
    public static void viewCategoryRating(){
        // karam

    }

    public static void doRollBack(String url, User createdBy) throws SQLException {
        if(!Authorization.hasAdminPermission(createdBy)){
            return;
        }
        VersionControl.Rollback(url, createdBy);
    }

}
