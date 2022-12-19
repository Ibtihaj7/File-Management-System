package edu.najah.cap;

import edu.najah.cap.users.User;
import edu.najah.cap.users.UserFactory;
import edu.najah.cap.users.UserRole;

import java.io.File;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
//        IDatabase database = MySQLDatabase.getInstance();
//        Scanner in=new Scanner(System.in);
//        System.out.println("Enter the user name.");
//        String userName=in.nextLine();
//        System.out.println("Enter the password.");
//        String password=in.nextLine();
//        try {
//            Authentication.logIn(userName, password);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        User user = UserFactory.createUser(UserRole.valueOf(Authentication.getUserRole()));
        User user = UserFactory.createUser(UserRole.ADMIN);
        File file = user.exportFile("ibtihaj","category1");
        System.out.println(file);
        user.importFile("/Users/ibtihaj/Desktop/words.txt");
    }
}