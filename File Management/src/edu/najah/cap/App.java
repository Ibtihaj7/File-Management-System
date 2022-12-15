package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Database.intf.IDatabase;
import edu.najah.cap.FileRepository.impl.NormalImport;
import edu.najah.cap.users.User;
import edu.najah.cap.users.UserFactory;
import edu.najah.cap.users.UserType;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        User admin = UserFactory.createUser(UserType.ADMIN );
        admin.importFile("/Users/ibtihaj/Desktop/words.txt");
        System.out.println("vvvvv");
    }
}