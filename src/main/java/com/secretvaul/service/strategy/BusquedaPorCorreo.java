package com.secretvaul.service.strategy;
import com.secretvaul.dao.CredencialDAO;
import com.secretvaul.model.Credencial;
import java.util.List;
/**
 * Busca credenciales filtrando por correo.
 */

public class BusquedaPorCorreo implements BusquedaStrategy {
    private final CredencialDAO dao;
    public BusquedaPorCorreo(CredencialDAO dao){
        this.dao = dao;
    }
    @Override
    public List<Credencial>buscar(int idUsuario, String query) {
        return dao.buscarPorCorreo(idUsuario, query);
    }
}