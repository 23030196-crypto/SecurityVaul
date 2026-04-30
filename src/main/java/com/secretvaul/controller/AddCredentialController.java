package com.secretvaul.controller;

import com.secretvaul.model.Usuario;
import com.secretvaul.service.ICredencialService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controlador para registrar Credenciales
 */
public class AddCredentialController {

    @FXML private TextField     txtSitio;
    @FXML private TextField     txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private TextField     txtNotas;
    @FXML private Label         lblMensaje;

    private Usuario            usuarioActivo;
    private ICredencialService credencialService;

    public void inicializar(Usuario usuario, ICredencialService service) {
        this.usuarioActivo     = usuario;
        this.credencialService = service;
    }

    @FXML
    private void handleGuardar() {
        String sitio    = txtSitio.getText().trim();
        String correo   = txtCorreo.getText().trim();
        String password = txtPassword.getText();
        String notas    = txtNotas.getText().trim();

        if (sitio.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Sitio y contraseña son obligatorios.");
            return;
        }

        try {
            credencialService.guardar(
                usuarioActivo.getIdUsuario(),
                sitio, correo, password, notas
            );
            cerrarVentana();
        } catch (Exception e) {
            lblMensaje.setText("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar(){
        cerrarVentana();
    }
    private void cerrarVentana(){
        ((Stage) txtSitio.getScene().getWindow()).close();
    }
}