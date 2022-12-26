package edu.najah.cap.VersionControl.intf;

import edu.najah.cap.Users.User;

public interface VersionControl {
    void importWithVersionControl(String url, User user) throws Exception;
}
