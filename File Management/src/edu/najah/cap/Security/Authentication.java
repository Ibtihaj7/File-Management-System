package edu.najah.cap.Security;

import edu.najah.cap.Database.impl.MySQLDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class Authentication {
   private static String userRole;
    public static void logIn(String userName,String password)  throws SQLException {
        String query=null;
        ResultSet resultQuery=null;
        try {
            query = "select * from user WHERE name = '" + userName + "' AND password= '" + password + "'";
            resultQuery=MySQLDatabase.getInstance().execute(query);
            System.out.println("Login completed successfully.");
        }catch (Exception e){
         e.printStackTrace();
        }

        if(resultQuery.next()) {

        userRole=resultQuery.getString("role");
            System.out.println(userRole);
        }

    }

    public static String getUserRole() {
        return userRole;
    }
}
