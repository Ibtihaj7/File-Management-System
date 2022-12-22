package edu.najah.cap.FileClassification;

import edu.najah.cap.FileClassification.FileType;
import edu.najah.cap.FileRepository.SystemFile;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassificationType {

    public static HashMap<Integer, ArrayList<SystemFile>> getSizeClasification() {
        return sizeClasification;
    }

    public static HashMap<FileType, ArrayList<SystemFile>> getTypeClasification() {
        return typeClasification;
    }

    public static HashMap<String, ArrayList<SystemFile>> getNewCategoryClasification() {
        return newCategoryClasification;
    }

    public static HashMap<Integer, ArrayList<SystemFile> > sizeClasification = new HashMap<Integer, ArrayList<SystemFile>>();
    public static HashMap<FileType, ArrayList<SystemFile> > typeClasification = new HashMap<FileType, ArrayList<SystemFile>>();
    public static HashMap<String, ArrayList<SystemFile> > newCategoryClasification = new HashMap<String, ArrayList<SystemFile>>();

}
