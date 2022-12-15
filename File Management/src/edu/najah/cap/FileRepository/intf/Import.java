package edu.najah.cap.FileRepository.intf;

import edu.najah.cap.Database.intf.IDatabase;

import java.sql.SQLException;

public interface Import {
    public void importFile(String pathName) throws SQLException;
}
