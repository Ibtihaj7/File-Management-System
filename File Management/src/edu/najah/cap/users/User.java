package edu.najah.cap.users;

import edu.najah.cap.FileRepository.intf.Delete;
import edu.najah.cap.FileRepository.intf.Export;
import edu.najah.cap.FileRepository.intf.Import;

import java.sql.SQLException;

public class User {
    protected String name;
    protected String email;
    protected String password;

    protected Import importer;
    protected Export exporter;
    protected Delete deleter;


    public void importFile(String url) throws SQLException {
        if(importer != null){
            importer.importFile(url);
        }
    }
    public void deleteFile(String filename, String category){
        if(deleter != null){
            deleter.delete(filename,category);
        }
    }
    public void exportFile(String filename, String category){
        if(exporter != null){
            exporter.export(filename,category);
        }
    }

    public void setImporter(Import importer) {
        this.importer = importer;
    }

    public void setExporter(Export exporter) {
        this.exporter = exporter;
    }

    public void setDeleter(Delete deleter) {
        this.deleter = deleter;
    }



    public void viewFiles() {

    }
}
