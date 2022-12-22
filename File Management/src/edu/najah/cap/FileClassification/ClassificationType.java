package edu.najah.cap.FileClassification;

import edu.najah.cap.FileClassification.FileType;
import edu.najah.cap.FileRepository.SystemFile;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassificationType {
    private static HashMap<String, ArrayList<SystemFile> > sizeClasification = new HashMap<String, ArrayList<SystemFile>>();
    private static HashMap<String, ArrayList<SystemFile> > typeClasification = new HashMap<String, ArrayList<SystemFile>>();
    private static HashMap<String, ArrayList<SystemFile> > newCategoryClasification = new HashMap<String, ArrayList<SystemFile>>();

    public static HashMap<String, ArrayList<SystemFile>> getSizeClasification() {
        return sizeClasification;
    }

    public static HashMap<String, ArrayList<SystemFile>> getTypeClasification() {
        return typeClasification;
    }

    public static HashMap<String, ArrayList<SystemFile>> getNewCategoryClasification() { return newCategoryClasification; }

}
