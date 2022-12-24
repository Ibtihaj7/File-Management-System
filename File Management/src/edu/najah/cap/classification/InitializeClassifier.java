package edu.najah.cap.classification;

import edu.najah.cap.FileRepository.SystemFile;
import java.util.ArrayList;
import java.util.HashMap;
abstract class InitializeClassifier {
    protected static void initializeStaticVariable(HashMap<String, ArrayList<SystemFile>> fileSizeRanges, HashMap<String, ArrayList<SystemFile>> fileTypeRuler, HashMap<String, ArrayList<SystemFile>> fileCategoryRulers) {
        initializeFileSizeRanges(fileSizeRanges);
        initializeFileTypeRuler(fileTypeRuler);
        initializeFileCategoryRulers(fileCategoryRulers);
    }
    protected static void initializeFileCategoryRulers(HashMap<String, ArrayList<SystemFile>> fileCategoryRulers) {
        fileCategoryRulers = new HashMap<String,ArrayList<SystemFile>>();
    }

    protected static void initializeFileTypeRuler(HashMap<String, ArrayList<SystemFile>> fileTypeRuler) {
        fileTypeRuler = new HashMap<>();
        fileTypeRuler.put("txt",new ArrayList<>());
        fileTypeRuler.put("pdf",new ArrayList<>());
        fileTypeRuler.put("csv",new ArrayList<>());
        fileTypeRuler.put("gif",new ArrayList<>());
        fileTypeRuler.put("png",new ArrayList<>());
        fileTypeRuler.put("jpeg",new ArrayList<>());
        fileTypeRuler.put("doc",new ArrayList<>());
        fileTypeRuler.put("html",new ArrayList<>());
    }

    protected static void initializeFileSizeRanges(HashMap<String, ArrayList<SystemFile>> fileSizeRanges) {
        fileSizeRanges = new HashMap<String, ArrayList<SystemFile>>();
        fileSizeRanges.put("Large",new ArrayList<>());
        fileSizeRanges.put("Medium",new ArrayList<>());
        fileSizeRanges.put("Small",new ArrayList<>());
    }
}
