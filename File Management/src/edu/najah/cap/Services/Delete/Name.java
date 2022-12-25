package edu.najah.cap.Services.Delete;

import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.Users.User;

import java.util.Arrays;

public class Name implements Delete {
    @Override
    public void delete(String filename, String type, User user) {
        String fileNameEncoded = Encryption.encodeBase64(filename);
        String query = "DELETE FROM files WHERE name = ? AND type = ?";
        try {
            MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(fileNameEncoded, type));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("File deleted successfully");
    }
}
