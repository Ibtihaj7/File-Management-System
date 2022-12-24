package edu.najah.cap.VersionControl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Exceptions.AuthorizationExeption;
import edu.najah.cap.Security.AES;
import edu.najah.cap.Exceptions.FileNotFoundExeption;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class VersionControl {

    public static void Enable(String name, String type, int size, String path, ResultSet result) throws SQLException {
        String query;
        int newVersion = result.getInt("version");
        while (result.next()) {
            newVersion = result.getInt("version");
        }
        newVersion += 1;
        query = "INSERT INTO files VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = MySQLDatabase.getConnection().prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, type);
        preparedStatement.setInt(3,size);
        preparedStatement.setString(4,path);
        preparedStatement.setInt(5, newVersion);
        try {
            preparedStatement.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("The file has been imported successfully");
    }
    public static void Disable(String name,String type, int size, String path, ResultSet result) throws SQLException {

        if (result.next()) {
            MySQLDatabase.getInstance().updateQuery("UPDATE files\n" +
                    "SET\n" +
                    "`type` = " + type + ",\n" +
                    "`size` = " + size + ",\n" +
                    "`path` = " + path + "\n" +
                    "WHERE `name` = " + name + ";\n");
            System.out.println("The file has been imported successfully");
        }
    }
    public static void Rollback(String fileName,int version, User createdBy) throws Exception {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name = '" +AES.encodeBase64(fileName) + "' ";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if(!statement.next())
            throw new FileNotFoundExeption( "The file name you entered does not exist");

        statement.last();
        query = "DELETE FROM files WHERE name = ? AND version > ?";
        PreparedStatement preparedStatement = MySQLDatabase.getConnection().prepareStatement(query);
        preparedStatement.setString(1, statement.getString("name"));
        preparedStatement.setInt(2, version);
        try {
            preparedStatement.execute();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        }
}

