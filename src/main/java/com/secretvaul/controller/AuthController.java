package com.secretvaul.controller;
import com.secretvaul.crypto.SHA256Util;
import com.secretvaul.model.Credencial;
import com.secretvaul.model.Usuario;
import com.secretvaul.service.ICredencialService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador para autenticcaciom.
 */
public class AuthController {

    @FXML private PasswordField txtPasswordMaestra;
    @FXML private Label lblError;
    @FXML private VBox panelResultado;
    @FXML private Label lblPasswordDescifrada;
    private Credencial credencial;
    private Usuario usuarioActivo;
    private ICredencialService credencialService;

    public void inicializar(Credencial credencial, Usuario usuario, ICredencialService service){
        this.credencial = credencial;
        this.usuarioActivo = usuario;
        this.credencialService= service;
    }
    @FXML
    private void handleVerificar() {
        String ingresada = txtPasswordMaestra.getText();

        String hashIngresado = SHA256Util.hash(ingresada);
        if (!hashIngresado.equals(usuarioActivo.getPasswordHash())) {
            lblError.setText("Contraseña incorrecta.");
            return;
        }
        String descifrada = credencialService.descifrarPassword(credencial);
        lblPasswordDescifrada.setText(descifrada);
        panelResultado.setVisible(true);
        lblError.setText("");
    }
    @FXML
    private void handleCerrar() {
        ((Stage) txtPasswordMaestra.getScene().getWindow()).close();
    }
}