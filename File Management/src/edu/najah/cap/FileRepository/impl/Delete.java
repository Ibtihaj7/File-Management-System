package edu.najah.cap.FileRepository.impl;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.intf.DeleteBehaviour;
import edu.najah.cap.Security.Authorization;

public class Delete implements DeleteBehaviour {

    @Override
    public void delete(String filename, String category) {
        try {
            if(!Authorization.hasAdminPermission()){
                return;
            }
            String query = "DELETE FROM files WHERE name = "+filename+" AND category = "+category+";";
            MySQLDatabase.getInstance().insertDeleteQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("delete successfully");
    }
    public void delete(String filename) {
        delete(filename,"null");
    }
}
