package com.secretvaul.dao;
import java.sql.*;

import com.secretvaul.configuration.ConexionMySQL;
import com.secretvaul.model.Credencial;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de sql para contrato dao credencial
 */
public class CredencialDAOImpl implements CredencialDAO {

    private final Connection conn = ConexionMySQL.getInstance().getConnection();

    @Override
    public void guardar ( Credencial c){
        String sql = "INSERT INTO credenciales (id_usuario, sitio, correo, password_cifrado, notas) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getIdUsuario());
            ps.setString(2, c.getSitio());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getPasswordCifrado());
            ps.setString(5, c.getNotas());
            ps.executeUpdate();
        } catch (SQLException e){

            throw new RuntimeException ("Error guardando credencial: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Credencial c) {
        String sql = "UPDATE credenciales SET sitio=?, correo=?, password_cifrado=?, notas=? WHERE id_credencial=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getSitio());
            ps.setString(2, c.getCorreo());
            ps.setString(3, c.getPasswordCifrado());
            ps.setString(4, c.getNotas());
            ps.setInt(5, c.getIdCredencial());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando credencial: " + e.getMessage());
        }


    }
    @Override
    public void eliminar(int idCredencial) {
        String sql="DELETE FROM credenciales WHERE id_credencial = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCredencial);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando credencial: " + e.getMessage());
        }

    }
    @Override
    public List <Credencial>buscarPorSitio(int idUsuario, String sitio) {
        String sql = "SELECT * FROM credenciales WHERE id_usuario=? AND sitio LIKE ?";
        return ejecutarBusqueda(sql, idUsuario, "%" + sitio + "%");
    }
    @Override
    public List<Credencial>buscarPorCorreo(int idUsuario,  String correo) {
        String sql = "SELECT * FROM credenciales WHERE id_usuario=? AND correo LIKE ?";
        return ejecutarBusqueda(sql, idUsuario, "%" + correo + "%");
    }
    @Override
    public List<Credencial> listarTodas(int idUsuario) {
        String sql = "SELECT * FROM credenciales WHERE id_usuario=?";
        List<Credencial> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Error listando credenciales: " + e.getMessage());
        }
        return lista;
    }

    private List<Credencial> ejecutarBusqueda(String sql, int idUsuario, String valor) {
        List<Credencial> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, valor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error en búsqueda: " + e.getMessage());
        }
        return lista;
    }

    private Credencial mapear(ResultSet rs) throws SQLException {
        Credencial c = new Credencial();

        c.setIdCredencial(rs.getInt("id_credencial"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setSitio(rs.getString("sitio"));
        c.setCorreo(rs.getString("correo"));
        c.setPasswordCifrado(rs.getString("password_cifrado"));
        c.setNotas(rs.getString("notas"));
        return c;
    }
}