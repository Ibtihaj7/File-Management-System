package edu.najah.cap.Services.Import;

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

                System.out.println("The file has been imported successfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
