package edu.najah.cap.Security;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class Authentication {
   private static String userRole=null;

    public static void logIn(String userEmail,String password)  throws SQLException {
        String query;
        ResultSet resultQuery=null;
        try {
            query = "select * from user WHERE email = '" + userEmail + "' AND password= '" + password + "'";
            resultQuery=MySQLDatabase.getInstance().selectQuery(query);
            System.out.println("Login completed successfully.");
        }catch (Exception e){
         e.printStackTrace();
        }

        if(resultQuery.next()) {
            userRole=resultQuery.getString("role");
        }
    }

    public static String getUserRole() {
        return userRole;
    }
}
