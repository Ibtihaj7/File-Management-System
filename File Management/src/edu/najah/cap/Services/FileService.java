package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.VersionControl.VersionControl;
import edu.najah.cap.users.User;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class FileService {
    static Scanner input = new Scanner(System.in);
    public void doImport(String pathName, User createdBy) throws SQLException {

//        if(Authorization.hasStaffPermission() || Authorization.hasStaffPermission()){
//            return;
//        }
        File file = new File(pathName);
        String name = file.getName().split("\\.")[0];
        String type = file.getName().split("\\.")[1];
        int size = (int) file.length();

        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name LIKE '" + name + "(%' OR name = '" + name + "' ";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet statement1 = statement;

        if (!statement1.next()) {
            try {
                query = "INSERT INTO files VALUES ('" + name + "','" + type + "'," + size + ",null,'" + pathName + "',0);";
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
                VersionControl.Enable(name,type,size,pathName,statement);
                return;
            }
            if(choice.equals("yes")){
                VersionControl.Disable(type,size,pathName,statement);
                return;
            }
        }
        VersionControl.Enable(name,type,size,pathName,statement);
    }

    public SystemFile doExport(String filename, User createdBy) throws SQLException {
        ResultSet statement = null;
        try{
            String query =  "SELECT * FROM files WHERE (name LIKE '"+filename+"(%' OR name = '"+filename+"')";
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

    public void doDelete(String filename, User createdBy) {
        try {
            if(!Authorization.hasAdminPermission()){
                return;
            }
            String query = "DELETE FROM files WHERE name = "+filename+";";
            MySQLDatabase.getInstance().insertDeleteQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("delete successfully");
    }

    public void view() throws SQLException  {
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
            System.out.println("File " + i + " name: "+statement.getString("name")+" \t path : "+statement.getString("path")+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }

    public void doRollBack(String url, User createdBy) throws SQLException {
        VersionControl.Rollback(url, createdBy);
    }

}
