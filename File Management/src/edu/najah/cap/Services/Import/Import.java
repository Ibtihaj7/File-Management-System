package edu.najah.cap.Services.Import;

import edu.najah.cap.FileRepository.SystemFile;

import java.sql.ResultSet;

public interface Import {
    void Import(SystemFile encryptedFile, ResultSet result);
}
