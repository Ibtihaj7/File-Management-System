package edu.najah.cap.classification;

import edu.najah.cap.FileRepository.SystemFile;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class FileClassifier {
    private static HashMap<String,ArrayList<SystemFile>> fileSizeRanges ;
    private static HashMap<String,ArrayList<SystemFile>> fileTypeRuler;
    private static HashMap<String,ArrayList<SystemFile>> fileCategoryRulers;

    static {
        fileSizeRanges = new HashMap<String, ArrayList<SystemFile>>();
        fileSizeRanges.put("Large",new ArrayList<SystemFile>());
        fileSizeRanges.put("Medium",new ArrayList<SystemFile>());
        fileSizeRanges.put("Small",new ArrayList<SystemFile>());

        fileTypeRuler = new HashMap<String,ArrayList<SystemFile>>();
        fileTypeRuler.put("txt",new ArrayList<SystemFile>());
        fileTypeRuler.put("pdf",new ArrayList<SystemFile>());
        fileTypeRuler.put("csv",new ArrayList<SystemFile>());
        fileTypeRuler.put("gif",new ArrayList<SystemFile>());
        fileTypeRuler.put("png",new ArrayList<SystemFile>());
        fileTypeRuler.put("jpeg",new ArrayList<SystemFile>());
        fileTypeRuler.put("doc",new ArrayList<SystemFile>());
        fileTypeRuler.put("html",new ArrayList<SystemFile>());

        fileCategoryRulers = new HashMap<String,ArrayList<SystemFile>>();
    }
    public static void classifyFileBySize(SystemFile file){
        if(file.getSize() <100 ) {
            fileSizeRanges.get("Small").add(file);
            return;
        }
        if(file.getSize() < 1000){
            fileSizeRanges.get("Medium").add(file);
            return;
        }
        fileSizeRanges.get("Large").add(file);
    }

    public static void classifyFileByType(SystemFile file){
        if(file.getType().equals("txt")){
            fileTypeRuler.get("txt").add(file);
            return;
        }
        if(file.getType().equals("pdf")){
            fileTypeRuler.get("pdf").add(file);
            return;
        }
        if(file.getType().equals("csv")){
            fileTypeRuler.get("csv").add(file);
            return;
        }
        if(file.getType().equals("gif")){
            fileTypeRuler.get("gif").add(file);
            return;
        }
        if(file.getType().equals("png")){
            fileTypeRuler.get("png").add(file);
            return;
        }
        if(file.getType().equals("jpeg")){
            fileTypeRuler.get("jpeg").add(file);
            return;
        }
        if(file.getType().equals("doc")){
            fileTypeRuler.get("doc").add(file);
            return;
        }
        if(file.getType().equals("html")){
            fileTypeRuler.get("html").add(file);
            return;
        }
        System.out.println("Error type name");
    }

    public static void classifyFileByCategory(SystemFile file,String categoryName){
        if(!fileCategoryRulers.containsKey(categoryName))
            fileCategoryRulers.put(categoryName,new ArrayList<SystemFile>());

        fileCategoryRulers.get(categoryName).add(file);
    }

    public static HashMap<String, ArrayList<SystemFile>> getFileSizeRanges() { return fileSizeRanges; }

    public static HashMap<String, ArrayList<SystemFile>> getFileTypeRuler() { return fileTypeRuler; }

    public static HashMap<String, ArrayList<SystemFile>> getFileCategoryRulers() { return fileCategoryRulers; }
}
