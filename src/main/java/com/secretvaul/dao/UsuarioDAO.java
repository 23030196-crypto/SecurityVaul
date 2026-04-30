package com.secretvaul.dao;

import com.secretvaul.model.Usuario;

/**
 * Contrato de acceso a datos para la entidad Usuario.
 */
public interface UsuarioDAO {
    void guardar(Usuario usuario);
    Usuario buscarPorUsername(String username);
}