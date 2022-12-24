package edu.najah.cap.VersionControl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Exceptions.AuthorizationExeption;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Security.Encryption;
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
        String query = "Update files SET type = ?, size = ?, path = ? WHERE name = ?";
        PreparedStatement preparedStatement = MySQLDatabase.getConnection().prepareStatement(query);
        preparedStatement.setString(1, type);
        preparedStatement.setInt(2, size);
        preparedStatement.setString(3,path);
        preparedStatement.setString(4,name);
        if (result.next()) {
                preparedStatement.executeUpdate();

            System.out.println("The file has been imported successfully");
        }
    }
    public static void Rollback(String fileName,int version, User createdBy) throws Exception {
        if (!Authorization.hasAdminPermission(createdBy)) {
            throw new AuthorizationExeption("Your type is not allowed to do an export a file.");
        }
        String fileNameEncrypted = Encryption.encodeBase64(fileName);
        String query = "DELETE FROM files WHERE name = ? AND version > ?";
        PreparedStatement preparedStatement = MySQLDatabase.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setString(1, fileNameEncrypted);
        preparedStatement.setInt(2, version);
        try {
            preparedStatement.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}

