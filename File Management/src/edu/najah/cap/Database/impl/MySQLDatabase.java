package edu.najah.cap.Database.impl;


import edu.najah.cap.App;
import edu.najah.cap.Database.intf.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabase implements Database {
    private static final String ROOT = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/File_Management";
    private static MySQLDatabase instance = null;
    private static Connection connection;

    private MySQLDatabase() {
        try {
            connection = DriverManager.getConnection(URL, ROOT, PASSWORD);
            App.LOGGER.info("make new connection by "+ URL+" url.");
        } catch (SQLException e) {
            App.LOGGER.error("connection not work by"+ URL+" url.");
            System.out.println(e.getMessage());
        }
        App.LOGGER.debug("Connection successfully");
    }

    public static synchronized MySQLDatabase getInstance() {
        if (instance == null) {
            try {
                instance = new MySQLDatabase();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return instance;
    }

    @Override
    public ResultSet executeQuery(String sql, List<Object> parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        for (int i = 0; i < parameters.size(); i++) {
            preparedStatement.setObject(i + 1, parameters.get(i));
        }
        return preparedStatement.executeQuery();
    }

    @Override
    public void executeStatement(String sql, List<Object> parameters) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        for (int i = 0; i < parameters.size(); i++) {
            preparedStatement.setObject(i + 1, parameters.get(i));
        }
        preparedStatement.execute();
    }
    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                App.LOGGER.info("close the connection.");
            } catch (SQLException e) {
                App.LOGGER.info("error in close the connection.");
                System.err.println(e);
            }
        }
    }
}
