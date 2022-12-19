package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.ImportBehaviour;
import edu.najah.cap.Security.Authorization;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Import implements ImportBehaviour {
    Scanner input = new Scanner(System.in);
    @Override
    public void importFile(String pathName) throws SQLException {

        if(!Authorization.hasAdminPermission()&&!Authorization.hasStaffPermission()){
            return;
        }
      File file = new File(pathName);
      String name = file.getName().split("\\.")[0];
      String type = file.getName().split("\\.")[1];
      int size = (int) file.length();
        ResultSet statement = null;
        try {
            statement = MySQLDatabase.getInstance().selectQuery("SELECT * FROM files WHERE name LIKE '"+name+"(' OR name = '"+name+"' ");

        }catch (Exception e){
            e.printStackTrace();
        }

        if(!statement.next()){
            try {
                String query = "INSERT INTO files VALUES ('" + name + "','" + type + "'," + size + ",null,'"+pathName+"',0);";
                MySQLDatabase.getInstance().insertDeleteQuery(query);
                System.out.println("The file has been imported successfully");
            }catch (Exception e){
                e.printStackTrace();
            }
           return;
        }
            System.out.println("Enter 1 if you want to add new version to the file.");
            System.out.println("Enter 2 if you want to replace the current file version.");
            System.out.print("your choice : ");
            int choice = input.nextInt();
            while (choice != 1 && choice != 2) {
                System.out.print("Bad number, try again enter valid number : ");
                choice = input.nextInt();
            }

            if (choice == 1) {
                int newVersion = statement.getInt("version");
                while (statement.next()) {
                    newVersion = statement.getInt("version");
                    statement.next();
                }
                newVersion += 1;
                MySQLDatabase.getInstance().insertDeleteQuery("INSERT INTO files VALUES ('" + name + "(" + newVersion + ")','" + type + "'," + size + ",'" + pathName + "'," + newVersion + ")");
                System.out.println("The file has been imported successfully");
                return;
            }
            if(Authorization.hasAdminPermission()) {
                MySQLDatabase.getInstance().updateQuery("UPDATE files\n" +
                        "SET\n" +
                        "`type` = " + type + ",\n" +
                        "`size` = " + size + "\n" +
                        "`path` = " + pathName + "\n" +
                        "WHERE `name` = " + statement.getString("name") + ";\n");
                System.out.println("The file has been imported successfully");
            }
            else{
                System.out.println("You don't have access for this");
            }
        }
    }

