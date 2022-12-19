package edu.najah.cap.users;

import edu.najah.cap.File.SystemFile;
import edu.najah.cap.FileRepository.intf.DeleteBehaviour;
import edu.najah.cap.FileRepository.intf.ExportBehaviour;
import edu.najah.cap.FileRepository.intf.ImportBehaviour;


import java.sql.SQLException;

public class User {
    protected String name;
    protected String email;
    protected String password;

    protected ImportBehaviour importer;
    protected ExportBehaviour exporter;
    protected DeleteBehaviour deleter;


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
    public SystemFile exportFile(String filename, String category) throws SQLException {
        if(exporter != null){
            return exporter.export(filename,category);
        }
        return null;
    }

    public void setImporter(ImportBehaviour importer) {
        this.importer = importer;
    }

    public void setExporter(ExportBehaviour exporter) {
        this.exporter = exporter;
    }

    public void setDeleter(DeleteBehaviour deleter) {
        this.deleter = deleter;
    }



    public void viewFiles() {


    }
}
