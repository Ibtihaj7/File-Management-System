package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Database.intf.IDatabase;
import edu.najah.cap.FileRepository.intf.Import;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class NormalImport implements Import {
    Scanner input = new Scanner(System.in);
    @Override
    public void importFile(String pathName) throws SQLException {
      File file = new File(pathName);
      String name = file.getName().split("\\.")[0];
      String type = file.getName().split("\\.")[1];
      int size = (int) file.length();
        ResultSet statement = null;
        try {
            statement = MySQLDatabase.getInstance().execute("SELECT * FROM files WHERE name LIKE '"+name+"(' OR name = '"+name+"' ");

        }catch (Exception e){
            e.printStackTrace();
        }

        if(statement.next() == false){
            System.out.println("success");
            try {
                String query = "INSERT INTO files VALUES ('" + name + "','" + type + "'," + size + ",null,0);";
                MySQLDatabase.getInstance().execute(query);
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
            System.out.print("Bad number, try agin enter valid number : ");
            choice = input.nextInt();
        }

        if(choice == 1){
            int newVersion = statement.getInt("version");
            while(statement.next()) {
                newVersion = statement.getInt("version");
                statement.next();
            }
            newVersion+=1;
            MySQLDatabase.getInstance().execute("INSERT INTO files VALUES ('" + name+"("+newVersion+")','" + type + "'," + size + "," + newVersion + ")" );
            System.out.println("The file has been imported successfully");
            return;
        }
        MySQLDatabase.getInstance().execute("UPDATE files\n" +
                "SET\n" +
                "`type` = "+type+",\n" +
                "`size` = "+size+"\n" +
                "WHERE `name` = "+statement.getString("name")+";\n");
        System.out.println("The file has been imported successfully");
    }
}
