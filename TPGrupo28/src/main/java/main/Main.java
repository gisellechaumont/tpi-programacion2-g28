/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.CredencialAccesoDAO;
import dao.UsuarioDao;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CredencialAcceso;
import models.Usuario;

/**
 *
 * @author ezequ
 */
public class Main {

    public static void main(String[] args) {


        /*
        La creacion de los DAO esta de forma temporal para testeo. Deben ser
        llevadas a los servicios
        La creacion de algunos objetos posiblemente vayan en el menu o similar
        tal como en el ejemplo pasado por la catedra
        */
        CredencialAccesoDAO dao = null;
        try {
            dao = new CredencialAccesoDAO();

            System.out.println("\n--- DAO de CredencialAccesoDAO ---");

            System.out.println("\n--- Insertamos un registro en la DB ---");
            CredencialAcceso objetoCreado = new CredencialAcceso(0,"hash456",LocalDateTime.now(),false);
            dao.insertar(objetoCreado);
            System.out.println(objetoCreado);

            System.out.println("\n--- Prueba del traer por ID ---");
            CredencialAcceso credencialID1 = dao.getById(1);
            System.out.println(credencialID1);

            System.out.println("\n--- Prueba del listar ---");
            System.out.println(dao.getAll());

            //System.out.println("\n--- Actualizar un registro en la DB ---");
            //credencialID1.setHashPassword("hash982");
            //dao.actualizar(credencialID1);
            //System.out.println(credencialID1);

            //System.out.println("\n--- Eliminamos (soft delete) la credencial de ID 1 ---");
            //dao.eliminar(1);

            System.out.println("\n--- Prueba del listamos despues de eliminar ---");
            System.out.println(dao.getAll());


        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            UsuarioDao usuarioDao = new UsuarioDao(dao);

            System.out.println("\n--- DAO de Usuario ---");

            System.out.println("\n--- Insertamos un registro de Usuario en la DB ---");
            Usuario usuarioCreado = new Usuario(0,"maria16","maria16@gmail.com",false,LocalDateTime.now(),dao.getById(4));
            usuarioDao.insertar(usuarioCreado);
            System.out.println(usuarioCreado);



            System.out.println("\n--- Prueba del listar ---");
            System.out.println(usuarioDao.getAll());


            System.out.println("\n--- Prueba del traer por ID ---");
            Usuario usuarioID1 = usuarioDao.getById(1);
            System.out.println(usuarioID1);


            System.out.println("\n--- Actualizar un registro en la DB ---");
            usuarioID1.setUsername("pepe");
            usuarioDao.actualizar(usuarioID1);
            System.out.println(usuarioID1);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
