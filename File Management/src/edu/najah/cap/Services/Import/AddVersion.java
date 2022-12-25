package edu.najah.cap.Services.Import;

import edu.najah.cap.App;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;

import java.sql.ResultSet;
import java.util.Arrays;

public class AddVersion implements Import{
    @Override
    public void doAction(SystemFile encryptedFile, ResultSet result) {
        try {
            int newVersion = result.getInt("version");
            while (result.next()) {
                newVersion = result.getInt("version");
            }
            newVersion += 1;
            String query = "INSERT INTO files VALUES (?, ?, ?, ?, ?)";
            MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(encryptedFile.getName(),encryptedFile.getType()
                    ,encryptedFile.getSize(),encryptedFile.getPath(),newVersion));
        }catch (Exception e){
            App.LOGGER.error("error while insert a file.");
            System.err.println(e.getMessage());
        }
        App.LOGGER.info("The file has been imported successfully");
    }
}
