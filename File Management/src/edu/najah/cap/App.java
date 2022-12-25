package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Users.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class App {
    public static final Logger LOGGER = Logger.getLogger(App.class.getName());
    public static void main(String[] args) {
        BasicConfigurator.configure();
        FileRepository fileRepository = new FileRepository();
        User user = new User("ABRAR","1122");
        SystemFile file;
        try{
            fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam22.txt",user);
            file = fileRepository.exportFileByName("karam22","txt",user);
            System.out.println(file);
            fileRepository.viewAllFiles();

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            MySQLDatabase.disconnect();
        }
    }

}