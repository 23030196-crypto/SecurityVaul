package com.secretvaul.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * hashes SHA-256.
 * Validar contraseña.
 */
public class SHA256Util {

    private SHA256Util() {}

    /**
     * Convierte un texto en su hash SHA-256 en formato hexadecimal.
     * @param input texto a hashear
     * @return hash de 64 caracteres hexadecimales
     */
    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());


            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible: " + e.getMessage());
        }
    }
}