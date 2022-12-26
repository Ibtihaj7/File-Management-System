package edu.najah.cap.VersionControl.impl;

import edu.najah.cap.Services.FileService;
import edu.najah.cap.Users.User;
import edu.najah.cap.VersionControl.intf.VersionControl;

public class Disable implements VersionControl {

    @Override
    public void importWithVersionControl(String url, User user) throws Exception {
        FileService.doImport(url,user,this);
    }
}
