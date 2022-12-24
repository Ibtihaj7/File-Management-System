package edu.najah.cap.Security;

import java.util.Base64;

public abstract class Encryption {
    public static String encodeBase64(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }
}
