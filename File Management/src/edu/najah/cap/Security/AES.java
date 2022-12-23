package edu.najah.cap.Security;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public abstract class AES {

    public static String encodeBase64(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public static String decodeBase64(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
