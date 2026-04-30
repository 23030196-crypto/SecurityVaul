package com.secretvaul.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Cifra y descifra contraseñas usando AES con la clave activa de la sesión.
 * El resultado del cifrado se convierte a Base64 para poder guardarlo como
 * texto en MySQL, ya que el cifrado AES devuelve bytes que no son texto legible.
 */
public class AESUtil {

    private static final String ALGORITMO = "AES";

    private AESUtil() {}

    /**
     * Cifra un texto plano con la clave AES.
     * @param texto contraseña original del usuario
     * @return texto cifrado en Base64 (listo para guardar en BD)
     */
    public static String encrypt(String texto){
        try {
            SecretKey key= KeyManager.getKey();
            
            Cipher cipher =Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] cifrado=cipher.doFinal(texto.getBytes());
            return Base64.getEncoder().encodeToString(cifrado);
        } catch (Exception e){
            throw new RuntimeException("Error al cifrar: "+ e.getMessage());
        }
    }
    /**
     * Descifra un texto cifrado en Base64.
     * @param textoCifrado el valor guardado en la base de datos
     * @return la contraseña original en texto plano
     */
    
    public static String decrypt(String textoCifrado) {
        
        try {
            SecretKey key = KeyManager.getKey();

            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(textoCifrado);
            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar: " + e.getMessage());
            
        }
    }
}