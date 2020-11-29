package at.campus.ads.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * This class has implemented by Sergey Kargopolov
 * the main source code is available in https://www.appsdeveloperblog.com/encrypt-user-password-example-java/
 */
public class PasswordUtils {

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SALT = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    public static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public static String generateSecurePassword(String password) throws Exception {
        byte[] securePassword = hash(password.toCharArray(), SALT.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public static boolean verifyUserPassword(String providedPassword, String securedPassword) throws Exception {
        String newSecurePassword = generateSecurePassword(providedPassword);
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }

    private static byte[] hash(char[] password, byte[] salt) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
        return secretKeyFactory.generateSecret(keySpec).getEncoded();
    }
}
