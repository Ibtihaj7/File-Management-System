package edu.najah.cap.classification;

import edu.najah.cap.App;
import edu.najah.cap.Constant.Constant;
import edu.najah.cap.FileRepository.SystemFile;
import java.util.ArrayList;
import java.util.HashMap;
abstract class InitializeClassifier {
    protected static void initializeStaticVariable(HashMap<String, ArrayList<SystemFile>> fileSizeRanges, HashMap<String, ArrayList<SystemFile>> fileTypeRuler) {
        initializeFileSizeRanges(fileSizeRanges);
        initializeFileTypeRuler(fileTypeRuler);
    }

    protected static void initializeFileTypeRuler(HashMap<String, ArrayList<SystemFile>> fileTypeRuler) {
        fileTypeRuler.put(Constant.FILE_FORMAT_TEXT,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_PDF,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_CSV,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_GIF,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_PNG,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_JPEG,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_HTML,new ArrayList<SystemFile>());
        fileTypeRuler.put(Constant.FILE_FORMAT_DOC,new ArrayList<SystemFile>());
        App.LOGGER.info("initialFile Types added in the system.");
    }

    protected static void initializeFileSizeRanges(HashMap<String, ArrayList<SystemFile>> fileSizeRanges) {
        fileSizeRanges.put(Constant.LARGE_SIZE_FILE,new ArrayList<SystemFile>());
        fileSizeRanges.put(Constant.MEDIUM_SIZE_FILE,new ArrayList<SystemFile>());
        fileSizeRanges.put(Constant.SMALL_SIZE_FILE,new ArrayList<SystemFile>());
        App.LOGGER.info("initialFile Types added in the system.");
    }

}
