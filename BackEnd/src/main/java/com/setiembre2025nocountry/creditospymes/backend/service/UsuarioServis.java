package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UsuarioServis {

    // método para crear un usuario
    UsuarioDtoRes createUser(UsuarioDtoReq userDTOReq);

    // método para obtener un usuario por su ID
    UsuarioDtoRes getUserById(Long id) throws ChangeSetPersister.NotFoundException;

    //  método para actualizar un usuario
    UsuarioDtoRes updateUser(Long id, UsuarioDtoReq userDTOReq) throws ChangeSetPersister.NotFoundException;

    // método para eliminar un usuario por su ID
    void deleteUser(Long id) throws ChangeSetPersister.NotFoundException;

    // método para obtener todos los usuarios
    List<UsuarioDtoRes> getAllUsers();


}