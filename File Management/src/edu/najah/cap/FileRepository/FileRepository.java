package edu.najah.cap.FileRepository;

import edu.najah.cap.Constant.Constant;
import edu.najah.cap.Exceptions.AuthorizationExeption;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Services.Delete.Delete;
import edu.najah.cap.Services.Export.Category;
import edu.najah.cap.Services.Export.Export;
import edu.najah.cap.Services.Export.Name;
import edu.najah.cap.Services.FileService;
import edu.najah.cap.Services.Import.Import;
import edu.najah.cap.Users.User;
import edu.najah.cap.VersionControl.impl.Enable;
import edu.najah.cap.VersionControl.intf.VersionControl;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

public class FileRepository {

    private VersionControl versionControl = new Enable();
    private Import anImport;
    private Export anExport;
    private Delete anDelete;
    public void importFile(String url, User createdBy) throws Exception  {
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_AOUTHORIZE_EXEPTION_MESSAGE);
        }
     FileService.doImport(url,createdBy,versionControl);
    }

    public SystemFile exportFileByName(String fileName, String type, User createdBy) throws Exception {
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_AOUTHORIZE_EXEPTION_MESSAGE);
        }
        setAnExport(new Name());
       return (SystemFile) anExport.export(fileName,type, createdBy);
    }
    public ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_AOUTHORIZE_EXEPTION_MESSAGE);
        }
        setAnExport(new Category());
        return (ArrayList<SystemFile>) anExport.export(categoryName, categoryType, createdBy);
    }
    public void deleteFileByName(String filename,String type, User createdBy)throws Exception  {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_AOUTHORIZE_EXEPTION_MESSAGE);
        }
        setAnDelete(new edu.najah.cap.Services.Delete.Name());
        anDelete.delete(filename,type,createdBy);
    }
    public void deleteFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        if(!Authorization.hasAdminPermission(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_HAS_ADMIN_PERMISSION_EXEPTION_MESSAGE);
        }
        setAnDelete(new edu.najah.cap.Services.Delete.Category());
        anDelete.delete(categoryName,categoryType, createdBy);
    }

    public void classifyFileBySize(SystemFile file, User createdBy)throws Exception{
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_AOUTHORIZE_EXEPTION_MESSAGE);
        }
        FileClassifier.classifyFileBySize(file);
    }
    public void classifyFileByType(SystemFile file, User createdBy) throws Exception{
        if(!Authorization.isAuthorized(createdBy)){
            throw new AuthorizationExeption(Constant.USER_NOT_AOUTHORIZE_EXEPTION_MESSAGE);
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
            throw new AuthorizationExeption(Constant.USER_NOT_HAS_ADMIN_PERMISSION_EXEPTION_MESSAGE);
        }
        FileService.doRollBack(fileName,version);
    }

    public void setVersionControl(VersionControl versionControl,User user) throws Exception {
        if(!Authorization.hasAdminPermission(user)){
            throw new AuthorizationExeption(Constant.USER_NOT_HAS_ADMIN_PERMISSION_EXEPTION_MESSAGE);
        }
        this.versionControl = versionControl;
    }

    public void setAnImport(Import anImport) {
        this.anImport = anImport;
    }

    public void setAnExport(Export anExport) {
        this.anExport = anExport;
    }

    public void setAnDelete(Delete anDelete) {
        this.anDelete = anDelete;
    }
}
