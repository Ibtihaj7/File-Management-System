package edu.najah.cap.classification;

import edu.najah.cap.FileRepository.SystemFile;
import java.util.ArrayList;
import java.util.HashMap;
abstract class InitializeClassifier {
    protected static void initializeStaticVariable(HashMap<String, ArrayList<SystemFile>> fileSizeRanges, HashMap<String, ArrayList<SystemFile>> fileTypeRuler) {
        initializeFileSizeRanges(fileSizeRanges);
        initializeFileTypeRuler(fileTypeRuler);
    }

    protected static void initializeFileTypeRuler(HashMap<String, ArrayList<SystemFile>> fileTypeRuler) {
        fileTypeRuler.put("txt",new ArrayList<SystemFile>());
        fileTypeRuler.put("pdf",new ArrayList<SystemFile>());
        fileTypeRuler.put("csv",new ArrayList<SystemFile>());
        fileTypeRuler.put("gif",new ArrayList<SystemFile>());
        fileTypeRuler.put("png",new ArrayList<SystemFile>());
        fileTypeRuler.put("jpeg",new ArrayList<SystemFile>());
        fileTypeRuler.put("doc",new ArrayList<SystemFile>());
        fileTypeRuler.put("html",new ArrayList<SystemFile>());
    }

    protected static void initializeFileSizeRanges(HashMap<String, ArrayList<SystemFile>> fileSizeRanges) {
        fileSizeRanges.put("Large",new ArrayList<SystemFile>());
        fileSizeRanges.put("Medium",new ArrayList<SystemFile>());
        fileSizeRanges.put("Small",new ArrayList<SystemFile>());
    }
}
