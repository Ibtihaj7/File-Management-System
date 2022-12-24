package edu.najah.cap.Security;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Exceptions.AuthorizationExeption;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public abstract class Authentication {
   private static String userRole=null;
   private static String userName = null;
    private static  boolean logUserStatus=false;

    public static void logIn(String userEmail,String password)  throws Exception {
        String query = "select * from user WHERE email = ? AND password= ?";
        ResultSet resultQuery=null;
        try {
            resultQuery = MySQLDatabase.getInstance().executeQuery(query, Arrays.asList(userEmail,password));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(resultQuery.next()) {
            logUserStatus=true;
            userRole=resultQuery.getString("role");
            userName = resultQuery.getString("name");
            return;
        }
        throw new AuthorizationExeption("Email or password incorrect.");
    }

    public static String getUserRole() {
        return userRole;
    }
    public static boolean getLogUserStatus() {
        return logUserStatus;
    }
    public static String getUserName(){return userName;}
}
