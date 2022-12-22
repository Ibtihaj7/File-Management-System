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
import java.util.ArrayList;
import java.util.HashMap;

public class FileRepository {
    private String name;
    private static FileService fileService;

    public FileRepository(String name) {
        this.name = name;
    }

    private void importFile(String url, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
        fileService.doImport(url,createdBy);
    }
    private SystemFile exportFile(String url, User createdBy) throws SQLException , NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException{
       return fileService.doExport(url, createdBy);
    }
    private void deleteFile(String url,User createdBy) {
        fileService.doDelete(url,createdBy);
    }

    private void viewFiles() throws SQLException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {
        fileService.view();
    }

    private void RollBack(String url,User createdBy) throws SQLException {
        fileService.doRollBack(url,createdBy);
    }
}
