package main;

import service.UsuarioService;
import service.CredencialAccesoService;
import models.Usuario;
import models.CredencialAcceso;

import java.time.LocalDateTime;
import java.util.Scanner;

public class PuntoDeEntrada {

    public static void main(String[] args) {

        UsuarioService usuarioService = new UsuarioService();
        CredencialAccesoService credService = new CredencialAccesoService();

        CrudController<Usuario> usuarioController =
                new CrudController<>(usuarioService, Usuario.class);

        CrudController<CredencialAcceso> credController =
                new CrudController<>(credService, CredencialAcceso.class);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=========== MENÚ PRINCIPAL ===========");
            System.out.println("1. CRUD Usuario");
            System.out.println("2. CRUD CredencialAcceso");
            System.out.println("3. Crear Usuario + Credencial (Transacción)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            String opt = scanner.nextLine();

            switch (opt) {
                case "1" -> usuarioController.iniciar();
                case "2" -> credController.iniciar();

                case "3" -> {
                    try {
                        System.out.println("=== Crear Usuario + Credencial ===");
                        Usuario u = new Usuario();
                        CredencialAcceso c = new CredencialAcceso();

                        // Usuario
                        System.out.print("Username: ");
                        u.setUsername(scanner.nextLine());

                        System.out.print("Email: ");
                        u.setEmail(scanner.nextLine());

                        u.setActivo(true);
                        u.setFechaRegistro(LocalDateTime.now());

                        // Credencial
                        System.out.print("Hash Password: ");
                        c.setHashPassword(scanner.nextLine());

                        c.setRequiereReset(false);
                        c.setUltimoCambio(LocalDateTime.now());

                        Usuario nuevo = usuarioService.insertarUsuarioConCredencial(u, c);

                        System.out.println("✓ Usuario y credencial creados:");
                        System.out.println(nuevo);

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case "0" -> {
                    System.out.println("Saliendo...");
                    return;
                }

                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
