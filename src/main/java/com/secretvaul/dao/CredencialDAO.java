package com.secretvaul.dao;

import com.secretvaul.model.Credencial;
import java.util.List;

/**
 * Contrato de dao Credenciales
 */
public interface CredencialDAO{
    void guardar(Credencial credencial);
    void actualizar(Credencial credencial);
    void eliminar (int idCredencial);

    List <Credencial> buscarPorSitio(int idUsuario, String sitio);
    List<Credencial> buscarPorCorreo (int idUsuario, String correo);
    List <Credencial>listarTodas(int idUsuario);
}