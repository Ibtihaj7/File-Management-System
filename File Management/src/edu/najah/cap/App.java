package edu.najah.cap;

import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.VersionControl.VersionControl;
import edu.najah.cap.users.User;
import edu.najah.cap.users.UserFactory;
import edu.najah.cap.users.UserRole;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
//        IDatabase database = MySQLDatabase.getInstance();
        Scanner in=new Scanner(System.in);
        System.out.print("Enter the user name : ");
        String userName=in.nextLine();
        System.out.print("Enter the password : ");
        String password=in.nextLine();
        try {
            Authentication.logIn(userName, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        User user = UserFactory.createUser(UserRole.valueOf(Authentication.getUserRole()));
        VersionControl.Rollback("karam");
        SystemFile file = user.exportFile("karam");
        System.out.println(file.getName());
    }
}