package edu.najah.cap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/File_Management", "root", "password");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM files");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}