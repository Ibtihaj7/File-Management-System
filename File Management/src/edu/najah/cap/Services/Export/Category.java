package edu.najah.cap.Services.Export;

import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Users.User;

import java.util.ArrayList;

public class Category implements Export<ArrayList<SystemFile>> {
    @Override
    public ArrayList<SystemFile> export(String name, String type, User user) throws Exception {
        return CategoryFactory.getCategoryType(name, type);
    }
}
