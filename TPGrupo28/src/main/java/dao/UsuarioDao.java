/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import models.Usuario;
import models.CredencialAcceso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements GenericDAO<Usuario> {

    private final CredencialAccesoDAO credencialDAO;

    private static final String INSERT_SQL = "INSERT INTO usuario (username,email,activo,fecha_registro,credencial_acceso_id) VALUES (?,?,?,?,?)";

    private static final String UPDATE_SQL = "UPDATE usuario SET username = ?, email = ?, activo = ?, credencial_acceso_id = ? WHERE id = ?";

    private static final String SELECT_ALL_SQL = "SELECT * FROM usuario WHERE eliminado = FALSE";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM usuario WHERE id = ? AND eliminado = FALSE";

    private static final String DELETE_SQL = "UPDATE usuario SET eliminado = TRUE WHERE id = ?";

    public UsuarioDao(CredencialAccesoDAO credencialDAO) {
        if (credencialDAO == null) {
            throw new IllegalArgumentException("DomicilioDAO no puede ser null");
        }
        this.credencialDAO = credencialDAO;
    }

    @Override
    public void insertar(Usuario usuario) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getEmail());
            stmt.setBoolean(3, usuario.isActivo());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.getFechaRegistro()));

            if (usuario.getCredencial() != null && usuario.getCredencial().getId() > 0) {
                stmt.setLong(5, usuario.getCredencial().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.executeUpdate();
            setGeneratedId(stmt, usuario);
        }
    }

    @Override
    public void insertTx(Usuario usuario, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getEmail());
            stmt.setBoolean(3, usuario.isActivo());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.getFechaRegistro()));

            if (usuario.getCredencial() != null && usuario.getCredencial().getId() > 0) {
                stmt.setLong(5, usuario.getCredencial().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            
            stmt.executeUpdate();
            
            setGeneratedId(stmt, usuario);
        }   
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getEmail());
            stmt.setBoolean(3, usuario.isActivo());
            
            if (usuario.getCredencial() != null && usuario.getCredencial().getId() > 0) {
                stmt.setLong(4, usuario.getCredencial().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setLong(5, usuario.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el domicilio con ID: " + usuario.getId());
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
                throw new SQLException("No se encontró usuario con ID: " + id);
            }
        }
    }

    @Override
    public Usuario getById(int id) throws SQLException {

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CredencialAcceso credencial = credencialDAO.getById(rs.getInt("credencial_acceso_id"));
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getBoolean("activo"),
                            rs.getObject("fecha_registro", LocalDateTime.class),
                            credencial
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Usuario> getAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {

                CredencialAcceso credencial = credencialDAO.getById(rs.getInt("credencial_acceso_id"));
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getBoolean("activo"),
                        rs.getObject("fecha_registro", LocalDateTime.class),
                        credencial
                ));
            }
        }
        return usuarios;

    }

    private void setGeneratedId(PreparedStatement stmt, Usuario usuario) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                usuario.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("La inserción del Usuario falló, no se obtuvo ID generado");
            }
        }
    }

}
