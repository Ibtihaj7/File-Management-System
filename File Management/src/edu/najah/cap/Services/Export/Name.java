package edu.najah.cap.Services.Export;

import edu.najah.cap.Exceptions.FileNotFoundExeption;
import edu.najah.cap.FileRepository.SystemFile;
import edu.najah.cap.Security.Encryption;
import edu.najah.cap.Services.FileServiceUtil;
import edu.najah.cap.Users.User;

import java.sql.ResultSet;

public class Name implements Export<SystemFile> {
    @Override
    public SystemFile export(String name, String type, User user) throws Exception {
        String encryptedFileName= Encryption.encodeBase64(name);

        ResultSet statement = FileServiceUtil.findFileByName(encryptedFileName, type);

        if(FileServiceUtil.isEmpty(statement)){
            throw new FileNotFoundExeption("There is no file with this name.");
        }
        return FileServiceUtil.findCurrentFile(statement);
    }
}
