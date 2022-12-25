package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Users.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class App {
    public static final Logger LOGGER = Logger.getLogger(App.class.getName());
    public static void main(String[] args) {
        BasicConfigurator.configure();
        FileRepository fileRepository = new FileRepository();
        User user1 = new User("ibtihaj","ibtihaj");
        User user2 = new User("abrar","abrar");
        User user3 = new User("karam","karam");

        SystemFile file1,file2,file3,file4 ;
        ArrayList<SystemFile> files;

//        try {
//            fileRepository.importFile("/Users/ibtihaj/Desktop/words.txt",user1);
//            fileRepository.importFile("/Users/ibtihaj/Desktop/words.pdf",user1);
//            fileRepository.importFile("/Users/ibtihaj/Desktop/sss.pdf",user1);
//            fileRepository.importFile("/Users/ibtihaj/Desktop/rrr.txt",user1);
//            fileRepository.importFile("/Users/ibtihaj/Desktop/fff.pdf",user1);
//            fileRepository.importFile("/Users/ibtihaj/Desktop/hhh.pdf",user1);
//        }catch (Exception e){
//            System.err.println(e);
//        }


        System.out.println("----------------------------------------");
        try {
            files = fileRepository.exportFileByCategory("type", "txt", user1);
        }catch (Exception e){
            System.err.println(e);
        }

        fileRepository.viewAllFiles();
        System.out.println("----------------------------------------");
        fileRepository.viewFileByClassification("type","txt");

        System.out.println("----------------------------------------");

        try {
            file1=fileRepository.exportFileByName("words","txt",user1);
            fileRepository.classifyFileByCategory(file1,"bbbb",user1);

            file2=fileRepository.exportFileByName("rrr","txt",user1);
            fileRepository.classifyFileByCategory(file2,"bbbb",user1);

            file3=fileRepository.exportFileByName("fff","pdf",user1);
            fileRepository.classifyFileByCategory(file3,"bbbb",user1);

            file4=fileRepository.exportFileByName("hhh","pdf",user1);
            fileRepository.classifyFileByCategory(file4,"bbbb",user1);
        }catch (Exception e){
            System.err.println(e);
        }
        System.out.println("----------------------------------------");

        fileRepository.viewFileByClassification("new category","bbbb");
        MySQLDatabase.disconnect();
    }

}