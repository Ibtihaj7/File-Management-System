package edu.najah.cap.VersionControl.intf;

import edu.najah.cap.Users.User;

public interface VersionControl {
    void doVersionControl(String url, User user) throws Exception;
}
