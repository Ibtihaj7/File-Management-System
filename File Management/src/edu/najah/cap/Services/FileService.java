package edu.najah.cap.Services;

import edu.najah.cap.Classification.FileClassifier;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.AES;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.VersionControl.VersionControl;
import edu.najah.cap.Exceptions.*;
import edu.najah.cap.Users.User;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileService {
    static Scanner input = new Scanner(System.in);

    public static void doImport(String pathName, User createdBy) throws Exception {

        if(!Authorization.hasAdminPermission(createdBy) && !Authorization.hasStaffPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an import a file.");
        }
        File file = new File(pathName);
        int index = file.getName().lastIndexOf(".");
        String name = file.getName().substring(0,index);
        String type = file.getName().substring(index + 1);
        int size = (int) file.length();
        String encryptedFileName= AES.encodeBase64(name);
        String encryptedFilePath= AES.encodeBase64(pathName);

        if(size > 1000000){
            throw new MaxSizeExeption("The file size is too large for the system to store");
        }
        ResultSet statement = null;
        String query = "SELECT * FROM files WHERE name = '" +  encryptedFileName + "' ";
        try {
            statement = MySQLDatabase.getInstance().selectQuery(query);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        ResultSet statement1 = statement;
        if (!statement1.next()) {
            query = "INSERT INTO files VALUES ('" +  encryptedFileName + "','" + type + "'," + size + ",'" + encryptedFilePath+ "',0);";
            try {
                MySQLDatabase.getInstance().insertDeleteQuery(query);
                System.out.println("The file has been imported successfully");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("The file has been imported successfully");
            return;
        }

        if(Authorization.hasStaffPermission(createdBy)){
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
            return;
        }
        throw new InvalidInputExeption("Bad entry choice.");
    }

    public static SystemFile doExportByName(String filename, User createdBy) throws Exception {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        ResultSet statement = null;
        String encryptedFileName= AES.encodeBase64(filename);
        String query =  "SELECT * FROM files WHERE name = '"+encryptedFileName+"'";
        try{
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(!statement.next()){
            throw new FileNotFoundExeption("There is no file with this name.");
        }
        statement.last();
        String fileName=AES.decodeBase64(statement.getString("name")),
                fileType=statement.getString("type"),
                filePath=AES.decodeBase64(statement.getString("path"));
        int fileSize= statement.getInt("size"),
                fileVersion=statement.getInt("version");

        return new SystemFile(fileName,fileType, fileSize, filePath, fileVersion);
    }
    public static ArrayList<SystemFile> doExportByCategory(String categoryName, String categoryType )throws Exception{
        if (categoryType.equals("size"))
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName))
                return FileClassifier.getFileSizeRanges().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");

        if(categoryType.equals("type"))
            if(FileClassifier.getFileTypeRuler().containsKey(categoryName))
                return FileClassifier.getFileTypeRuler().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");

        if(categoryType.equals("category"))
            if(FileClassifier.getFileCategoryRulers().containsKey(categoryName))
                return FileClassifier.getFileCategoryRulers().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");

        throw new CategoryNotFoundExeption("'"+categoryType+"' category not exist.");
    }

    public static void doDeleteByName(String filename, User createdBy)throws Exception {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        String fileNameEncoded = AES.encodeBase64(filename);
        String query = "DELETE FROM files WHERE name = '"+fileNameEncoded + "'";
        try {
            MySQLDatabase.getInstance().insertDeleteQuery(query);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("File deleted successfully");
    }
    public static void doDeleteByCategory(String categoryName, String categoryType) throws Exception{
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

    public static void view() throws Exception {
        String query="select * from files";
        ResultSet statement = null;
        try{
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
           System.err.println(e.getMessage());
        }
        if(!statement.next())  throw new FileNotFoundExeption("There is no file in the system.");

        for (int i = 1; statement.next(); i++) {
            System.out.println("File " + i + " name: "+AES.decodeBase64(statement.getString("name"))+" \t path : "+AES.decodeBase64(statement.getString("path"))+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }
    public static void doRollBack(String fileName, int version, User createdBy) throws Exception {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        VersionControl.Rollback(fileName,version, createdBy);
    }
}
