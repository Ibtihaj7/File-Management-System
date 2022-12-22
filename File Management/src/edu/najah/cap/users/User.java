package edu.najah.cap.users;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileClassification.Classification;
import edu.najah.cap.FileRepository.FileRepository;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Services.FileService;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private Classification classification ;

    public User() {
        classification=new Classification();
    }

    public void classifyFile(String fileName) throws SQLException {
        Scanner input = new Scanner(System.in);
        if (classification != null){
            System.out.println("How would you like to classify "+fileName+" file ? (size , type or new category) enter one of them please ");
            String choice = input.next();
            if(!(choice.equals("size") || choice.equals("type") || choice.equals("new category"))){
                System.out.println("bad choice");
                return;
            }
            if(choice.equals("size")){
                classification.classifySize(fileName);
                return;
            }
            if(choice.equals("type")){
                return;
            }
        }
    }
    public void PrintClassifiedFiles(){
        classification.displaySizeClassification();
    }

    public void viewFiles() throws SQLException  {
        String query="select * from files";
        ResultSet statement = null;
        try{
            statement = MySQLDatabase.getInstance().selectQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!statement.next()){
            System.out.println("there is no file in the system.");
            return;
        }
        for (int i = 1; statement.next(); i++) {
            System.out.println("File " + i + " name: "+statement.getString("name")+" \t path : "+statement.getString("path")+" \t type : "+statement.getString("type")+" \t size : "+statement.getInt("size")+" \t category : "+statement.getString("category"));
        }
        System.out.println();
    }

}
