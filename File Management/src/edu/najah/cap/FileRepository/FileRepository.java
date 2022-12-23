package edu.najah.cap.FileRepository;

import edu.najah.cap.Services.FileService;
import edu.najah.cap.users.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class FileRepository {
    private String name;

    public FileRepository(String name) {
        this.name = name;
    }

 public void importFile(String url, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
     FileService.doImport(url,createdBy);
    }
    private SystemFile exportFile(String url, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException{
       return FileService.doExport(url, createdBy);
    }
    private void deleteFile(String url,User createdBy) {
        FileService.doDelete(url,createdBy);
    }

    private void viewFiles() throws SQLException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
        FileService.view();
    }

    private void RollBack(String url,User createdBy) throws SQLException {
        FileService.doRollBack(url,createdBy);
    }
}
