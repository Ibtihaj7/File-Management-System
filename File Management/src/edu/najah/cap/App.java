package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Users.User;


public class App {
    public static void main(String[] args) {
        FileRepository fileRepository = new FileRepository();
        User user = new User("karam","karam@123");
        SystemFile file;
        try{
//            fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam22.txt",user);
//            fileRepository.setVersionControl(new Disable(), user);
//            fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam22.txt",user);
//            file = fileRepository.exportFileByName("karam22","txt",user);
//            System.out.println(file);
//            fileRepository.setVersionControl(new Enable(), user);
//            fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam22.txt",user);
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