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
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {
        Scanner in=new Scanner(System.in);
        System.out.print("Enter the user email: ");
        String userEmail=in.nextLine();
        System.out.print("Enter the password : ");
        String password=in.nextLine();
        try {
            Authentication.logIn(userEmail, password);
            if(!Authentication.isLogUserStatus()){
           System.out.println("User name or password isn't correct.");
            return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        User user =new User(userEmail,password);

        FileRepository fileRepository = new FileRepository("karam");
        System.out.println(user.getRole());
//        fileRepository.importFile("/Users/ibtihaj/Desktop/xcxct.txt",user);
        SystemFile filee = fileRepository.exportFileByName("xcxct",user);
        System.out.println("----------->"+filee);
//       fileRepository.importFile("/Users/ibtihaj/Desktop/words.txt",user);
  //   fileRepository.importFile("/Users/ibtihaj/Desktop/sss.txt",user);
//        fileRepository.importFile("/Users/ibtihaj/Desktop/www.txt",user);
//        fileRepository.importFile("/Users/ibtihaj/Desktop/qqq.txt",user);
//        fileRepository.importFile("/Users/ibtihaj/Desktop/aaa.txt",user);
//        fileRepository.importFile("/Users/ibtihaj/Desktop/zzz.txt",user);
        SystemFile file1 = new SystemFile("ee","txt",100,"/Users/ibtihaj/Desktop/ee.txt",0);
        SystemFile file2 = new SystemFile("tt","txt",1000,"/Users/ibtihaj/Desktop/tt.txt",0);
        SystemFile file3 = new SystemFile("yy","txt",10,"/Users/ibtihaj/Desktop/yy.txt",0);


        fileRepository.classifyFileBySize(file1);
        fileRepository.classifyFileBySize(file2);
        fileRepository.classifyFileBySize(file3);


        FileClassifier.getFileSizeRanges().forEach((key,value)->{
            System.out.println(key+" : ");
            value.forEach(file->{
                System.out.println(file);
            });
        });
        System.out.println(file1);

    }

}