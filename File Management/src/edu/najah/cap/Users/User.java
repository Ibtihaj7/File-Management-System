package edu.najah.cap.Users;

import edu.najah.cap.Security.Authentication;


public class User {
    public String getUsername() {
        return username;
    }

    private final String username;
    private final String password;
    private String role;
    private boolean loginStatus;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        try{
            Authentication.logIn(username, password);
            role = Authentication.getUserRole();
            loginStatus = Authentication.getLogUserStatus();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public String getRole() { return role; }
    public boolean hasLogged() {return loginStatus;}
}
