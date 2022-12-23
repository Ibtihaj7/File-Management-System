package edu.najah.cap;

import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.AES;
import edu.najah.cap.Security.Authentication;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.classification.FileClassifier;
import edu.najah.cap.users.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        FileRepository fileRepository = new FileRepository("ibtihaj");
        User user = new User("ibtihaj.adham7@gmail.com","ibtihaj");
//        fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam.txt",user);
        SystemFile file1=null;
        try{
             file1 = fileRepository.exportFileByName("karam",user);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println(file1);
//        fileRepository.deleteFileByName("karam", user);

//        SystemFile file1 = new SystemFile("ee","txt",100,"/Users/ibtihaj/Desktop/ee.txt",0);
//        SystemFile file2 = new SystemFile("tt","txt",1000,"/Users/ibtihaj/Desktop/tt.txt",0);
//        SystemFile file3 = new SystemFile("yy","txt",10,"/Users/ibtihaj/Desktop/yy.txt",0);


//        fileRepository.classifyFileBySize(file1);
//        fileRepository.classifyFileBySize(file2);
//        fileRepository.classifyFileBySize(file3);

        System.out.println("fff");
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