package edu.najah.cap.users;

import edu.najah.cap.FileRepository.impl.Delete;
import edu.najah.cap.FileRepository.impl.Export;
import edu.najah.cap.FileRepository.impl.Import;

public class UserFactory {

    public static User createUser(UserRole type){
        User user = null;
        if(type.equals(UserRole.ADMIN)){
            user = new User();
            user.setImporter(new Import());
            user.setDeleter(new Delete());
            user.setExporter(new Export());
        }
        if(type.equals(UserRole.STAFF)){
            user = new User();
            user.setImporter(new Import());
            user.setExporter(new Export());
        }
        if(type.equals(UserRole.READER)){
            user = new User();
            user.viewFiles();
        }
        return user;
    }
}
