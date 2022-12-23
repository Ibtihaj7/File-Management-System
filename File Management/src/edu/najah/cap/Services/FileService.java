package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.AES;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.VersionControl.VersionControl;
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
import java.util.Scanner;

public abstract class FileService {
    static Scanner input = new Scanner(System.in);


    public void doImport(String pathName, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        System.out.println(333333);
        if(!Authorization.hasAdminPermission(createdBy)||!Authorization.hasStaffPermission(createdBy)){
            System.out.println(44444);
            return;
        }
        File file = new File(pathName);
        String name = file.getName().split("\\.")[0];
        String type = file.getName().split("\\.")[1];
        int size = (int) file.length();
        String encryptedFileName= AES.encrypt(name);
        String encryptedFilePath= AES.encrypt(pathName);
        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name LIKE '" +  encryptedFileName + "(%' OR name = '" +  encryptedFileName + "' ";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet statement1 = statement;
        System.out.println(1);
        if (!statement1.next()) {
            System.out.println(2);
            try {
                query = "INSERT INTO files VALUES ('" +  encryptedFileName + "','" + type + "'," + size + ",null,'" + encryptedFilePath+ "',0);";
                MySQLDatabase.getInstance().insertDeleteQuery(query);
                System.out.println("The file has been imported successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
            if(!Authorization.hasAdminPermission(createdBy)){
                VersionControl.Enable(name,type,size,encryptedFilePath,statement);
                return;
            }

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

        VersionControl.Enable(name,type,size,encryptedFilePath,statement);

    }

    public SystemFile doExport(String filename, User createdBy) throws SQLException  , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException{
        if(!Authorization.hasAdminPermission(createdBy)){
            return null;
        }
        ResultSet statement = null;
        String encryptedFileName= AES.encrypt(filename);

        try{
            String query =  "SELECT * FROM files WHERE (name LIKE '"+encryptedFileName+"(%' OR name = '"+encryptedFileName+"')";
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!statement.next()){
            System.out.println("There is no file with this name or category.");
            return null;
        }
        statement.last();
        String fileName=AES.decrypt(statement.getString("name")),
                fileType=statement.getString("type"),
                fileCategory=statement.getString("category"),
                filePath=AES.decrypt(statement.getString("path"));
        int fileSize= statement.getInt("size"),
                fileVersion=statement.getInt("version");


        return ( new SystemFile(fileName,fileType, fileSize, filePath, fileVersion));
    }

    public void doDelete(String filename, User createdBy) {
        if(!Authorization.hasAdminPermission(createdBy)){
            return;
        }
        try {
            String query = "DELETE FROM files WHERE name = "+AES.encrypt(filename)+";";
            MySQLDatabase.getInstance().insertDeleteQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("delete successfully");
    }

    public void view() throws SQLException  , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
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
            System.out.println("File " + i + " name: "+AES.decrypt(statement.getString("name"))+" \t path : "+AES.decrypt(statement.getString("path"))+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }

    public void doRollBack(String url, User createdBy) throws SQLException {
        if(!Authorization.hasAdminPermission(createdBy)){
            return;
        }

        VersionControl.Rollback(url, createdBy);
    }

}
