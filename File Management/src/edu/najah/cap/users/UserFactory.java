package edu.najah.cap.users;

import edu.najah.cap.FileRepository.impl.NormalDelete;
import edu.najah.cap.FileRepository.impl.NormalExport;
import edu.najah.cap.FileRepository.impl.NormalImport;

public class UserFactory {

    public static User createUser(UserRole type){
        User user = null;
        if(type.equals(UserRole.ADMIN)){
            user = new User();
            user.setImporter(new NormalImport());
            user.setDeleter(new NormalDelete());
            user.setExporter(new NormalExport());
        }
        if(type.equals(UserRole.STAFF)){
            user = new User();
            user.setImporter(new NormalImport());
            user.setExporter(new NormalExport());
        }
        if(type.equals(UserRole.READER)){
            user = new User();
        }
        return user;
    }
}
