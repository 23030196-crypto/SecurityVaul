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
     * Cambia la vista dependiendo del archivo FXML.
     * Aplico el CSS global aquí para no repetirlo en cada FXML —
     * cualquier escena que se cargue por este método recibe los estilos.
     */
    public static void cambiarVista(String nombre) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("view/" + nombre + ".fxml")
        );
        Scene scene = new Scene(loader.load());
        aplicarEstilos(scene);
        stagePrincipal.setScene(scene);
    }

    /**
     * Igual que cambiarVista pero devuelve el controller para pasarle datos.
     */
    public static <T> T cambiarVistaConController(String nombre) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("view/" + nombre + ".fxml")
        );
        Scene scene = new Scene(loader.load());
        aplicarEstilos(scene);
        stagePrincipal.setScene(scene);
        return loader.getController();
    }

    /**
     * Carga el archivo style.css y lo aplica a la escena recibida.
     * Lo centralizo aquí para no tener que referenciarlo en cada FXML.
     */
    private static void aplicarEstilos(Scene scene) {
        String css = App.class.getResource("view/style.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
    public static Stage getStage() {
        return stagePrincipal;
    }
    public static void main(String[] args) {
        launch(args);
    }
}