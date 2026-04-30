package com.secretvaul.controller;

import com.secretvaul.model.Credencial;
import com.secretvaul.model.Usuario;
import com.secretvaul.service.ICredencialService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import java.util.*;

/**
 * Controlador de las graficas
 */
public class GraficasController {
    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;

    public void inicializar(Usuario usuario, ICredencialService service) {
        List<Credencial> todas = service.listarTodas(usuario.getIdUsuario());

        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Credencial c : todas) {
            String sitio = c.getSitio() != null ? c.getSitio() : "Sin sitio";
            conteo.merge(sitio, 1, Integer::sum);
        }

        cargarPieChart(conteo);
        cargarBarChart(conteo);
    }

    private void cargarPieChart(Map<String, Integer> conteo) {
        var datos = conteo.entrySet().stream()
            .map(e -> new PieChart.Data(e.getKey(), e.getValue()))
            .toList();
        pieChart.setData(FXCollections.observableArrayList(datos));
    }
    private void cargarBarChart(Map<String, Integer> conteo) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Credenciales");
        conteo.forEach((sitio, cantidad) ->
            serie.getData().add(new XYChart.Data<>(sitio, cantidad))
        );
        barChart.getData().add(serie);
    }
    @FXML
    private void handleCerrar() {
        ((Stage) pieChart.getScene().getWindow()).close();
    }
}