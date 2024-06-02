package Managers;


import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordManager {
    public static String enterPassword() {
        Console console = System.console();
        char[] passwordChars = console.readPassword();
        return new String(passwordChars);
    }

    public static String hashPassword(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-224");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] inputBytes = password.getBytes();
        byte[] hashedBytes = digest.digest(inputBytes);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}