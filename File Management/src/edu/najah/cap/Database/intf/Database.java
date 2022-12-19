package edu.najah.cap.Database.intf;


import java.sql.ResultSet;

public interface Database {
     ResultSet selectQuery(String query);
      void insertDeleteQuery(String query);
      void updateQuery(String query);

}
