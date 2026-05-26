package com.ejemplo.repository;

import com.ejemplo.config.JPAUtil;
import com.ejemplo.entity.UserEntity;
import jakarta.persistence.*;

import java.util.List;

/**
 * UserRepository — misma interfaz que el proyecto anterior,
 * ahora usa EntityManager en lugar de SQL manual.
 *
 * Patrón de uso:
 *   1. Obtener un EntityManager (em)
 *   2. Abrir transacción (tx.begin) para operaciones de escritura
 *   3. Operar (persist / merge / remove / find / createQuery)
 *   4. Confirmar (tx.commit) o revertir (tx.rollback) si hay error
 *   5. Cerrar el EntityManager (em.close)
 */
public class UserRepository {

    // ----------------------------------------------------------
    // READ
    // ----------------------------------------------------------

    /**
     * Buscar por ID.
     * JPA genera: SELECT * FROM users WHERE id = ?
     */
    public UserEntity findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(UserEntity.class, id); // null si no existe
        } finally {
            em.close();
        }
    }

    /**
     * Buscar por email usando JPQL con parámetro nombrado.
     * JPQL trabaja con clases Java, no con tablas SQL.
     */
    public UserEntity findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.email = :email",
                    UserEntity.class
            )
            .setParameter("email", email) // evita inyección SQL
            .getSingleResult();
        } catch (NoResultException e) {
            return null; // email no encontrado
        } finally {
            em.close();
        }
    }

    /**
     * Obtener todos los usuarios.
     * JPQL: SELECT u FROM UserEntity u
     * SQL equivalente: SELECT * FROM users
     */
    public List<UserEntity> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM UserEntity u ORDER BY u.id",
                    UserEntity.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------

    /**
     * Guardar un nuevo usuario.
     * JPA genera: INSERT INTO users (name, email) VALUES (?, ?)
     */
    public void save(UserEntity user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user); // el ID se asigna automáticamente
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------

    /**
     * Actualizar un usuario existente.
     * JPA genera: UPDATE users SET name=?, email=? WHERE id=?
     */
    public void update(UserEntity user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user); // merge = actualizar si el ID ya existe
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ----------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------

    /**
     * Eliminar un usuario por ID.
     * JPA genera: DELETE FROM users WHERE id=?
     */
    public void delete(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            UserEntity user = em.find(UserEntity.class, id);
            if (user != null) {
                em.remove(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
