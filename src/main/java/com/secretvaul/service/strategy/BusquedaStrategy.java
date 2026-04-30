package com.secretvaul.service.strategy;

import com.secretvaul.model.Credencial;
import java.util.List;

/**
 * Patron Strategy. Estrategia de búsqueda intercambiable por boton
 */
public interface BusquedaStrategy {
    List<Credencial> buscar(int idUsuario, String query);
}