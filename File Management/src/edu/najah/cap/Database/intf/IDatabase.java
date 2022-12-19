package edu.najah.cap.Database.intf;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IDatabase {
    public ResultSet selectQuery(String query);
    public  void insertDeleteQuery(String query);
    public  void updateQuery(String query);

}
