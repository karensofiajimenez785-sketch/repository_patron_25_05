package com.ejemplo;

import com.ejemplo.config.JPAUtil;
import com.ejemplo.entity.UserEntity;
import com.ejemplo.exception.InvalidUserDataException;
import com.ejemplo.service.UserService;

import java.util.List;

/**
 * Main — punto de entrada.
 * Demuestra el ciclo completo CRUD usando JPA.
 *
 * IMPORTANTE: Asegúrate de que:
 *  1. MySQL esté corriendo en localhost:3306
 *  2. La base de datos "testdb" exista (CREATE DATABASE testdb;)
 *  3. Las credenciales en persistence.xml sean correctas
 */
public class Main {

    public static void main(String[] args) {
        UserService service = new UserService();

        try {
            System.out.println("═══════════════════════════════════");
            System.out.println("  Patrón Repository con JPA");
            System.out.println("═══════════════════════════════════\n");

            // ── 1. CREAR ──────────────────────────────────────────
            System.out.println("[ CREATE ] Creando usuarios...");
            service.createUser("Ana Gómez",    "ana@ejemplo.com");
            service.createUser("Carlos Ruiz",  "carlos@ejemplo.com");
            service.createUser("Laura Torres", "laura@ejemplo.com");
            System.out.println("  ✓ 3 usuarios creados.\n");

            // ── 2. LEER TODOS ────────────────────────────────────
            System.out.println("[ READ ALL ] Listando todos los usuarios:");
            List<UserEntity> todos = service.getAllUsers();
            todos.forEach(u ->
                System.out.printf("  %-3d │ %-20s │ %s%n",
                        u.getId(), u.getName(), u.getEmail())
            );

            // ── 3. LEER POR ID ───────────────────────────────────
            System.out.println("\n[ READ BY ID ] Buscando usuario con ID 1:");
            UserEntity u1 = service.getUserById(1);
            if (u1 != null) {
                System.out.println("  Encontrado: " + u1);
            }

            // ── 4. LEER POR EMAIL ─────────────────────────────────
            System.out.println("\n[ READ BY EMAIL ] Buscando carlos@ejemplo.com:");
            UserEntity porEmail = service.getUserByEmail("carlos@ejemplo.com");
            if (porEmail != null) {
                System.out.println("  Encontrado: " + porEmail);
            }

            // ── 5. ACTUALIZAR ────────────────────────────────────
            System.out.println("\n[ UPDATE ] Actualizando ID 1...");
            service.updateUser(1, "Ana M. Gómez", "ana.m@ejemplo.com");
            System.out.println("  ✓ Usuario actualizado: " + service.getUserById(1));

            // ── 6. ELIMINAR ──────────────────────────────────────
            System.out.println("\n[ DELETE ] Eliminando ID 3...");
            service.deleteUser(3);
            System.out.println("  ✓ Eliminado.");

            // ── 7. LISTA FINAL ───────────────────────────────────
            System.out.println("\n[ FINAL ] Estado actual de la tabla:");
            service.getAllUsers().forEach(u ->
                System.out.printf("  %-3d │ %-20s │ %s%n",
                        u.getId(), u.getName(), u.getEmail())
            );

            System.out.println("\n═══════════════════════════════════");
            System.out.println("  Operaciones completadas con éxito");
            System.out.println("═══════════════════════════════════");

        } catch (InvalidUserDataException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // SIEMPRE cerrar la fábrica al terminar
            JPAUtil.close();
            System.out.println("\nConexión cerrada.");
        }
    }
}
