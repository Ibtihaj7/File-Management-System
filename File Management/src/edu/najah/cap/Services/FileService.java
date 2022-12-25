package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.VersionControl.intf.VersionControl;
import edu.najah.cap.VersionControl.impl.Disable;
import edu.najah.cap.Exceptions.*;
import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;


public abstract class FileService {

    public static void doImport(String url, User createdBy, VersionControl versionControlType) throws Exception {

        SystemFile encryptedFile = FileServiceUtil.getFileInformation(url);

        if(encryptedFile.getSize() > 1000000){
            throw new MaxSizeExeption("The file size is too large for the system to store");
        }

        ResultSet result = FileServiceUtil.findExistFile(encryptedFile);

        if (FileServiceUtil.isEmpty(result)) {
            FileServiceUtil.insertToDB(encryptedFile);
            return;
        }
        if(versionControlType.getClass().equals(Disable.class) && Authorization.hasAdminPermission(createdBy)){
            FileServiceUtil.Overwrite(encryptedFile, result);
            return;
        }
            FileServiceUtil.addVersion(encryptedFile, result);
    }

    public static SystemFile doExportByName(String filename,String type, User createdBy) throws Exception {

        String encryptedFileName= Encryption.encodeBase64(filename);

        ResultSet statement = FileServiceUtil.findFileByName(encryptedFileName, type);

        if(FileServiceUtil.isEmpty(statement)){
            throw new FileNotFoundExeption("There is no file with this name.");
        }
        return FileServiceUtil.findCurrentFile(statement);
    }
    public static ArrayList<SystemFile> doExportByCategory(String categoryName, String categoryType )throws Exception{
        if (categoryType.equals("size"))
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName))
                return FileClassifier.getFileSizeRanges().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");

        if(categoryType.equals("type"))
            if(FileClassifier.getFileTypeRuler().containsKey(categoryName))
                return FileClassifier.getFileTypeRuler().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");

        if(categoryType.equals("category"))
            if(FileClassifier.getFileCategoryRulers().containsKey(categoryName))
                return FileClassifier.getFileCategoryRulers().get(categoryName);
            else
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");

        throw new CategoryNotFoundExeption("'"+categoryType+"' category not exist.");
    }

    public static void doDeleteByName(String filename, User createdBy)throws Exception {

        String fileNameEncoded =Encryption.encodeBase64(filename);
        String query = "DELETE FROM files WHERE name = ?";
        try {
            MySQLDatabase.getInstance().executeStatement(query,Arrays.asList(fileNameEncoded));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("File deleted successfully");
    }
    public static void doDeleteByCategory(String categoryName, String categoryType, User createdBy) throws Exception{
        if(categoryType.equals("size")){
            if (FileClassifier.getFileSizeRanges().containsKey(categoryName)){
                FileClassifier.getFileSizeRanges().get(categoryName).forEach(file->{
                    try {
                        doDeleteByName(file.getName(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
        if(categoryType.equals("type")){
            if (FileClassifier.getFileTypeRuler().containsKey(categoryName)){
                FileClassifier.getFileTypeRuler().get(categoryName).forEach(file->{
                    try {
                        doDeleteByName(file.getName(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
        if(categoryType.equals("category")){
            if (FileClassifier.getFileCategoryRulers().containsKey(categoryName)){
                FileClassifier.getFileCategoryRulers().get(categoryName).forEach(file->{
                    try {
                        doDeleteByName(file.getName(), createdBy);
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                    }
                });
            }else{
                throw new CategoryNotFoundExeption("'"+categoryName+"' not exist in "+categoryType+" category");
            }
        }
    }
    public static void view() throws Exception {
       ResultSet statement = null;
        try{
           statement = FileServiceUtil.getAvailableFiles();
        }catch (Exception e){
           System.err.println(e.getMessage());
        }
        if(FileServiceUtil.isEmpty(statement)){
            throw new FileNotFoundExeption("There is no file in the system.");
        }
       FileServiceUtil.printAvailableFiles(statement);
    }

    public static void doRollBack(String fileName, int version, User createdBy) throws Exception {

        String fileNameEncrypted = Encryption.encodeBase64(fileName);
        String query = "DELETE FROM files WHERE name = ? AND version > ?";
        try {
            MySQLDatabase.getInstance().executeStatement(query,Arrays.asList(fileNameEncrypted, version));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


  public static void viewFilesWithCustomCategory(String categoryName) {
        System.out.println(FileClassifier.getFileCategoryRulers().get(categoryName).toString());

    }

   public static void viewFilesCategorizedByType(String categoryName) {
        System.out.println(FileClassifier.getFileTypeRuler().get(categoryName).toString());


    }

  public static void viewFilesCategorizedBySize(String categoryName) {
        System.out.println(FileClassifier.getFileSizeRanges().get(categoryName).toString());

    }
}
