package edu.najah.cap.FileRepository.intf;

import edu.najah.cap.File.SystemFile;


import java.sql.SQLException;

public interface ExportBehaviour {
    SystemFile export(String filename, String category) throws SQLException;
}
