package edu.najah.cap.VersionControl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Security.AES;
import edu.najah.cap.users.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class VersionControl {

    public static void Enable(String name, String type, int size, String path, ResultSet result) throws SQLException, NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
        String query;
        result.next();
        int newVersion = result.getInt("version");
        while (result.next()) {
            newVersion = result.getInt("version");
        }
        newVersion += 1;
        String nameWithVersion = name + "(" + newVersion + ")";
     AES.encrypt(nameWithVersion);
        query = "INSERT INTO files VALUES ('" +  AES.encrypt(nameWithVersion)+ "','" + type + "'," + size + ",null,'" + path+ "'," + newVersion + ");";
        MySQLDatabase.getInstance().insertDeleteQuery(query);
        System.out.println("The file has been imported successfully");
    }
    public static void Disable(String type, int size, String path, ResultSet result) throws SQLException {

        if (result.next()) {
            MySQLDatabase.getInstance().updateQuery("UPDATE files\n" +
                    "SET\n" +
                    "`type` = " + type + ",\n" +
                    "`size` = " + size + ",\n" +
                    "`path` = " + path + "\n" +
                    "WHERE `name` = " + result.getString("name") + ";\n");
            System.out.println("The file has been imported successfully");
        }
    }
    public static void Rollback(String fileName, User createdBy) throws SQLException {
        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name LIKE '" + AES.encrypt(fileName) + "(%' OR name = '" +AES.encrypt(fileName) + "' ";
            statement = MySQLDatabase.getInstance().selectQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        statement.last();
        query = "DELETE FROM files WHERE name = ?";
        PreparedStatement preparedStatement = MySQLDatabase.getConnection().prepareStatement(query);
        preparedStatement.setString(1, statement.getString("name"));
        preparedStatement.execute();
        }
    }

