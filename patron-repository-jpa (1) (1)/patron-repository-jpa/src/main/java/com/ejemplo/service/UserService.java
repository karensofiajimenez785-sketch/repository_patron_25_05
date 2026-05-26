package com.ejemplo.service;

import com.ejemplo.entity.UserEntity;
import com.ejemplo.exception.InvalidUserDataException;
import com.ejemplo.repository.UserRepository;
import com.ejemplo.validator.UserValidator;

import java.util.List;

/**
 * UserService — lógica de negocio.
 * Cambio mínimo: UserDTO → UserEntity.
 * La validación y estructura permanecen iguales.
 */
public class UserService {

    private final UserRepository userRepository = new UserRepository();

    // ----------------------------------------------------------
    // Consultas
    // ----------------------------------------------------------

    public UserEntity getUserById(int id) {
        return userRepository.findById(id);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // ----------------------------------------------------------
    // Operaciones de escritura (con validación)
    // ----------------------------------------------------------

    public void createUser(String name, String email) throws InvalidUserDataException {
        if (!UserValidator.validateName(name)) {
            throw new InvalidUserDataException("Nombre inválido: debe tener entre 2 y 100 caracteres.");
        }
        if (!UserValidator.validateEmail(email)) {
            throw new InvalidUserDataException("Email inválido: formato incorrecto.");
        }

        UserEntity user = new UserEntity(name, email);
        userRepository.save(user);
    }

    public void updateUser(int id, String name, String email) throws InvalidUserDataException {
        UserEntity user = userRepository.findById(id);
        if (user == null) {
            throw new InvalidUserDataException("No existe usuario con ID: " + id);
        }
        if (!UserValidator.validateName(name)) {
            throw new InvalidUserDataException("Nombre inválido: debe tener entre 2 y 100 caracteres.");
        }
        if (!UserValidator.validateEmail(email)) {
            throw new InvalidUserDataException("Email inválido: formato incorrecto.");
        }

        user.setName(name);
        user.setEmail(email);
        userRepository.update(user);
    }

    public void deleteUser(int id) throws InvalidUserDataException {
        UserEntity user = userRepository.findById(id);
        if (user == null) {
            throw new InvalidUserDataException("No existe usuario con ID: " + id);
        }
        userRepository.delete(id);
    }
}
