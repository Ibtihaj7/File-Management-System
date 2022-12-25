package edu.najah.cap.classification;

import edu.najah.cap.App;
import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Exceptions.MaxSizeExeption;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Exceptions.TypeNotSupportExeption;
import edu.najah.cap.Users.User;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class FileClassifier {

    private static HashMap<String, ArrayList<SystemFile>> fileSizeRanges = new HashMap<String, ArrayList<SystemFile>>();
    private static HashMap<String, ArrayList<SystemFile>> fileTypeFormats = new HashMap<String, ArrayList<SystemFile>>();
    private static HashMap<String, ArrayList<SystemFile>> fileCustomCategories = new HashMap<String, ArrayList<SystemFile>>();

    static {
        App.LOGGER.info("Initialize FileClassifier parameter.");
        InitializeClassifier.initializeStaticVariable(fileSizeRanges, fileTypeFormats);
    }

    public static void classifyFileBySize(SystemFile file) {
        fileSizeRanges.get(sizeOfFile(file)).add(file);
    }

    private static String sizeOfFile(SystemFile file) {
        if (isFileSmall(file.getSize())) return Constant.SMALL_SIZE_FILE;
        if (isFileMedium(file.getSize())) return Constant.MEDIUM_SIZE_FILE;
        if (isFileLarge(file.getSize())) return Constant.LARGE_SIZE_FILE;
        return Constant.MAX_SIZE_EXCEPTION_MESSAGE;
    }

    private static boolean isFileSmall(int size) {
        return size < Constant.SMALL_FILES_RANGE;
    }

    private static boolean isFileMedium(int size) {
        return size < Constant.MEDIUM_FILES_RANGE;
    }
    private static boolean isFileLarge(int size) {
        return size < Constant.LARGE_FILES_RANGE;
    }

    public static void classifyFileByType(SystemFile file) {
        try {
            fileTypeFormats.get(typeOfFile(file)).add(file);
        } catch (TypeNotSupportExeption e) {
            System.err.println(e.getMessage());
        }
    }
        private static String typeOfFile(SystemFile file) throws TypeNotSupportExeption {
            if (SupportedTypes.txtFile(file)) return Constant.FILE_FORMAT_TEXT;
            if (SupportedTypes.pdfFile(file)) return Constant.FILE_FORMAT_PDF;
            if (SupportedTypes.csvFile(file)) return Constant.FILE_FORMAT_CSV;
            if (SupportedTypes.gifFile(file)) return Constant.FILE_FORMAT_GIF;
            if (SupportedTypes.pngFile(file)) return Constant.FILE_FORMAT_PNG;
            if (SupportedTypes.jpegFile(file)) return Constant.FILE_FORMAT_JPEG;
            if (SupportedTypes.docFile(file)) return Constant.FILE_FORMAT_DOC;
            if (SupportedTypes.htmlFile(file)) return Constant.FILE_FORMAT_HTML;

            throw new TypeNotSupportExeption(Constant.FILE_TYPE_NOT_SUPPORTED_Exeption_MESSAGE);
        }

        public static void classifyFileByCategory (SystemFile file, String categoryName){
            if (categoryNameNotExist(categoryName))
                fileCustomCategories.put(categoryName, new ArrayList<>());

            fileCustomCategories.get(categoryName).add(file);
        }

        private static boolean categoryNameNotExist(String categoryName){
            return !fileCustomCategories.containsKey(categoryName);
        }

        public static HashMap<String, ArrayList<SystemFile>> getFileSizeRanges () {
            return fileSizeRanges;
        }

        public static HashMap<String, ArrayList<SystemFile>> getFileTypeRuler () {
            return fileTypeFormats;
        }

        public static HashMap<String, ArrayList<SystemFile>> getFileCategoryRulers () {
            return fileCustomCategories;
        }

}