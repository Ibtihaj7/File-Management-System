package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Security.Decryption;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.VersionControl.VersionControl;
import edu.najah.cap.Exceptions.*;
import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public abstract class FileService {
    static Scanner input = new Scanner(System.in);

    public static void doImport(String pathName, User createdBy) throws Exception {

        if(!Authorization.hasAdminPermission(createdBy) && !Authorization.hasStaffPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an import a file.");
        }
        File file = new File(pathName);
        int lastIndex = file.getName().lastIndexOf(".");
        int firstIndex = file.getName().lastIndexOf("\\");
        String name = file.getName().substring(firstIndex+1,lastIndex);
        String type = file.getName().substring(lastIndex + 1);
        int size = (int) file.length();
        String encryptedFileName= Encryption.encodeBase64(name);
        String encryptedFilePath= Encryption.encodeBase64(pathName);

        if(size > 1000000){
            throw new MaxSizeExeption("The file size is too large for the system to store");
        }
        ResultSet statement = null;
        String query = "SELECT * FROM files WHERE name = ? AND type = ?";
        try {
           statement = MySQLDatabase.getInstance().executeQuery(query, Arrays.asList(encryptedFileName,type));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (!statement.first()) {
            query = "INSERT INTO files VALUES (?, ?, ?, ?, ?)";
            try {
                MySQLDatabase.getInstance().executeStatement(query,Arrays.asList(encryptedFileName,type,size,encryptedFilePath,0));
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

    public static SystemFile doExportByName(String filename,String type, User createdBy) throws Exception {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        ResultSet statement = null;
        String encryptedFileName= Encryption.encodeBase64(filename);
        String query =  "SELECT * FROM files WHERE name = ? AND type = ?";
        try{
            statement = MySQLDatabase.getInstance().executeQuery(query,Arrays.asList(encryptedFileName,type));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(!statement.next()){
            throw new FileNotFoundExeption("There is no file with this name.");
        }
        statement.last();
        String fileName= Decryption.decodeBase64(statement.getString("name")),
                fileType=statement.getString("type"),
                filePath=Decryption.decodeBase64(statement.getString("path"));
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
        String fileNameEncoded =Encryption.encodeBase64(filename);
        String query = "DELETE FROM files WHERE name = ?";
        PreparedStatement preparedStatement = MySQLDatabase.getInstance().getConnection().prepareStatement(query);
        preparedStatement.setString(1, fileNameEncoded);
        try {
            preparedStatement.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("File deleted successfully");
    }
    public static void doDeleteByCategory(String categoryName, String categoryType, User createdBy) throws Exception{
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        if(categoryType.equals("size")){
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName)){
                FileClassifier.getFileSizeRanges().get(categoryName).forEach(file->{
                    try {
                        doDeleteByName(file.getName(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
        if(categoryType.equals("type")){
            if (FileClassifier.getFileTypeRuler().containsKey(categoryName)){
                FileClassifier.getFileTypeRuler().get(categoryName).forEach(file->{
                    try {
                        doDeleteByName(file.getName(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
        if(categoryType.equals("category")){
            if (FileClassifier.getFileCategoryRulers().containsKey(categoryName)){
                FileClassifier.getFileCategoryRulers().get(categoryName).forEach(file->{
                    try {
                        doDeleteByName(file.getName(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
    }

    public static void view() throws Exception {
        String query="select name,type,size,path,MAX(version) AS version\n" +
                " from files\n" +
                " GROUP BY name";
        ResultSet statement = null;
        try{
          statement = MySQLDatabase.getInstance().executeQuery(query,null);
        }catch (Exception e){
           System.err.println(e.getMessage());
        }
        if(!statement.first())  throw new FileNotFoundExeption("There is no file in the system.");
        int i = 1;
        System.out.println("File " + i++ + " name: "+Decryption.decodeBase64(statement.getString("name"))+" \t path : "+Decryption.decodeBase64(statement.getString("path"))+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t version : "+statement.getInt("version"));
        while(statement.next()){
            System.out.println("File " + i++ + " name: "+Decryption.decodeBase64(statement.getString("name"))+" \t path : "+Decryption.decodeBase64(statement.getString("path"))+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t version : "+statement.getInt("version"));
        }
        System.out.println();
    }
    public static void doRollBack(String fileName, int version, User createdBy) throws Exception {
        VersionControl.Rollback(fileName,version, createdBy);
    }

    public static void viewFilesByCategory(String categoryName) {
        boolean isCategoryNameSize=categoryName.equals("size");
        boolean isCategoryNameType=categoryName.equals("type");
        boolean isCategoryNameCustom=categoryName.equals("custom");
        if(isCategoryNameSize) {
            viewFilesCategorizedBySize();
        }
        if(isCategoryNameType) {
            viewFilesCategorizedByType();
        }
        if(isCategoryNameCustom)
          viewFilesWithCustomCategory();
    }

    private static void viewFilesWithCustomCategory() {
        int filesWithCategoryNumber=0;

        for (ArrayList<SystemFile>files:FileClassifier.getFileCategoryRulers().values()) {
            System.out.println(files.get(filesWithCategoryNumber).toString());
            filesWithCategoryNumber++;
        }

    }

    private static void viewFilesCategorizedByType() {
        int filesWithCategoryNumber=0;
        for (ArrayList<SystemFile>files:FileClassifier.getFileTypeRuler().values()) {
            System.out.println(files.get(filesWithCategoryNumber).toString());
            filesWithCategoryNumber++;
        }

    }

    private static void viewFilesCategorizedBySize() {
        int filesWithCategoryNumber=0;

        for (ArrayList<SystemFile>files:FileClassifier.getFileSizeRanges().values()) {
            System.out.println( files.get(filesWithCategoryNumber).toString());
            filesWithCategoryNumber++;
        }
    }
}
