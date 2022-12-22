package edu.najah.cap.FileRepository;

import edu.najah.cap.Services.FileService;
import edu.najah.cap.users.User;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileRepository {
    private String name;

    public FileRepository(String name) { this.name = name; }

    public void importFile(String url, User createdBy) throws SQLException {
        FileService.doImport(url,createdBy);
    }
    public SystemFile exportFileByName(String url,User createdBy) throws SQLException {
       return FileService.doExportByFileName(url,createdBy);
    }
//    public ArrayList<SystemFile> exportFileByCategory(String categoryName, String categoryType,User createdBy) throws SQLException {
//        return FileService.doExportByCategory(categoryName,categoryType,createdBy);
//    }
    public void deleteFile(String url,User createdBy) {
        FileService.doDelete(url,createdBy);
    }

    public void viewFiles() throws SQLException {
        FileService.view();
    }

    public void RollBack(String url,User createdBy) throws SQLException {
        FileService.doRollBack(url,createdBy);
    }
//    public void classifyFile(String fileName) throws SQLException {
//        Scanner input = new Scanner(System.in);
//        if (classification != null){
//            System.out.println("How would you like to classify "+fileName+" file ? (size , type or new category) enter one of them please ");
//            String choice = input.next();
//            if(!(choice.equals("size") || choice.equals("type") || choice.equals("new category"))){
//                System.out.println("bad choice");
//                return;
//            }
//            if(choice.equals("size")){
//                classification.classifySize(fileName);
//                return;
//            }
//            if(choice.equals("type")){
//                return;
//            }
//        }
    }


