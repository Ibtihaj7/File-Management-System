package edu.najah.cap.FileRepository.intf;

import java.io.File;
import java.sql.SQLException;

public interface Export {
    public File export(String filename, String category) throws SQLException;
}
