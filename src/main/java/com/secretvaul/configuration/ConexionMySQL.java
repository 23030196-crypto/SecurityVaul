package com.secretvaul.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Maneja la conexión a la base de datos MySQL.
 * Solo abro una conexión durante toda la ejecución de la app (Singleton),
 * porque abrir y cerrar conexiones en cada consulta es lento y puede
 * generar problemas con MySQL si se acumulan demasiadas conexiones abiertas.
 */
public class ConexionMySQL {

    private static final String URL  = "jdbc:mysql://localhost:3306/securevault";
    private static final String USER = "root";
    private static final String PASS = "";

    private static ConexionMySQL instancia;
    private Connection connection;

    private ConexionMySQL() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conectado con MySQL");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con MySQL: " + e.getMessage());
        }
    }

    public static ConexionMySQL getInstance() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

    public Connection getConnection() {
        return connection;
    }
}