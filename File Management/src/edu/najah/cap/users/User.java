package edu.najah.cap.users;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.Security.Authorization;


import java.sql.ResultSet;
import java.sql.SQLException;


public class User {
private String email;
    private String password;
    private String name;
    private String role;
    private boolean loginStatus;

    public User(String email, String password){
        this.email = email;
        this.password = password;
        try{
            Authentication.logIn(email, password);
            role = Authentication.getUserRole();
            loginStatus = Authentication.getLogUserStatus();
            name = Authentication.getUserName();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public String getEmail() { return email; }

    public String getRole() { return role; }

    public boolean hasLogged() {return loginStatus;}

    public String getName() {
        return name;
    }
}
