package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.ASEDecryptionEncryption;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.VersionControl.VersionControl;
import edu.najah.cap.classification.FileClassifier;
import edu.najah.cap.users.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public  class FileService {
    static Scanner input = new Scanner(System.in);
    public static void doImport(String pathName, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

//        if(Authorization.hasStaffPermission() || Authorization.hasStaffPermission()){
//            return;
//        }
        File file = new File(pathName);

        int index = pathName.lastIndexOf("/");
        String filenameWin = pathName.substring(index + 1);
        String name = filenameWin.split("\\.")[0];
        String type = file.getName().split("\\.")[1];
        int size = (int) file.length();
        String encryptedFileName= ASEDecryptionEncryption.Encrypt(name);
        String encryptedFilePath= ASEDecryptionEncryption.Encrypt(pathName);
        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name LIKE '" +  encryptedFileName + "(%' OR name = '" +  encryptedFileName + "' ";
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

        if(Authorization.hasAdminPermission()){
            System.out.println("Do you want to disable Version Control ?");
            System.out.print("your choice : ");
            String choice = input.next();

            if (choice.equals("no")) {
                VersionControl.Enable(name,type,size,encryptedFilePath,statement);
                return;
            }
            if(choice.equals("yes")){
                VersionControl.Disable(type,size,encryptedFilePath,statement);
                return;
            }
        }
        VersionControl.Enable(name,type,size,encryptedFilePath,statement);
    }

    public static SystemFile doExportByName(String filename, User createdBy) throws SQLException  , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException{
        ResultSet statement = null;
        String encryptedFileName= ASEDecryptionEncryption.Encrypt(filename);
        System.out.println("encryptedFileName   -> "+encryptedFileName);

        try{
            String query =  "SELECT * FROM files WHERE (name LIKE '"+encryptedFileName+"(%' OR name = '"+encryptedFileName+"')";
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!statement.next()){
            System.out.println("There is no file with this name.");
            return null;
        }

        statement.last();

        System.out.println("decript -> "+ASEDecryptionEncryption.decrypt(statement.getString("name")));

        String fileName=ASEDecryptionEncryption.decrypt(statement.getString("name")),
                fileType=statement.getString("type"),
                fileCategory=statement.getString("category"),
                filePath=ASEDecryptionEncryption.decrypt(statement.getString("path"));
        int fileSize= statement.getInt("size"),
                fileVersion=statement.getInt("version");

        return ( new SystemFile(fileName,fileType, fileSize, filePath, fileVersion));
    }

    public static ArrayList<SystemFile> doExportByCategory(String categoryName,String categoryType ) {
        if(categoryType.equals("size"))
            if(FileClassifier.getFileSizeRanges().containsKey(categoryName))
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
        try {
            if(!Authorization.hasAdminPermission()){
                return;
            }
            String query = "DELETE FROM files WHERE name = "+ASEDecryptionEncryption.Encrypt(filename)+";";
            MySQLDatabase.getInstance().insertDeleteQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("delete successfully");
    }
    public static void doDeleteByCategory(String categoryName,String categoryType) {
        if(categoryType.equals("size")){

        }
        if(categoryType.equals("type")){

        }
        if(categoryType.equals("category")){

        }

    }

    public static void view() throws SQLException  , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
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
            System.out.println("File " + i + " name: "+ASEDecryptionEncryption.decrypt(statement.getString("name"))+" \t path : "+ASEDecryptionEncryption.decrypt(statement.getString("path"))+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }
    public static void viewSizeRating(){

    }
    public static void viewTypeRating(){

    }
    public static void viewCategoryRating(){

    }


    public static void doRollBack(String url, User createdBy) throws SQLException {
        VersionControl.Rollback(url, createdBy);
    }

}
