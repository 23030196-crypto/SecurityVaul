package com.secretvaul.service;

import com.secretvaul.model.Usuario;

/**
 * Patron Service Layer. Contrats para la logica de servicio de usuario
 */
public interface IUsuarioService {
    boolean autenticar(String username, String password);
    void registrar(String username, String password);

    Usuario getUsuarioActivo();
}