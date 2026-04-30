package com.secretvaul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * App
 */
public class App extends Application {

    private static Stage stagePrincipal;

    @Override
    public void start(Stage stage) throws Exception {
        stagePrincipal = stage;
        cambiarVista("LoginView");
        stage.setTitle("SecureVault");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Cambia la vista dependiendo del archivo
     */
    public static void cambiarVista(String nombre) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("view/" + nombre + ".fxml")
        );
        Scene scene = new Scene(loader.load());
        stagePrincipal.setScene(scene);
    }

    /**
     * Devuelve el controller para pasarle datos.
     */
    public static <T> T cambiarVistaConController(String nombre) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("view/" + nombre + ".fxml")
        );

        Scene scene = new Scene(loader.load());
        stagePrincipal.setScene(scene);
        return loader.getController();
    }
    public static Stage getStage() {
        return stagePrincipal;
    }
    public static void main(String[] args) {
        launch(args);
    }
}