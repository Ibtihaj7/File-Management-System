package edu.najah.cap.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static edu.najah.cap.Security.Encryption.encrypt;

public abstract class ASEDecryptionEncryption{
    public static String Encrypt(String textToEncrypt) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {

       return encrypt(textToEncrypt);


    }
    public static String decrypt(String TextToDecrypt) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException {

        return decrypt(TextToDecrypt);

    }
}