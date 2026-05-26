package com.ejemplo.validator;

/**
 * UserValidator — sin cambios respecto al proyecto anterior.
 * Valida nombre y email antes de guardar en la BD.
 */
public class UserValidator {

    /**
     * El nombre debe tener entre 2 y 100 caracteres.
     */
    public static boolean validateName(String name) {
        if (name == null) return false;
        String trimmed = name.trim();
        return trimmed.length() >= 2 && trimmed.length() <= 100;
    }

    /**
     * El email debe tener formato básico: texto@texto.texto
     */
    public static boolean validateEmail(String email) {
        if (email == null) return false;
        return email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$");
    }
}
