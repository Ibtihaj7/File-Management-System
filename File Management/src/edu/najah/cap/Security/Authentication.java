package edu.najah.cap.Security;

import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Exceptions.AuthorizationExeption;

import java.sql.ResultSet;
import java.util.Arrays;

public abstract class Authentication {
   private static String userRole=null;
    private static  boolean logUserStatus=false;

    public static void logIn(String username,String password)  throws Exception {
        String query = "select * from user WHERE name = ? AND password= ?";
        ResultSet resultQuery=null;
        try {
            resultQuery = MySQLDatabase.getInstance().executeQuery(query, Arrays.asList(username,password));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(resultQuery.next()) {
            logUserStatus=true;
            userRole=resultQuery.getString("role");
            return;
        }
        throw new AuthorizationExeption(Constant.AUTHENTICATION_EXCEPTION_MESSAGE);
    }

    public static String getUserRole() {
        return userRole;
    }
    public static boolean getLogUserStatus() {
        return logUserStatus;
    }
}
