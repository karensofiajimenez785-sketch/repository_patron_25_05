package com.ejemplo.exception;

/**
 * Excepción para datos de usuario inválidos.
 * Sin cambios respecto al proyecto anterior.
 */
public class InvalidUserDataException extends Exception {

    public InvalidUserDataException(String message) {
        super(message);
    }
}
