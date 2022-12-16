package edu.najah.cap.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
public  abstract class EncryptionAES256 {

    private static final String SECRET_KEY = "my_super_secret_key";
    private static final String SALT = "bbbhhhhhhhhhhh!!!!";

    public static String encrypt(String stringToEncrypt) {
        try {
            final String ALGORITHM="PBKDF2WithHmacSHA256";
            final int BLOCK_SIZE=128/8;
            final int PASSWORD_ITERATION_COUNT =65536;
            final int KEY_LENGTH=256;
            byte[] initializationVector =new byte[BLOCK_SIZE];
            IvParameterSpec initializationVectorSpec = new IvParameterSpec(initializationVector);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), PASSWORD_ITERATION_COUNT, KEY_LENGTH );
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,initializationVectorSpec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

}
