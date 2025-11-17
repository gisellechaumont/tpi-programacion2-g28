package service;

import config.DatabaseConnection;
import dao.UsuarioDao;
import dao.CredencialAccesoDAO;
import models.Usuario;
import models.CredencialAcceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioService implements GenericService<Usuario> {

    private final UsuarioDao usuarioDao;
    private final CredencialAccesoDAO credDao;

    public UsuarioService() {
        this.credDao = new CredencialAccesoDAO();
        this.usuarioDao = new UsuarioDao(credDao);
    }

    // ==================================================
    // MÉTODO ESPECIAL: Usuario + Credencial en transacción
    // ==================================================
    public Usuario insertarUsuarioConCredencial(Usuario usuario, CredencialAcceso cred) throws Exception {

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // ------------ VALIDACIONES ---------------
            if (usuario.getUsername() == null || usuario.getUsername().isBlank())
                throw new IllegalArgumentException("El username es obligatorio.");

            if (usuario.getEmail() == null || usuario.getEmail().isBlank())
                throw new IllegalArgumentException("El email es obligatorio.");

            // username único
            for (Usuario u : usuarioDao.getAll()) {
                if (u.getUsername().equalsIgnoreCase(usuario.getUsername()))
                    throw new IllegalArgumentException("El username ya existe.");
            }

            // email único
            for (Usuario u : usuarioDao.getAll()) {
                if (u.getEmail().equalsIgnoreCase(usuario.getEmail()))
                    throw new IllegalArgumentException("El email ya está registrado.");
            }

            // ------------ CREAR CREDENCIAL ------------
            if (cred != null) {
                cred.setUltimoCambio(LocalDateTime.now());
                cred.setRequiereReset(false);
                credDao.insertTx(cred, conn);
            }

            // ------------ CREAR USUARIO ------------
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setCredencial(cred);

            usuarioDao.insertTx(usuario, conn);

            conn.commit();
            return usuario;

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;

        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // ==================================================
    // MÉTODOS GENÉRICOS (para el CRUD AUTOMÁTICO)
    // ==================================================

    @Override
    public Usuario insertar(Usuario u) throws Exception {

        if (u.getUsername() == null || u.getUsername().isBlank())
            throw new IllegalArgumentException("El username es obligatorio.");

        if (u.getEmail() == null || u.getEmail().isBlank())
            throw new IllegalArgumentException("El email es obligatorio.");

        u.setFechaRegistro(LocalDateTime.now());
        usuarioDao.insertar(u);

        return u;
    }

    @Override
    public Usuario getById(long id) throws Exception {
        return usuarioDao.getById((int) id);
    }

    @Override
    public List<Usuario> getAll() throws Exception {
        return usuarioDao.getAll();
    }

    @Override
    public void actualizar(Usuario u) throws Exception {
        usuarioDao.actualizar(u);
    }

    @Override
    public void eliminar(long id) throws Exception {
        usuarioDao.eliminar((int) id);
    }
}
