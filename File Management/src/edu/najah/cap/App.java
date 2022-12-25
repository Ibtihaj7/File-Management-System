package edu.najah.cap;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Users.User;

public class App {
    public static void main(String[] args) {
        FileRepository fileRepository = new FileRepository("karam");
        User user = new User("ABAR@GMAIL.COM","1122");
        SystemFile file1=null;
        try{
            fileRepository.importFile("C:\\Users\\HP\\Desktop\\karamzz.txt",user);
            fileRepository.importFile("C:\\Users\\HP\\Desktop\\abr.txt",user);

            SystemFile file = fileRepository.exportFileByName("karamzz","txt",user);
            SystemFile file22 = fileRepository.exportFileByName("abr","txt",user);
            fileRepository.classifyFileByType(file);
            fileRepository.classifyFileByType(file22);
//            fileRepository.RollBack("karam",0,user);
//            fileRepository.RollBack("abr",0,user);
//            fileRepository.RollBack("ahm",0,user);
//            fileRepository.RollBack("ibti",0,user);
//             file1 = fileRepository.exportFileByName("karam","txt",user);

            fileRepository.viewFilesCategorizedByType("txt");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
//        System.out.println(file1);

//        fileRepository.deleteFileByName("karam", user);

//        SystemFile file1 = new SystemFile("ee","txt",100,"/Users/ibtihaj/Desktop/ee.txt",0);
//        SystemFile file2 = new SystemFile("tt","txt",1000,"/Users/ibtihaj/Desktop/tt.txt",0);
//        SystemFile file3 = new SystemFile("yy","txt",10,"/Users/ibtihaj/Desktop/yy.txt",0);


//        fileRepository.classifyFileBySize(file1);
//        fileRepository.classifyFileBySize(file2);
//        fileRepository.classifyFileBySize(file3);

//
//        FileClassifier.getFileSizeRanges().forEach((key,value)->{
//            System.out.println(key+" : ");
//            value.forEach(file->{
//                System.out.println(file);
//            });
//        });
//        System.out.println(file1);

    }

}