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

import static edu.najah.cap.Constant.Constant.LOGIN_WAS_NOT_CORRECT;
import static edu.najah.cap.Constant.Constant.USER_NOT_AUTHORIZE_EXCEPTION_MESSAGE;

public class FileRepository {

    private VersionControl versionControl = new Enable();
    private Export exporter;
    private Delete deleter;

    public synchronized void importFile(String url, User createdBy) throws Exception  {
        Authenticate(createdBy);
        AuthorizeUser(createdBy);
            versionControl.importWithVersionControl(url, createdBy);
    }

    public  SystemFile exportFileByName(String fileName, String type, User createdBy) throws Exception {
        Authenticate(createdBy);
        AuthorizeUser(createdBy);
        setAnExport(new NameExporter());
        return (SystemFile) exporter.export(fileName,type, createdBy);
    }
    public  ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        Authenticate(createdBy);
        AuthorizeUser(createdBy);
        setAnExport(new CategoryExporter());
        return (ArrayList<SystemFile>) exporter.export(categoryName, categoryType, createdBy);
    }
    public synchronized void deleteFileByName(String filename,String type, User createdBy)throws Exception  {
        Authenticate(createdBy);
        AuthorizeAdmin(createdBy);
        setAnDelete(new NameDeleter());
        deleter.delete(filename,type,createdBy);
    }
    public synchronized void deleteFileByCategory(String categoryName, String categoryType, User createdBy)throws Exception  {
        Authenticate(createdBy);
        AuthorizeAdmin(createdBy);
        setAnDelete(new CategoryDeleter());
        deleter.delete(categoryName,categoryType, createdBy);
    }

    public  void classifyFileBySize(SystemFile file, User createdBy)throws Exception{
        Authenticate(createdBy);
        AuthorizeUser(createdBy);
        FileClassifier.classifyFileBySize(file);
    }
    public void classifyFileByType(SystemFile file, User createdBy) throws Exception{
        Authenticate(createdBy);
        AuthorizeUser(createdBy);
        FileClassifier.classifyFileByType(file);
    }
    public void classifyFileByCategory(SystemFile file, String categoryName,User user)throws Exception{
        Authenticate(user);
        AuthorizeUser(user);
        FileClassifier.classifyFileByCategory(file,categoryName);
    }

    public void viewAllFiles(User user)throws Exception {
        Authenticate(user);
        FileService.view(user);
    }
    public void viewFileByClassification(String classificationName,String classificationType, User user)throws Exception {
        Authenticate(user);
        try {
            FileService.viewFileByClassification(classificationName, classificationType,user);
        }catch (CategoryNotFoundExeption e){
            System.err.println(e);
        }
    }

    public synchronized void RollBack(String fileName,String type, int version,User createdBy) throws Exception  {
        AuthorizeAdmin(createdBy);
        FileService.doRollBack(fileName,type,version);
    }

    public synchronized void setVersionControl(VersionControl versionControl,User user) throws Exception {
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
    private void Authenticate(User user) throws AuthorizationExeption{
        if(!user.hasLogged()){
            throw new AuthorizationExeption(user.getUsername() + LOGIN_WAS_NOT_CORRECT);
        }
    }
}
