package edu.najah.cap.FileRepository;

import edu.najah.cap.Services.FileService;
import edu.najah.cap.users.User;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileRepository {
    private String name;
    private static FileService fileService;

    public FileRepository(String name) { this.name = name; }

    private void importFile(String url, User createdBy) throws SQLException {
        fileService.doImport(url,createdBy);
    }
    private SystemFile exportFileByName(String url,User createdBy) throws SQLException {
       return fileService.doExportByFileName(url,createdBy);
    }
    private ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType,User createdBy) throws SQLException {
        return fileService.doExportByCategory(categoryName,categoryType,createdBy);
    }
    private void deleteFile(String url,User createdBy) {
        fileService.doDelete(url,createdBy);
    }

    private void viewFiles() throws SQLException {
        fileService.view();
    }

    private void RollBack(String url,User createdBy) throws SQLException {
        fileService.doRollBack(url,createdBy);
    }
}
