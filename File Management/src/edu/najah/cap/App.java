package edu.najah.cap;

import edu.najah.cap.FileClassification.ClassificationType;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.users.User;
import edu.najah.cap.users.UserFactory;
import edu.najah.cap.users.UserRole;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
//        VersionControl.Rollback("karam");
//        SystemFile file = user.exportFile("karam");

//        user.importFile("/Users/ibtihaj/Desktop/file1.txt");
//        user.importFile("/Users/ibtihaj/Desktop/file2.pdf");
//        user.importFile("/Users/ibtihaj/Desktop/file3.pdf");
//        user.importFile("/Users/ibtihaj/Desktop/file4.txt");
//        user.importFile("/Users/ibtihaj/Desktop/file5.pdf");

        user.classifyFile("file1");
        user.classifyFile("file2");
        user.classifyFile("file3");
        user.classifyFile("file4");
        user.classifyFile("file5");

        user.PrintClassifiedFiles();

    }

}