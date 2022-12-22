package edu.najah.cap;

import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.users.User;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
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
        User user =new User();
//        user.importFile("/Users/ibtihaj/Desktop/words.txt",new User());
//        user.importFile("/Users/ibtihaj/Desktop/file.pdf",new User());
//        user.importFile("/Users/ibtihaj/Desktop/sss.txt",new User());
//        user.importFile("/Users/ibtihaj/Desktop/ddd.txt",new User());
//        user.importFile("/Users/ibtihaj/Desktop/fff.txt",new User());
//
//        System.out.println("_________________");
//        SystemFile file = user.exportFileByName("words",new User());
//        ArrayList<SystemFile> file2 = user.exportFileByCategory("0","size",new User());

//        System.out.println(file);
//        System.out.println(file2);
        FileRepository fileRepository = new FileRepository("karam");
        fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam.txt", user);
        SystemFile file = fileRepository.exportFileByName("karam",user);
        System.out.println(file);

    }

}