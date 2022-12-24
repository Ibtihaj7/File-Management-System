package edu.najah.cap.Security;

import java.util.Base64;

public abstract class Decryption {
    public static String decodeBase64(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
