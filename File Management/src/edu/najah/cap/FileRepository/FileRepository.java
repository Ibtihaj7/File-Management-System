package edu.najah.cap.FileRepository;

import com.sun.corba.se.impl.encoding.CDROutputObject;
import edu.najah.cap.Services.FileService;

import edu.najah.cap.Users.User;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

public class FileRepository {
    private String name;

    public FileRepository(String name) {
        this.name = name;
    }

    public void importFile(String url, User createdBy) throws Exception  {
     FileService.doImport(url,createdBy);
    }
    public SystemFile exportFileByName(String fileName,String type, User createdBy) throws Exception {
       return FileService.doExportByName(fileName,type, createdBy);
    }
    public ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType)throws Exception  {
        return FileService.doExportByCategory(categoryName, categoryType);
    }
    public void deleteFileByName(String url,User createdBy)throws Exception  {
        FileService.doDeleteByName(url,createdBy);
    }
    public void deleteFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        FileService.doDeleteByCategory(categoryName,categoryType, createdBy);
    }

    public void classifyFileBySize(SystemFile file){
        FileClassifier.classifyFileBySize(file);
    }
    public void classifyFileByType(SystemFile file) throws Exception{
        FileClassifier.classifyFileByType(file);
    }
    public void classifyFileByCategory(SystemFile file, String categoryName){ FileClassifier.classifyFileByCategory(file,categoryName); }

    public void viewAllFiles() throws Exception { FileService.view(); }
    public void viewFilesWithCustomCategory(String categoryName) {
        FileService.viewFilesWithCustomCategory(categoryName);
    }

    public  void viewFilesCategorizedByType(String categoryName) {
        FileService.viewFilesCategorizedByType(categoryName);


    }

    public  void viewFilesCategorizedBySize(String categoryName) {
        FileService.viewFilesCategorizedBySize(categoryName);
    }



    public void RollBack(String fileName,int version,User createdBy) throws Exception  {
        FileService.doRollBack(fileName,version,createdBy);
    }
}
