package com.secretvaul.dao;

import com.secretvaul.configuration.ConexionMySQL;
import com.secretvaul.model.Usuario;

import java.sql.*;

/**
 * Implementacion contrato DAO Usuario SQL
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    private final Connection conn = ConexionMySQL.getInstance().getConnection();

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password_hash, salt) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPasswordHash());
            ps.setString(3, usuario.getSalt());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error guardando usuario: " + e.getMessage());
        }
    }
    @Override
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setSalt(rs.getString("salt"));
                return u;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando usuario: " + e.getMessage());
        }

        return null;
    }
}