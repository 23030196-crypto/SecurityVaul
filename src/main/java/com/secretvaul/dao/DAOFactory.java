package com.secretvaul.dao;

/**
 * Patron Factory: Decide si crear la implementacion de DAO para Usuario o para Credenciales
 */
public class DAOFactory {
    private DAOFactory() {}

    public static UsuarioDAO createUsuarioDAO() {
        return new UsuarioDAOImpl();
    }
    public static CredencialDAO createCredencialDAO() {
        return new CredencialDAOImpl();
    }
}