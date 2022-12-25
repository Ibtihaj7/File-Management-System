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
import java.sql.SQLException;
import java.util.Arrays;


public abstract class FileService {
    private static Import anImport;
    private static Export anExport;
    private static Delete anDelete;

    public static void doImport(String url, User createdBy, VersionControl versionControlType) throws Exception {

        SystemFile encryptedFile = FileServiceUtil.getFileInformation(url);

        if(fileSizeIsLarge(encryptedFile)){
            throw new MaxSizeExeption(Constant.MAX_SIZE_EXCEPTION_MESSAGE);
        }

        ResultSet result = FileServiceUtil.findExistFile(encryptedFile);

        if (FileServiceUtil.isEmpty(result)) {
            FileServiceUtil.insertToDB(encryptedFile);
            App.LOGGER.debug("there is no version of the file and file add to version 0.");
            return;
        }
        boolean userIsAdmin = Authorization.hasAdminPermission(createdBy)
                ,canDoOverwrite = versionControlType.getClass().equals(Disable.class);
        if(canDoOverwrite && userIsAdmin){
            setAnImport(new Overwrite());
            anImport.doAction(encryptedFile,result);
            return;
        }
        setAnImport(new AddVersion());
        anImport.doAction(encryptedFile, result);
    }

    private static boolean fileSizeIsLarge(SystemFile file) { return file.getSize() > 1000000;}
    public static void view() {

        ResultSet statement = FileServiceUtil.getAvailableFiles();
        if (FileServiceUtil.isEmpty(statement))
            try {
                throw new FileNotFoundExeption(Constant.FILE_NOT_FOUND_EXCEPTION_MESSAGE);
            }catch (FileNotFoundExeption e){
                System.err.println(e.getMessage());
            }
        else App.LOGGER.debug("there is available files in the system.");
        FileServiceUtil.printAvailableFiles(statement);
    }

    public static void doRollBack(String fileName, int version) {
        String fileNameEncrypted = Encryption.encodeBase64(fileName);
        String query = "DELETE FROM files WHERE name = ? AND version > ?";
        try {
            MySQLDatabase.getInstance().executeStatement(query,Arrays.asList(fileNameEncrypted, version));
        } catch (SQLException e) {
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