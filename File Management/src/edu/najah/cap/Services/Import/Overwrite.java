package edu.najah.cap.Services.Import;

import edu.najah.cap.App;
import edu.najah.cap.Database.impl.MySQLDatabase;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Services.FileServiceUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Overwrite implements Import{
    @Override
    public void doAction(SystemFile encryptedFile, ResultSet result) {
        String query = "Update files SET type = ?, size = ?, path = ? WHERE name = ?";
        try {
            if (!FileServiceUtil.isEmpty(result)) {
                MySQLDatabase.getInstance().executeStatement(query, Arrays.asList(encryptedFile.getType(), encryptedFile.getSize()
                        , encryptedFile.getPath(), encryptedFile.getName()));

                App.LOGGER.debug("The file has been imported successfully");
            }
            else App.LOGGER.warn("there is problem and the file not stored.");

        }catch (SQLException e){
            App.LOGGER.error("error in "+e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
