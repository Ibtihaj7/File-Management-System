package edu.najah.cap.Database.intf;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IDatabase {
    public ResultSet execute(String query);
}
