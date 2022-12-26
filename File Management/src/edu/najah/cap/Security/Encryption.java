package edu.najah.cap.Security;

import edu.najah.cap.App;

import java.util.Base64;

public abstract class Encryption {
    public static String encodeBase64(String message) {
        App.LOGGER.debug("EncodeBase 64 message: " + message);

        String encodedMessage= Base64.getEncoder().encodeToString(message.getBytes());
        App.LOGGER.debug("Encoded message: " + encodedMessage);
        return encodedMessage;

    }
}
