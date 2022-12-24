package edu.najah.cap.Database.impl;

import edu.najah.cap.Database.intf.Database;

import java.sql.*;

public class MySQLDatabase implements Database {
    private static final String ROOT = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/File_Management";
    private static MySQLDatabase mySQLDatabase = null;
    private static Connection connection;
    private static Statement statement;
    private static int counter = 0;

    private MySQLDatabase()  {
        try {
            counter++;
            connection = DriverManager.getConnection(URL, ROOT, PASSWORD);
            statement = connection.createStatement();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public static synchronized MySQLDatabase getInstance(){
        if(mySQLDatabase == null){
            try{
                mySQLDatabase = new MySQLDatabase();
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }catch (Exception e){
                System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
        }
        return result;
    }
    @Override
    public synchronized void insertDeleteQuery(String query) {
        try{
            statement.executeUpdate(query);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public synchronized void updateQuery(String query) {
        try{
            connection.prepareStatement(query);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public static Connection getConnection() {
        return connection;
    }

    public static int getCounter() {
        return counter;
    }
}
