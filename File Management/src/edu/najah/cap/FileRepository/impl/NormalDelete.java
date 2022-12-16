package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.Delete;

public class NormalDelete implements Delete {

    @Override
    public void delete(String filename, String category) {
        try {
            String query = "DELETE FROM files WHERE name = "+filename+" AND category = "+category+";";
            MySQLDatabase.getInstance().execute(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("delete successfully");
    }
}
