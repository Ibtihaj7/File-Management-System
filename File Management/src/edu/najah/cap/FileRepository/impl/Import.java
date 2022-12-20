package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.ImportBehaviour;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.VersionControl.VersionControl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Import implements ImportBehaviour {

    Scanner input = new Scanner(System.in);
    @Override
    public void importFile(String pathName) throws SQLException {
        if(Authorization.hasStaffPermission() || Authorization.hasStaffPermission()){
             return;
        }
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
}

