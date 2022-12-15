package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Database.intf.IDatabase;
import edu.najah.cap.FileRepository.impl.NormalImport;
import edu.najah.cap.users.User;
import edu.najah.cap.users.UserFactory;
import edu.najah.cap.users.UserType;

public class App {
    public static void main(String[] args) {
        IDatabase database = MySQLDatabase.getInstance();
        User admin = UserFactory.createUser(UserType.ADMIN);
    }
}