package edu.najah.cap.FileRepository.impl;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Database.intf.IDatabase;
import edu.najah.cap.FileRepository.intf.Import;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.users.UserRole;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class NormalImport implements Import {
    Scanner input = new Scanner(System.in);
    @Override
    public void importFile(final String pathName) throws SQLException {
        String query = null;
        File file = new File(pathName);
        String name = file.getName().split("\\.")[0];
        String type = file.getName().split("\\.")[1];
        int size = (int) file.length();
        ResultSet statement = null;
        try {
            query = "SELECT * FROM files WHERE name LIKE '"+name+"(' OR name = '"+name+"' ";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        }catch (Exception e){
             System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        ResultSet statement1 = statement;
        if(!statement1.next()){
            try {
                query = "INSERT INTO files VALUES ('" + name + "','" + type + "'," + size + ",null,'"+pathName+"',0);";
                MySQLDatabase.getInstance().insertDeleteQuery(query);
                System.out.println("The file has been imported successfully");
            }catch (Exception e){
                e.printStackTrace();
            }
           return;
        }


        System.out.println("Enter 1 if you want to add new version to the file.");
        System.out.println("Enter 2 if you want to replace the file version.");
        System.out.print("your choice : ");
        int choice = input.nextInt();
        while(choice!= 1 && choice!= 2 ){
            System.out.print("Bad number, try again enter valid number : ");
            choice = input.nextInt();
        }

        if(choice == 1){
            int newVersion = statement.getInt("version");
            while(statement.next()) {
                newVersion = statement.getInt("version");
                statement.next();
            }
            newVersion+=1;
            System.out.println("newVersion  =  "+newVersion);
            String newNameWithVersion = name +"("+newVersion+")";
            query = "INSERT INTO files VALUES ('" + newNameWithVersion + "','" + type + "'," + size + ",null,'"+pathName+"',"+newVersion+");";
            MySQLDatabase.getInstance().insertDeleteQuery(query );
            System.out.println("The file has been imported successfully");
            return;
        }

        query=" UPDATE files\n" +
                "SET\n" +
                "`type` = "+type+",\n" +
                "`size` = "+size+",\n" +
                "`path` = '"+pathName+"'\n" +
                "WHERE `name` = '"+statement.getString("name")+"';\n";
        MySQLDatabase.getInstance().updateQuery(query);
        System.out.println("The file has been imported successfully");
    }
}
