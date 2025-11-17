package service;

import dao.CredencialAccesoDAO;
import models.CredencialAcceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class CredencialAccesoService implements GenericService<CredencialAcceso> {

    private final CredencialAccesoDAO credDao = new CredencialAccesoDAO();

    // ===================== INSERTAR =====================
    @Override
    public CredencialAcceso insertar(CredencialAcceso c) throws Exception {

        if (c.getHashPassword() == null || c.getHashPassword().isBlank())
            throw new IllegalArgumentException("El hash de contraseña es obligatorio.");

        if (c.getUltimoCambio() == null)
            c.setUltimoCambio(LocalDateTime.now());

        credDao.insertar(c);
        return c;
    }

    // ===================== OBTENER POR ID =====================
    @Override
    public CredencialAcceso getById(long id) throws Exception {
        return credDao.getById((int) id);
    }

    // ===================== LISTAR =====================
    @Override
    public List<CredencialAcceso> getAll() throws Exception {
        return credDao.getAll();
    }

    // ===================== ACTUALIZAR =====================
    @Override
    public void actualizar(CredencialAcceso c) throws Exception {

        if (c.getId() <= 0)
            throw new IllegalArgumentException("ID inválido.");

        c.setUltimoCambio(LocalDateTime.now());
        credDao.actualizar(c);
    }

    // ===================== ELIMINAR =====================
    @Override
    public void eliminar(long id) throws Exception {
        credDao.eliminar((int) id);
    }

    // ===================== USADO POR UsuarioService =====================
    public void insertarEnTransaccion(CredencialAcceso cred, Connection conn) throws SQLException {

        if (cred.getUltimoCambio() == null)
            cred.setUltimoCambio(LocalDateTime.now());

        credDao.insertTx(cred, conn);
    }
}
