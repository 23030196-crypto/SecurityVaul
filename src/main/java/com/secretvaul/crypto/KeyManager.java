package com.secretvaul.crypto;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Administra la clave AES que se usa para cifrar las contraseñas del baúl.
 *
 * La clave no se guarda en la BD — se genera cada vez que el usuario hace login
 * usando su contraseña + un salt único (PBKDF2). Esto significa que si alguien
 * roba la base de datos, no puede descifrar nada sin conocer la contraseña maestra.
 * Cuando el usuario cierra sesión, la clave se borra de memoria.
 */
public class KeyManager {

    private static SecretKey claveActiva;
    private KeyManager() {}

    /**
     * Guarda la clave al iniciar sesion
     */
    public static void inicializarClave(String passwordMaestro, String saltBase64) {
        claveActiva = derivarClave(passwordMaestro, saltBase64);
    }

    /**
     * Devuelve la clave activa de la sesión actual, no funciona si no se ha iniciado sesión
     */
    public static SecretKey getKey() {
        if (claveActiva == null) {
            throw new IllegalStateException("No hay sesión activa. Haz login primero.");
        }
        return claveActiva;
    }

    /**
     *Bora la clave cuando la sesión se cierra
     */
    public static void cerrarSesion() {
        claveActiva = null;
    }

    /**
     * Genera un salt aleatorio para un usuario nuevo.
     * @return es el salt en base64
     */
    public static String generarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Deriva una clave AES-256 a partir del password y su salt.
     */
    private static SecretKey derivarClave(String password, String saltBase64) {
        try {
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt,65536,256);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();

            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Error derivando clave: " + e.getMessage());
        }
    }
}