package edu.najah.cap.Services;

import edu.najah.cap.App;
import edu.najah.cap.Constant.Constant;
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

        if(encryptedFile.getSize()> Constant.LARGE_FILES_RANGE){
            throw new MaxSizeExeption(Constant.MAX_SIZE_EXCEPTION_MESSAGE);
        }

        ResultSet result = FileServiceUtil.findExistFile(encryptedFile);

        if (FileServiceUtil.isEmpty(result)) {
            FileServiceUtil.insertToDB(encryptedFile);
            App.LOGGER.debug("there is no version of the file and file add to version 0.");
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
    public static void view() {
        ResultSet statement = null;
        try{
            statement = FileServiceUtil.getAvailableFiles();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(FileServiceUtil.isEmpty(statement)){
            try {
                throw new FileNotFoundExeption(Constant.FILE_NOT_FOUND_EXCEPTION_MESSAGE);
            }catch (FileNotFoundExeption e){
                System.err.println(e);
            }
        }
        else FileServiceUtil.printAvailableFiles(statement);
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

    public static void viewFileByClassification(String categoryName, String categoryType) throws CategoryNotFoundExeption{
        if(categoryName.equals("size"))
            if(FileClassifier.getFileSizeRanges().containsKey(categoryType)) {
                viewFilesCategorizedBySize(categoryType);
                return;
            } else throw new CategoryNotFoundExeption("the category type not found in this category");
        if(categoryName.equals("type"))
            if (FileClassifier.getFileTypeRuler().containsKey(categoryType)) {
                viewFilesCategorizedByType(categoryType);
                return;
            } else throw new CategoryNotFoundExeption("the category type not found in this category");
        if(categoryName.equals("new category"))
            if(FileClassifier.getFileCategoryRulers().containsKey(categoryType)) {
                viewFilesWithCustomCategory(categoryType);
                return;
            }else throw new CategoryNotFoundExeption("the category type not found in this category");

        throw new CategoryNotFoundExeption("the category name not found in this category");
    }

    private static void viewFilesWithCustomCategory(String categoryName) {
        FileClassifier.getFileCategoryRulers().get(categoryName).forEach( file -> System.out.println(file.toString()) );
    }

    private static void viewFilesCategorizedByType(String categoryName) {
        FileClassifier.getFileTypeRuler().get(categoryName).forEach( file -> System.out.println(file.toString()) );
    }

    private static void viewFilesCategorizedBySize(String categoryName) {
        FileClassifier.getFileSizeRanges().get(categoryName).forEach( file -> System.out.println(file.toString()) );
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
