package edu.najah.cap.Services;

import edu.najah.cap.App;
import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Security.Encryption;
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

    public static void doImport(String url, User createdBy, VersionControl versionControlType) throws Exception {
        App.LOGGER.info("Received request to import file from URL: " + url +
                " by user: " + createdBy.getUsername() + " with version control type: " + versionControlType);

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
            anImport.Import(encryptedFile,result);
           App.LOGGER.info("Successfully imported file from URL: " + url);
            return;
        }
            setAnImport(new AddVersion());
            anImport.Import(encryptedFile, result);
        App.LOGGER.info("Successfully imported file from URL: " + url);

    }
    public static void view(User user) {
        ResultSet statement = null;
        App.LOGGER.debug("Displaying file list for" + user.getUsername());

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

    public static void doRollBack(String fileName,String type, int version){
        App.LOGGER.info("Received request to roll back file: " + fileName + " to version: " + version);
        String fileNameEncrypted = Encryption.encodeBase64(fileName);
        String query = "DELETE FROM files WHERE name = ? AND type = ? AND version > ?";
        try {
            MySQLDatabase.getInstance().executeStatement(query,Arrays.asList(fileNameEncrypted,type, version));
           App.LOGGER.info("Successfully rolled back file: " + fileName + " to version: " + version);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void viewFileByClassification(String categoryName, String categoryType, User user) throws CategoryNotFoundExeption{
        App.LOGGER.debug(user.getUsername() +" is displaying files classified by category: " + categoryName + " with type: " + categoryType);
        FileServiceUtil.checkCategoryNameAndPrint(categoryName,categoryType, user);
    }

    static void viewFilesWithCustomCategory(String categoryName, User user) {
       App.LOGGER.debug(user.getUsername() +" is displaying list of files with custom category: " + categoryName);
        FileClassifier.getFileCategoryRulers().get(categoryName).forEach( file -> System.out.println(file.toString()) );
    }

    static void viewFilesCategorizedByType(String categoryName, User user) {
        App.LOGGER.debug(user.getUsername() + "is displaying list of files with custom category: " + categoryName);
        FileClassifier.getFileTypeRuler().get(categoryName).forEach( file -> System.out.println(file.toString()) );
    }

    static void viewFilesCategorizedBySize(String categoryName, User user) {
        App.LOGGER.debug(user.getUsername() + "is displaying list of files with custom category: " + categoryName);
        FileClassifier.getFileSizeRanges().get(categoryName).forEach( file -> System.out.println(file.toString()) );
    }


    public static void setAnImport(Import anImport) {
        FileService.anImport = anImport;
    }

}
