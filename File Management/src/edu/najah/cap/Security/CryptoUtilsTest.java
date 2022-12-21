package edu.najah.cap.Security;

import java.io.File;

public class CryptoUtilsTest {
    public static void main(String[] args) {
        String key = "MbQeThWmZq4t6w9z$C&F)J@NcRfUjXn3";
        File inputFile = new File("C:\\Users\\user\\Downloads\\fileeee.txt");
        File encryptedFile = new File("fileeee.encrypted");
        File decryptedFile = new File("fileeee.decrypted");

        try {
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
            CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
