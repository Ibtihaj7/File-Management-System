package edu.najah.cap.FileClassification.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;

import edu.najah.cap.FileClassification.FileType;
import edu.najah.cap.FileRepository.Ifile;
import edu.najah.cap.FileRepository.SystemFile;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TypeClassification {

    private ArrayList<Ifile> documentFiles;
    private ArrayList<Ifile>textFiles;
    private ArrayList<Ifile>pdfFiles;
    private ArrayList<Ifile> csvFiles;
    private ArrayList<Ifile> htmlFiles;
    public TypeClassification() {
        documentFiles=new ArrayList<>();
        textFiles=new ArrayList<>();
        pdfFiles=new ArrayList<>();
        csvFiles=new ArrayList<>();
        htmlFiles=new ArrayList<>();
    }
    public void addClassification(){
        try {
            String query ="SELECT* FROM files";
            ResultSet statement = MySQLDatabase.getInstance().selectQuery(query);
            while(statement.next()){
                SystemFile file=new SystemFile(statement.getString("name"),statement.getString("type"),
                        statement.getInt("size"), statement.getString("category"),
                        statement.getString("path"), statement.getInt("version"));
                if (FileType.valueOf(file.getType().toUpperCase()).equals(FileType.TXT)){
                    textFiles.add(file);
                }
                if (FileType.valueOf(file.getType().toUpperCase()).equals(FileType.CSV)){
               csvFiles.add(file);
                }
                if (FileType.valueOf(file.getType().toUpperCase()).equals(FileType.DOC)){
               documentFiles.add(file);
                }
                if (FileType.valueOf(file.getType().toUpperCase()).equals(FileType.HTML)){
             htmlFiles.add(file);
                }
                if (FileType.valueOf(file.getType().toUpperCase()).equals(FileType.PDF)){
                pdfFiles.add(file);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void classifyTextFiles(){
        for (Ifile file:textFiles) {
            System.out.println(file.toString());
        }
    }
    public void classifyPdfFiles(){
        for (Ifile file:pdfFiles) {
            System.out.println(file.toString());
        }
    }
    public void classifyCsvFiles(){
        for (Ifile file:csvFiles) {
            System.out.println(file.toString());
        }
    }
    public void classifyDocumentFiles(){
        for (Ifile file:documentFiles) {
            System.out.println(file.toString());
        }
    }
    public void classifyHtmlFiles(){
        for (Ifile file:htmlFiles) {
            System.out.println(file.toString());
        }
    }

}

