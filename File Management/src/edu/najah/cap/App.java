package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Database.intf.IDatabase;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.users.User;
import edu.najah.cap.users.UserFactory;
import edu.najah.cap.users.UserRole;
import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        IDatabase database = MySQLDatabase.getInstance();
        Scanner in=new Scanner(System.in);
        System.out.println("Enter the user name.");
        String userName=in.nextLine();
        System.out.println("Enter the password.");
        String password=in.nextLine();
        try {
            Authentication.logIn(userName, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        User user = UserFactory.createUser(UserRole.valueOf(Authentication.getUserRole()));

    }
}