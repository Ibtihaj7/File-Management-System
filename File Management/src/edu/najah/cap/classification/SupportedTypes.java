package edu.najah.cap.classification;

import edu.najah.cap.FileRepository.SystemFile;

public class SupportedTypes {
    protected static boolean txtFile(SystemFile file) {return file.getType().equals("txt");}
    protected static boolean pdfFile(SystemFile file) {return file.getType().equals("pdf");}
    protected static boolean csvFile(SystemFile file) {return file.getType().equals("csv");}
    protected static boolean gifFile(SystemFile file) {return file.getType().equals("gif");}
    protected static boolean pngFile(SystemFile file) {return file.getType().equals("png");}
    protected static boolean jpegFile(SystemFile file) {return file.getType().equals("jpeg");}
    protected static boolean docFile(SystemFile file) {return file.getType().equals("doc");}
    protected static boolean htmlFile(SystemFile file) {return file.getType().equals("html");}
}
