package edu.najah.cap.Services.Export;

import edu.najah.cap.Users.User;

public interface Export<T> {
    T export(String name, String type, User user) throws Exception;
}
