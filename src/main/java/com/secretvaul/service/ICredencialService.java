package com.secretvaul.service;
import com.secretvaul.model.Credencial;
import com.secretvaul.service.strategy.BusquedaStrategy;
import java.util.List;

/**
 * Patron Service Layer. Contrato para servicio de credenciales
 */
public interface ICredencialService {
    void guardar (int idUsuario, String sitio, String correo, String password, String notas);
    void actualizar (Credencial credencial, String nuevoPassword);
    void eliminar (int idCredencial);

    String descifrarPassword  (Credencial credencial);
    List<Credencial> buscar(int idUsuario, String query);
    List<Credencial> listarTodas(int idUsuario);
    void setEstrategia(BusquedaStrategy estrategia);
}