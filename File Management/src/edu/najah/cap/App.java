package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Users.User;
import edu.najah.cap.VersionControl.impl.Disable;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class App {
    public static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        BasicConfigurator.configure();
        FileRepository fileRepository = new FileRepository();
        User user1 = new User("karam", "karam@123");
        User user2 = new User("ibtihaj", "ibtihaj@123");
        User user3 = new User("abrar", "abrar@123");

        SystemFile file1, file2, file3, file4;
        ArrayList<SystemFile> files;

        try {
            fileRepository.setVersionControl(new Disable(), user1);
            fileRepository.importFile("/Users/HP/Desktop/words.txt",user1);
      fileRepository.importFile("/Users/HP/Desktop/rrr.txt",user1);
        fileRepository.importFile("/Users/HP/Desktop/fff.pdf",user1);
          fileRepository.importFile("/Users/HP/Desktop/hhh.pdf",user1);
        } catch (Exception e) {
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
        }finally {
            MySQLDatabase.disconnect();
        }
        System.out.println("----------------------------------------");

        fileRepository.viewFileByClassification("new","bbbb");

    }

    }