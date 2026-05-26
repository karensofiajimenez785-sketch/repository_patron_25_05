package com.ejemplo.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JPAUtil — reemplaza a DatabaseConfig del proyecto anterior.
 *
 * Crea la EntityManagerFactory una sola vez (es costosa de instanciar)
 * y proporciona EntityManager bajo demanda.
 */
public class JPAUtil {

    // Singleton: se crea una vez al cargar la clase
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miUnidad");

    // Evitar instanciación directa
    private JPAUtil() {}

    /**
     * Devuelve un nuevo EntityManager listo para operar con la BD.
     * Debes cerrarlo (em.close()) después de cada operación.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Cierra la fábrica. Llama a este método al terminar la aplicación.
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
