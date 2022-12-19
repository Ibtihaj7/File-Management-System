package edu.najah.cap.FileRepository.intf;

import java.sql.SQLException;

public interface ImportBehaviour {
    void importFile(String pathName) throws SQLException;
}
