package edu.najah.cap.classification;

import edu.najah.cap.Constant.Constant;
import edu.najah.cap.FileRepository.SystemFile;

    public class SupportedTypes {
        protected static boolean txtFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_TEXT);}
        protected static boolean pdfFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_PDF);}
        protected static boolean csvFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_CSV);}
        protected static boolean gifFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_GIF);}
        protected static boolean pngFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_PNG);}
        protected static boolean jpegFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_JPEG);}
        protected static boolean docFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_DOC);}
        protected static boolean htmlFile(SystemFile file) {return file.getType().equals(Constant.FILE_FORMAT_HTML);}
    }

