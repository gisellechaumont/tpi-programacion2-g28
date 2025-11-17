package main;

import config.DatabaseConnection;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("✔ Conexión exitosa a la base de datos");
        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }
    }
}
