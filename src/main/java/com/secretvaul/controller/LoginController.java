package com.secretvaul.controller;

import com.secretvaul.App;
import com.secretvaul.service.IUsuarioService;
import com.secretvaul.service.UsuarioServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 * controlador de login
 */
public class LoginController {
    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label         lblError;

    private final IUsuarioService usuarioService = new UsuarioServiceImpl();

    @FXML
    private void handleLogin() {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Completa todos los campos.");
            return;
        }
        if (usuarioService.autenticar(user, pass)) {
            try {
                DashboardController controller = App.cambiarVistaConController("DashboardView");
                controller.inicializar(usuarioService.getUsuarioActivo());
            } catch (Exception e) {
                lblError.setText("Error al cargar el dashboard.");
                e.printStackTrace();
            }
        } else {
            lblError.setText("Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void handleRegistro() {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText();
        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Completa los campos para registrarte.");
            return;
        }
        try {
            usuarioService.registrar(user, pass);
            lblError.setStyle("-fx-text-fill: #208C12;");
            lblError.setText("Usuario registrado. Ya puedes iniciar sesión.");
        } catch (Exception e) {
            lblError.setText("Error: ese usuario ya existe.");
        }
    }
}