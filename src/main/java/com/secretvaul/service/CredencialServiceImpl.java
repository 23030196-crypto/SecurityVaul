package com.secretvaul.service;
import com.secretvaul.crypto.AESUtil;
import com.secretvaul.dao.CredencialDAO;
import com.secretvaul.dao.DAOFactory;
import com.secretvaul.model.Credencial;
import com.secretvaul.service.strategy.BusquedaStrategy;
import com.secretvaul.service.strategy.BusquedaPorSitio;
import java.util.List;
/**
 * Servicio que maneja toda la lógica relacionada a las credenciales del baúl.
 *
 * Decidí poner esta capa entre el controlador y el DAO porque el controlador
 * no debería saber nada de cifrado — su trabajo es solo manejar la interfaz.
 * Aquí es donde cifro antes de guardar y descifro antes de mostrar,
 * de forma que si algún día cambio el algoritmo de cifrado, solo toco este archivo.
 */
public class CredencialServiceImpl implements ICredencialService {
    private final CredencialDAO dao = DAOFactory.createCredencialDAO();

    // Por defecto busco por sitio, cambia según lo que el usuario seleccione
    private BusquedaStrategy estrategia = new BusquedaPorSitio(dao);

    @Override
    public void guardar(int idUsuario,String sitio,String correo, String password ,String notas) {
        // Cifro la contraseña aquí antes de pasarla al DAO — el DAO nunca ve el texto plano
        String cifrado = AESUtil.encrypt(password);

        Credencial c = new Credencial.Builder()
                .idUsuario(idUsuario)
                .sitio(sitio)
                .correo(correo)
                .passwordCifrado(cifrado)
                .notas(notas)
                .build();
        dao.guardar(c);
    }

    @Override
    public void actualizar(Credencial credencial, String nuevaP) {
        credencial.setPasswordCifrado(AESUtil.encrypt(nuevaP));
        dao.actualizar(credencial);
    }
    @Override
    public void eliminar(int idCredencial) {
        dao.eliminar(idCredencial);
    }
    @Override
    public String descifrarPassword(Credencial credencial) {
        return AESUtil.decrypt(credencial.getPasswordCifrado());
    }
    @Override
    public List<Credencial> buscar(int idUsuario, String query) {
        return estrategia.buscar(idUsuario, query);
    }
    @Override
    public List<Credencial> listarTodas(int idUsuario) {
        return dao.listarTodas(idUsuario);
    }
    @Override
    public void setEstrategia(BusquedaStrategy estrategia) {
        this.estrategia = estrategia;
    }
}
