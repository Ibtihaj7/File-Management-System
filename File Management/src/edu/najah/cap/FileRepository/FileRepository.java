package edu.najah.cap.FileRepository;

import edu.najah.cap.Exceptions.AuthorizationExeption;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Services.FileService;
import edu.najah.cap.Users.User;
import edu.najah.cap.VersionControl.impl.Enable;
import edu.najah.cap.VersionControl.intf.VersionControl;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

public class FileRepository {

    private VersionControl versionControl = new Enable();
    public void importFile(String url, User createdBy) throws Exception  {
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an import for a file.");
        }
     FileService.doImport(url,createdBy,versionControl);
    }

    public SystemFile exportFileByName(String fileName, String type, User createdBy) throws Exception {
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an export for a file.");
        }
       return FileService.doExportByName(fileName,type, createdBy);
    }
    public ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an export for these files.");
        }
        return FileService.doExportByCategory(categoryName, categoryType);
    }
    public void deleteFileByName(String url,User createdBy)throws Exception  {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an delete for a file.");
        }
        FileService.doDeleteByName(url,createdBy);
    }
    public void deleteFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an delete for these files.");
        }
        FileService.doDeleteByCategory(categoryName,categoryType, createdBy);
    }

    public void classifyFileBySize(SystemFile file, User createdBy)throws Exception{
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an classify for this file.");
        }
        FileClassifier.classifyFileBySize(file);
    }
    public void classifyFileByType(SystemFile file, User createdBy) throws Exception{
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an classify for this file.");
        }
        FileClassifier.classifyFileByType(file);
    }
    public void classifyFileByCategory(SystemFile file, String categoryName){ FileClassifier.classifyFileByCategory(file,categoryName); }

    public void viewAllFiles() throws Exception {
        FileService.view();
    }
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
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption("Your permission is not allowed to do an rollback for versions");
        }
        FileService.doRollBack(fileName,version,createdBy);
    }

    public void setVersionControl(VersionControl versionControl,User user) throws Exception {
        if(!Authorization.hasAdminPermission(user)){
            throw new AuthorizationExeption("Your permission is not allowed to set or change Version Control.");
        }
        this.versionControl = versionControl;
    }
}
