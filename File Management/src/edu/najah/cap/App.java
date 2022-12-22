package edu.najah.cap;

import edu.najah.cap.Security.Authentication;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
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



    }

}