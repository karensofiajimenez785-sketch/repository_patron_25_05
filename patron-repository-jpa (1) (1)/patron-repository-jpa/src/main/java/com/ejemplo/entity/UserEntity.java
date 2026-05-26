package com.ejemplo.entity;

import jakarta.persistence.*;

/**
 * UserEntity — reemplaza a UserDTO.
 *
 * Esta clase ya no es solo un contenedor de datos:
 * JPA la vincula directamente con la tabla "users" en MySQL.
 *
 * Reglas JPA:
 *  - @Entity   → esta clase es una tabla
 *  - @Id       → campo clave primaria
 *  - Constructor vacío obligatorio
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT en MySQL
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // -------------------------------------------------------
    // Constructor vacío: OBLIGATORIO para JPA (no lo elimines)
    // -------------------------------------------------------
    public UserEntity() {}

    // Constructor de conveniencia
    public UserEntity(String name, String email) {
        this.name  = name;
        this.email = email;
    }

    // -------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------
    public int getId()    { return id; }

    public String getName()                { return name; }
    public void   setName(String name)     { this.name = name; }

    public String getEmail()               { return email; }
    public void   setEmail(String email)   { this.email = email; }

    @Override
    public String toString() {
        return "UserEntity{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
