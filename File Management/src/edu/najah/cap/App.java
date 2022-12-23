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
    public static void main(String[] args) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {

        FileRepository fileRepository = new FileRepository("karam");
        User user = new User("karam@gmail.com","karam123");
        fileRepository.importFile("C:\\Users\\HP\\Desktop\\karam.txt",user);
//        SystemFile filek = fileRepository.exportFileByName("karam",user);
//        System.out.println(filek);

//        SystemFile file1 = new SystemFile("ee","txt",100,"/Users/ibtihaj/Desktop/ee.txt",0);
//        SystemFile file2 = new SystemFile("tt","txt",1000,"/Users/ibtihaj/Desktop/tt.txt",0);
//        SystemFile file3 = new SystemFile("yy","txt",10,"/Users/ibtihaj/Desktop/yy.txt",0);
//
//
//        fileRepository.classifyFileBySize(file1);
//        fileRepository.classifyFileBySize(file2);
//        fileRepository.classifyFileBySize(file3);
//
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