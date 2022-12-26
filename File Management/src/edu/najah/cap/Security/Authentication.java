package edu.najah.cap.Security;

import edu.najah.cap.App;
import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Exceptions.AuthorizationExeption;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class Authentication {
   private static String userRole=null;
    private static  boolean logUserStatus=false;

    public static void logIn(String username,String password)  throws Exception {
        String query = "select * from user WHERE name = ? AND password= ?";
        ResultSet resultQuery=null;
       App.LOGGER.info("Received request to log in with username: " + username + " and password: " + password);
        try {
            resultQuery = MySQLDatabase.getInstance().executeQuery(query, Arrays.asList(username,password));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(resultQuery.next()) {
            App.LOGGER.info("Successfully logged in with username: " + username);
            logUserStatus=true;
            userRole=resultQuery.getString("role");
            return;
        }
        throw new AuthorizationExeption(Constant.AUTHENTICATION_EXCEPTION_MESSAGE);
    }

    public static String getUserRole() {
        App.LOGGER.debug("Returning user role: " + userRole);

        return userRole;
    }
    public static boolean getLogUserStatus() {
        App.LOGGER.info("Returning user login status: " + logUserStatus);
        return logUserStatus;
    }
}
