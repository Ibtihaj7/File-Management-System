package edu.najah.cap.Services;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.Services.Delete.Delete;
import edu.najah.cap.Services.Export.Export;
import edu.najah.cap.Services.Import.AddVersion;
import edu.najah.cap.Services.Import.Import;
import edu.najah.cap.Services.Import.Overwrite;
import edu.najah.cap.VersionControl.intf.VersionControl;
import edu.najah.cap.VersionControl.impl.Disable;
import edu.najah.cap.Exceptions.*;
import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;

import java.sql.ResultSet;
import java.util.Arrays;


public abstract class FileService {
    private static Import anImport;
    private static Export anExport;
    private static Delete anDelete;

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
            setAnImport(new Overwrite());
            anImport.doAction(encryptedFile,result);
            return;
        }
            setAnImport(new AddVersion());
            anImport.doAction(encryptedFile, result);
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

    public static void doRollBack(String fileName, int version) throws Exception {

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

    public static void setAnImport(Import anImport) {
        FileService.anImport = anImport;
    }

    public static void setAnExport(Export anExport) {
        FileService.anExport = anExport;
    }

    public static void setAnDelete(Delete anDelete) {
        FileService.anDelete = anDelete;
    }
}
