package edu.najah.cap.Database.impl;

import edu.najah.cap.Database.intf.IDatabase;

import java.sql.*;

public class MySQLDatabase implements IDatabase{

    private static MySQLDatabase mySQLDatabase = null;
    private static Connection connection;
    private static Statement statement;
    private MySQLDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/File_Management", "root", "password");
        statement = connection.createStatement();
    }
    public static synchronized MySQLDatabase getInstance(){
        if(mySQLDatabase == null){
            try{
                mySQLDatabase = new MySQLDatabase();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mySQLDatabase;
    }

@Override
    public synchronized ResultSet execute(String query) {
        ResultSet result = null;
        try{
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
