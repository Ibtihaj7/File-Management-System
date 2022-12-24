package edu.najah.cap.classification;

import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Exceptions.TypeNotSupportExeption;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class FileClassifier {
    private static HashMap<String,ArrayList<SystemFile>> fileSizeRanges = new HashMap<String,ArrayList<SystemFile>>() ;
    private static HashMap<String,ArrayList<SystemFile>> fileTypeRuler= new HashMap<String,ArrayList<SystemFile>>() ;
    private static HashMap<String,ArrayList<SystemFile>> fileCategoryRulers= new HashMap<String,ArrayList<SystemFile>>() ;

    static {
     InitializeClassifier.initializeStaticVariable(fileSizeRanges,fileTypeRuler);
    }

    public static void classifyFileBySize(SystemFile file){
        fileSizeRanges.get(sizeOfFile(file)).add(file);
    }

    private static String sizeOfFile(SystemFile file) {
        if(isFileSmall(file.getSize())) return "Small";
        if(isFileMedium(file.getSize())) return "Medium";
        return "Large";
    }
    private static boolean isFileSmall(int size){ return size < 100; }
    private static boolean isFileMedium(int size){ return size<5000; }

    public static void classifyFileByType(SystemFile file) {
        try {
            fileTypeRuler.get(typeOfFile(file)).add(file);
        }catch (TypeNotSupportExeption e){
            System.err.println(e.getMessage());
        }
    }

    private static String typeOfFile(SystemFile file) throws TypeNotSupportExeption{
        if(SupportedTypes.txtFile(file))return "txt";
        if(SupportedTypes.pdfFile(file))return "pdf";
        if(SupportedTypes.csvFile(file))return "csv";
        if(SupportedTypes.gifFile(file))return "gif";
        if(SupportedTypes.pngFile(file))return "png";
        if(SupportedTypes.jpegFile(file))return "jpeg";
        if(SupportedTypes.docFile(file))return "doc";
        if(SupportedTypes.htmlFile(file))return "html";

        String exceptionMessage = "The type of this file not support in our system.";
        throw new TypeNotSupportExeption(exceptionMessage);
    }

    public static void classifyFileByCategory(SystemFile file,String categoryName){
        if(categoryNameNotExist(categoryName))
            fileCategoryRulers.put(categoryName,new ArrayList<>());

        fileCategoryRulers.get(categoryName).add(file);
    }

    private static boolean categoryNameNotExist(String categoryName) {
        return !fileCategoryRulers.containsKey(categoryName);
    }

    public static HashMap<String, ArrayList<SystemFile>> getFileSizeRanges() { return fileSizeRanges; }

    public static HashMap<String, ArrayList<SystemFile>> getFileTypeRuler() { return fileTypeRuler; }

    public static HashMap<String, ArrayList<SystemFile>> getFileCategoryRulers() { return fileCategoryRulers; }
}
