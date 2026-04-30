package com.secretvaul.service.strategy;

import com.secretvaul.dao.CredencialDAO;
import com.secretvaul.model.Credencial;
import java.util.List;
/**
 * Strategy para buscar por web
 */
public class BusquedaPorSitio implements BusquedaStrategy {

    private final CredencialDAO dao;
    public BusquedaPorSitio(CredencialDAO dao){
        this.dao =dao;
    }
    @Override
    public List<Credencial> buscar(int idUsuario, String query){
        return dao.buscarPorSitio(idUsuario,query);
    }
}