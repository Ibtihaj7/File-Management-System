package edu.najah.cap.VersionControl;

import edu.najah.cap.Database.impl.MySQLDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class VersionControl {

    public static void Enable(String name, String type, int size, String path, ResultSet result) throws SQLException {
        String query;
        result.next();
        int newVersion = result.getInt("version");
        while (result.next()) {
            newVersion = result.getInt("version");
        }
        newVersion += 1;
        String nameWithVersion = name + "(" + newVersion + ")";
        query = "INSERT INTO files VALUES ('" + nameWithVersion + "','" + type + "'," + size + ",null,'" + path + "'," + newVersion + ");";
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
    public static void Rollback(String fileName) throws SQLException {
        ResultSet statement = null;
        String query;
        try {
            query = "SELECT * FROM files WHERE name LIKE '" + fileName + "(%' OR name = '" + fileName + "' ";
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

