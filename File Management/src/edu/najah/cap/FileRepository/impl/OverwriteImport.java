package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.intf.IDatabase;
import edu.najah.cap.FileRepository.intf.Import;
import edu.najah.cap.Security.Authorization;

public class OverwriteImport implements Import {
    @Override
    public void importFile(String pathName){
        if(!Authorization.hasAdminPermission()){
            return;
        }
    }
}
