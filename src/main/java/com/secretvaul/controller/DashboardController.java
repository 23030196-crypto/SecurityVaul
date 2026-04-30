package com.secretvaul.controller;

import com.secretvaul.App;
import com.secretvaul.dao.DAOFactory;
import com.secretvaul.model.Credencial;
import com.secretvaul.model.Usuario;
import com.secretvaul.service.CredencialServiceImpl;
import com.secretvaul.service.ICredencialService;
import com.secretvaul.service.strategy.BusquedaPorCorreo;
import com.secretvaul.service.strategy.BusquedaPorSitio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controlador principal de la ventana principal
 * Se encarga de cargar y mostrar las credenciales del usuario en la tabla,
 * manejar la búsqueda en tiempo real y abrir los diálogos de agregar/ver contraseña.
 * No accede directamente a la BD — eso lo delego al servicio para mantener
 * la lógica separada de la interfaz.
 */
public class DashboardController {

    @FXML private Label lblUsuario;
    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cmbFiltro;
    @FXML private TableView<Credencial> tablaCredenciales;
    @FXML private TableColumn<Credencial,String> colSitio;
    @FXML private TableColumn<Credencial, String> colCorreo;
    @FXML private TableColumn<Credencial, String> colPass;
    @FXML private TableColumn<Credencial, String> colNotas;
    @FXML private TableColumn<Credencial, String> colAcciones;
    @FXML private Label lblEstado;

    private ICredencialService credencialService = new CredencialServiceImpl();
    private Usuario usuarioActivo;

    /**
     * Recibe el usuario desde Login
     */
    public void inicializar(Usuario usuario) {
        this.usuarioActivo = usuario;
        lblUsuario.setText("Hola, " + usuario.getUsername());

        cmbFiltro.setItems(FXCollections.observableArrayList("Por sitio", "Por correo"));
        cmbFiltro.setValue("Por sitio");

        cargarT();
        llenarT();
    }

    /**
     * Configura las columnas de la tabla.
     * La columna de contraseña siempre muestra "••••••••" sin importar lo que se meta,.
     */
    private void cargarT() {
        colSitio.setCellValueFactory(c-> new SimpleStringProperty (c.getValue().getSitio()));
        colCorreo.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getCorreo()));
        colNotas.setCellValueFactory(c-> new SimpleStringProperty (c.getValue().getNotas()) );

        // La contraseña nunca se muestra directo — el usuario debe verificarse primero
        colPass.setCellValueFactory(c -> new SimpleStringProperty ("••••••••"));

        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");
            private final Button btnDel = new Button("X");
            private final HBox hbox   = new HBox(6, btnVer, btnDel);

            {
                btnVer.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius:4;");
                btnDel.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius:4;");

                btnVer.setOnAction(e -> mostrarPassword(getTableView().getItems().get(getIndex())));
                btnDel.setOnAction(e -> eliminarCredencial(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });
    }

    private void llenarT() {
        List<Credencial> lista = credencialService.listarTodas(usuarioActivo.getIdUsuario());
        tablaCredenciales.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void buscar() {
        String query = txtBuscar.getText().trim();
        if (query.isEmpty()) {
            llenarT();
            return;
        }
        List<Credencial> resultado = credencialService.buscar(usuarioActivo.getIdUsuario(), query);
        tablaCredenciales.setItems(FXCollections.observableArrayList(resultado));
    }

    /**
     * Cambia el tipo de búsqueda según lo que el usuario seleccionó en el ComboBox.
     *
     */
    @FXML
    private void cambiarfiltro() {
        String filtroSeleccionado = cmbFiltro.getValue();
        System.out.println("[Filtro cambiado a]: " + filtroSeleccionado);

        if ("Por sitio".equals(filtroSeleccionado)) {
            credencialService.setEstrategia(new BusquedaPorSitio(DAOFactory.createCredencialDAO()));
        } else {
            credencialService.setEstrategia(new BusquedaPorCorreo(DAOFactory.createCredencialDAO()));
        }
        buscar();
    }

    @FXML
    private void nuevaCred() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/AddCredentialView.fxml"));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Nueva credencial");
            dialog.setScene(new Scene(loader.load()));
            AddCredentialController ctrl = loader.getController();
            ctrl.inicializar(usuarioActivo, credencialService);
            dialog.showAndWait();

            llenarT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarPassword(Credencial credencial) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/AuthView.fxml"));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Autenticación requerida");
            dialog.setScene(new Scene(loader.load()));
            AuthController ctrl = loader.getController();
            ctrl.inicializar(credencial, usuarioActivo, credencialService);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void eliminarCredencial(Credencial credencial) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Eliminar credencial de " + credencial.getSitio() + "?",
            ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES) {
                credencialService.eliminar(credencial.getIdCredencial());
                llenarT();
                lblEstado.setText("Credencial eliminada.");
            }
        });
    }
    @FXML
    private void graficas() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/GraficasView.fxml"));
            Stage ventana = new Stage();
            ventana.setTitle("Estadísticas");
            ventana.setScene(new Scene(loader.load()));
            GraficasController ctrl = loader.getController();
            ctrl.inicializar(usuarioActivo, credencialService);
            ventana.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrar() {
        try {
            com.secretvaul.crypto.KeyManager.cerrarSesion();
            App.cambiarVista("LoginView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}