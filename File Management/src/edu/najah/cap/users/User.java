package edu.najah.cap.users;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileClassification.Classification;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.FileRepository.intf.DeleteBehaviour;
import edu.najah.cap.FileRepository.intf.ExportBehaviour;
import edu.najah.cap.FileRepository.intf.ImportBehaviour;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    protected String name;
    protected String email;
    protected String password;

    protected ImportBehaviour importer;
    protected ExportBehaviour exporter;
    protected DeleteBehaviour deleter;

    protected Classification classification ;

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


    public void importFile(String url) throws SQLException {
        if(importer != null){
            importer.importFile(url);
        }
    }
    public void deleteFile(String filename, String category){
        if(deleter != null){
            deleter.delete(filename,category);
        }
    }
    public SystemFile exportFile(String filename, String category) throws SQLException {
        if(exporter != null){
            return exporter.export(filename,category);
        }
        return null;
    }

    public void setImporter(ImportBehaviour importer) {
        this.importer = importer;
    }

    public void setExporter(ExportBehaviour exporter) {
        this.exporter = exporter;
    }

    public void setDeleter(DeleteBehaviour deleter) {
        this.deleter = deleter;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
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

    public SystemFile exportFile(String fileName) throws SQLException {
        return exporter.export(fileName);
    }
}
