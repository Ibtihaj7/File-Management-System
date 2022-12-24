package edu.najah.cap.Database.intf;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Database {
    Connection getConnection() throws SQLException;
    public ResultSet executeQuery(String sql, List<Object> parameters) throws SQLException;
    public void executeStatement(String sql, List<Object> parameters) throws SQLException;
}
