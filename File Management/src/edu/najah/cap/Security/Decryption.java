package edu.najah.cap.Security;

import edu.najah.cap.App;

import java.util.Base64;

public abstract class Decryption {
    public static String decodeBase64(String encodedMessage) {
        App.LOGGER.debug("Decoding base64 encoded message: " + encodedMessage);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        String decodedString = new String(decodedBytes);
      App.LOGGER.debug("Decoded message: " + decodedString);

        return decodedString;
    }
}
