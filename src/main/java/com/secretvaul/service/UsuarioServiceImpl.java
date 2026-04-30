package com.secretvaul.service;

import com.secretvaul.crypto.KeyManager;
import com.secretvaul.crypto.SHA256Util;
import com.secretvaul.dao.DAOFactory;
import com.secretvaul.dao.UsuarioDAO;
import com.secretvaul.model.Usuario;
/**
 * Maneja el registro y la autenticación del usuario maestro.
 *
 * Al autenticar, además de verificar el hash SHA-256, inicializo la clave AES
 * en memoria para que el resto de la sesión pueda cifrar y descifrar sin
 * volver a pedir la contraseña. Cuando el usuario cierra sesión esa clave
 * se borra de memoria (ver KeyManager.cerrarSesion).
 */
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioDAO dao = DAOFactory.createUsuarioDAO();
    private Usuario usuarioActivo;

    @Override
    public boolean autenticar(String username, String password) {
        Usuario usuario = dao.buscarPorUsername(username);
        if (usuario ==null) return false;

        String hashIngresado =SHA256Util.hash(password);
        if (!hashIngresado.equals(usuario.getPasswordHash())) return false;

        KeyManager.inicializarClave(password, usuario.getSalt());
        usuarioActivo = usuario;
        return true;
    }

    @Override
    public void registrar(String username, String password) {
        String salt = KeyManager.generarSalt();
        String hash = SHA256Util.hash(password);

        Usuario nuevo = new Usuario();
        nuevo.setUsername(username);
        nuevo.setPasswordHash(hash);
        nuevo.setSalt(salt);
        dao.guardar(nuevo);
    }
    @Override
    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }
}