package edu.najah.cap.Services.Delete;

import edu.najah.cap.App;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.Users.User;

import java.util.Arrays;

public class NameDeleter implements Delete {
    @Override
    public void delete(String filename, String type, User user) {
        String fileNameEncoded = Encryption.encodeBase64(filename);
        App.LOGGER.info("Received request to delete file: " + filename + " of type: " + type + " from user: " + user.getUsername());
        String query = "DELETE FROM files WHERE name = ? AND type = ?";
        try {
            MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(fileNameEncoded, type));
            App.LOGGER.info("Successfully deleted file: " + filename);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("File deleted successfully");
    }
}
