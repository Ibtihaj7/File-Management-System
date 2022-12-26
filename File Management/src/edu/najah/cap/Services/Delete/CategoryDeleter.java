package edu.najah.cap.Services.Delete;

import edu.najah.cap.Users.User;

public class CategoryDeleter implements Delete{
    @Override
    public void delete(String filename, String type, User user) {
        try {
            CategoryFactory.doDelete(filename, type, user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
