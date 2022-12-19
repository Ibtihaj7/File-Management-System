package edu.najah.cap.Database.impl;

import edu.najah.cap.Database.intf.IDatabase;

import java.sql.*;

public class MySQLDatabase implements IDatabase{
    private static final String ROOT = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/File_Management";
    private static MySQLDatabase mySQLDatabase = null;
    private static Connection connection;
    private static Statement statement;
    private MySQLDatabase()  {
        try {
            connection = DriverManager.getConnection(URL, ROOT, PASSWORD);
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static synchronized MySQLDatabase getInstance(){
        if(mySQLDatabase == null){
            try{
                mySQLDatabase = new MySQLDatabase();
                statement = connection.createStatement();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mySQLDatabase;
    }

    @Override
    public synchronized ResultSet selectQuery(String query) {
        ResultSet result = null;
        try{
            result = statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public synchronized void insertDeleteQuery(String query) {
        try{
            statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void updateQuery(String query) {
        try{
            connection.prepareStatement(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
