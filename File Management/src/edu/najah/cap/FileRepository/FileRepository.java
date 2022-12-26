package edu.najah.cap.FileRepository;

import edu.najah.cap.Exceptions.AuthorizationExeption;
import edu.najah.cap.Exceptions.CategoryNotFoundExeption;
import edu.najah.cap.Security.Authorization;
import edu.najah.cap.Services.Delete.CategoryDeleter;
import edu.najah.cap.Services.Delete.Delete;
import edu.najah.cap.Services.Delete.NameDeleter;
import edu.najah.cap.Services.Export.CategoryExporter;
import edu.najah.cap.Services.Export.Export;
import edu.najah.cap.Services.Export.NameExporter;
import edu.najah.cap.Services.FileService;
import edu.najah.cap.Users.User;
import edu.najah.cap.VersionControl.impl.Enable;
import edu.najah.cap.VersionControl.intf.VersionControl;
import edu.najah.cap.classification.FileClassifier;

import java.util.ArrayList;

import static edu.najah.cap.Constant.Constant.USER_NOT_AUTHORIZE_EXCEPTION_MESSAGE;

public class FileRepository {

    private VersionControl versionControl = new Enable();
    private Export exporter;
    private Delete deleter;

    public void importFile(String url, User createdBy) throws Exception  {
        AuthorizeUser(createdBy);
        versionControl.importWithVersionControl(url, createdBy);
    }

    public SystemFile exportFileByName(String fileName, String type, User createdBy) throws Exception {
        AuthorizeUser(createdBy);
        setAnExport(new NameExporter());
        return (SystemFile) exporter.export(fileName,type, createdBy);
    }
    public ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        AuthorizeUser(createdBy);
        setAnExport(new CategoryExporter());
        return (ArrayList<SystemFile>) exporter.export(categoryName, categoryType, createdBy);
    }
    public void deleteFileByName(String filename,String type, User createdBy)throws Exception  {
        AuthorizeAdmin(createdBy);
        setAnDelete(new NameDeleter());
        deleter.delete(filename,type,createdBy);
    }
    public void deleteFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        AuthorizeAdmin(createdBy);
        setAnDelete(new CategoryDeleter());
        deleter.delete(categoryName,categoryType, createdBy);
    }

    public void classifyFileBySize(SystemFile file, User createdBy)throws Exception{
        AuthorizeUser(createdBy);
        FileClassifier.classifyFileBySize(file);
    }
    public void classifyFileByType(SystemFile file, User createdBy) throws Exception{
        AuthorizeUser(createdBy);
        FileClassifier.classifyFileByType(file);
    }
    public void classifyFileByCategory(SystemFile file, String categoryName,User user)throws Exception{
        AuthorizeUser(user);
        FileClassifier.classifyFileByCategory(file,categoryName);
    }

    public void viewAllFiles()  {
        FileService.view();
    }
    public void viewFileByClassification(String classificationName,String classificationType) {
        try {
            FileService.viewFileByClassification(classificationName, classificationType);
        }catch (CategoryNotFoundExeption e){
            System.err.println(e);
        }
    }

    public void RollBack(String fileName,int version,User createdBy) throws Exception  {
        AuthorizeAdmin(createdBy);
        FileService.doRollBack(fileName,version);
    }

    public void setVersionControl(VersionControl versionControl,User user) throws Exception {
        AuthorizeAdmin(user);
        this.versionControl = versionControl;
    }

    private void setAnExport(Export exporter) {
        this.exporter = exporter;
    }

    private void setAnDelete(Delete deleter) {
        this.deleter = deleter;
    }

    private void AuthorizeUser(User user)throws AuthorizationExeption{
        if(!Authorization.isAuthorized(user)){
            throw new AuthorizationExeption(USER_NOT_AUTHORIZE_EXCEPTION_MESSAGE);
        }
    }
    private void AuthorizeAdmin(User user)throws AuthorizationExeption{
        if(!Authorization.hasAdminPermission(user)){
            throw new AuthorizationExeption(USER_NOT_AUTHORIZE_EXCEPTION_MESSAGE);
        }
    }
}
