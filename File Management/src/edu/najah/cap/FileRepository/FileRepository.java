package edu.najah.cap.FileRepository;

import edu.najah.cap.Services.FileService;
import edu.najah.cap.classification.FileClassifier;
import edu.najah.cap.users.User;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileRepository {
    private String name;

    public FileRepository(String name) {
        this.name = name;
    }

    public void importFile(String url, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
        FileService.doImport(url,createdBy);
    }
    public SystemFile exportFileByName(String url, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException{
       return FileService.doExportByName(url, createdBy);
    }
    public ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType) {
        return FileService.doExportByCategory(categoryName, categoryType);
    }
    public void deleteFileByName(String url,User createdBy) {
        FileService.doDeleteByName(url,createdBy);
    }
    public void deleteFileByCategory(String categoryName,String categoryType) {
        FileService.doDeleteByCategory(categoryName,categoryType);
    }

    public void classifyFileBySize(SystemFile file){
        FileClassifier.classifyFileBySize(file);
    }
    public void classifyFileByType(SystemFile file){
        FileClassifier.classifyFileByType(file);
    }
    public void classifyFileByCategory(SystemFile file, String categoryName){
        FileClassifier.classifyFileByCategory(file,categoryName);
    }
    public void viewFiles() throws SQLException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
        FileService.view();
    }
    public void ViewSizeRating(){
        FileService.viewSizeRating();
    }
    public void ViewTypeRating(){
        FileService.viewTypeRating();
    }
    public void ViewCategoryRating(){
        FileService.viewCategoryRating();
    }

    public void RollBack(String url,User createdBy) throws SQLException {
        FileService.doRollBack(url,createdBy);
    }
}
