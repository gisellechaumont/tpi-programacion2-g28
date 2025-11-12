/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import models.CredencialAcceso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CredencialAccesoDAO implements GenericDAO<CredencialAcceso> {

    private static final String INSERT_SQL = "INSERT INTO credencial_acceso (hash_password,ultimo_cambio,requiere_reset) VALUES (?, ?,?)";
    
    private static final String UPDATE_SQL = "UPDATE credencial_acceso SET hash_password = ?, ultimo_cambio = ?, requiere_reset = ? WHERE id = ?";

    private static final String SELECT_ALL_SQL = "SELECT * FROM credencial_acceso WHERE eliminado = FALSE";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM credencial_acceso WHERE id = ? AND eliminado = FALSE";

    private static final String DELETE_SQL = "UPDATE credencial_acceso SET eliminado = TRUE WHERE id = ?";

    
    @Override
    public void insertar(CredencialAcceso credencial) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, credencial.getHashPassword());
            stmt.setTimestamp(2, Timestamp.valueOf(credencial.getUltimoCambio()));
            stmt.setBoolean(3, credencial.isRequiereReset());
            
            stmt.executeUpdate();

            setGeneratedId(stmt, credencial);
        }    
    
    }

    @Override
    public void insertTx(CredencialAcceso credencial, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, credencial.getHashPassword());
            stmt.setTimestamp(2, Timestamp.valueOf(credencial.getUltimoCambio()));
            stmt.setBoolean(3, credencial.isRequiereReset());
            
            stmt.executeUpdate();
            
            setGeneratedId(stmt, credencial);
        }    
    }

    @Override
    public void actualizar(CredencialAcceso credencial) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, credencial.getHashPassword());
            stmt.setTimestamp(2, Timestamp.valueOf(credencial.getUltimoCambio()));
            stmt.setBoolean(3, credencial.isRequiereReset());
            stmt.setLong(4, credencial.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el domicilio con ID: " + credencial.getId());
            }
        }   
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se encontró domicilio con ID: " + id);
            }
        }
    }

    @Override
    public CredencialAcceso getById(int id) throws SQLException {
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CredencialAcceso(
                        rs.getInt("id"),
                        rs.getString("hash_password"),
                        rs.getObject("ultimo_cambio", LocalDateTime.class),
                        rs.getBoolean("requiere_reset"));
                }
            }
        }
        return null;    
    }

    @Override
    public List<CredencialAcceso> getAll() throws SQLException {
        List<CredencialAcceso> credenciales = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                credenciales.add(new CredencialAcceso(
                        rs.getInt("id"),
                        rs.getString("hash_password"),
                        rs.getObject("ultimo_cambio", LocalDateTime.class),
                        rs.getBoolean("requiere_reset")
                ));
            }
        }
        return credenciales;

    }
    
    
    private void setGeneratedId(PreparedStatement stmt, CredencialAcceso credencial) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                credencial.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("La inserción de la Credencial De Acceso falló, no se obtuvo ID generado");
            }
        }
    }

}
