package main;

import service.GenericService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CrudController<T> {

    private final GenericService<T> service;
    private final Class<T> clazz;
    private final Scanner scanner = new Scanner(System.in);

    public CrudController(GenericService<T> service, Class<T> clazz) {
        this.service = service;
        this.clazz = clazz;
    }

    public void iniciar() {

        while (true) {
            System.out.println("\n========== CRUD AUTOMÁTICO: " + clazz.getSimpleName() + " ==========");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcion) {
                    case 1 -> crear();
                    case 2 -> listar();
                    case 3 -> buscar();
                    case 4 -> actualizar();
                    case 5 -> eliminar();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // =================== CREATE ===================
    private void crear() throws Exception {
        T entidad = clazz.getDeclaredConstructor().newInstance();

        System.out.println("\n--- CREAR " + clazz.getSimpleName() + " ---");

        for (Field f : clazz.getDeclaredFields()) {

            if (f.getName().equalsIgnoreCase("id") ||
                    f.getName().equalsIgnoreCase("eliminado") ||
                    f.getType().getSimpleName().equals("CredencialAcceso")) continue;

            System.out.print("Ingrese " + f.getName() + ": ");
            f.setAccessible(true);

            if (f.getType().equals(String.class)) {
                f.set(entidad, scanner.nextLine());

            } else if (f.getType().equals(boolean.class)) {
                f.set(entidad, Boolean.parseBoolean(scanner.nextLine()));

            } else if (f.getType().equals(LocalDateTime.class)) {
                f.set(entidad, LocalDateTime.now());
            }
        }

        service.insertar(entidad);
        System.out.println("✔ Registro creado correctamente");
    }

    // =================== READ ===================
    private void listar() throws Exception {
        List<T> lista = service.getAll();
        lista.forEach(System.out::println);
    }

    private void buscar() throws Exception {
        System.out.print("ID: ");
        long id = Long.parseLong(scanner.nextLine());
        System.out.println(service.getById(id));
    }

    // =================== UPDATE ===================
    private void actualizar() throws Exception {
        System.out.print("ID a actualizar: ");
        long id = Long.parseLong(scanner.nextLine());

        T entidad = service.getById(id);
        if (entidad == null) {
            System.out.println("No existe.");
            return;
        }

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals("id") || f.getName().equals("eliminado")) continue;

            f.setAccessible(true);
            System.out.print("Nuevo valor para " + f.getName() +
                    " (ENTER para mantener '" + f.get(entidad) + "'): ");

            String input = scanner.nextLine();
            if (input.isBlank()) continue;

            if (f.getType().equals(String.class))
                f.set(entidad, input);
            else if (f.getType().equals(boolean.class))
                f.set(entidad, Boolean.parseBoolean(input));
        }

        service.actualizar(entidad);
        System.out.println("✔ Registro actualizado");
    }

    // =================== DELETE ===================
    private void eliminar() throws Exception {
        System.out.print("ID a eliminar: ");
        long id = Long.parseLong(scanner.nextLine());
        service.eliminar(id);
        System.out.println("✔ Eliminado correctamente");
    }
}
