package edu.najah.cap.Database.intf;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Database {
    ResultSet executeQuery(String sql, List<Object> parameters) throws SQLException;
    void executeStatement(String sql, List<Object> parameters) throws SQLException;
}
