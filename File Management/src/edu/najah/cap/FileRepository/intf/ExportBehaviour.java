package edu.najah.cap.FileRepository.intf;

import edu.najah.cap.FileRepository.SystemFile;


import java.sql.SQLException;

public interface ExportBehaviour {
    SystemFile export(String filename, String category) throws SQLException;
    SystemFile export(String filename) throws SQLException;
}
