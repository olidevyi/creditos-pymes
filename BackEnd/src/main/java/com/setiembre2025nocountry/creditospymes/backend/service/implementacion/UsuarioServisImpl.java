package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.UsuarioMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.UsuarioServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServisImpl implements UsuarioServis {

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDtoRes createUser(UsuarioDtoReq userDTOReq) {
        Usuario usuario = usuarioMapper.toEntity(userDTOReq);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDtoRes getUserById(Long id) throws ChangeSetPersister.NotFoundException {

        return usuarioMapper.toDto(usuarioRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException()));
    }

    @Override
    public UsuarioDtoRes updateUser(Long id, UsuarioDtoReq userDTOReq) throws ChangeSetPersister.NotFoundException {
        Usuario existingUser = usuarioRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        existingUser.setNombre(userDTOReq.nombre());
        existingUser.setApellido(userDTOReq.apellido());
        existingUser.setFechaNacimiento(userDTOReq.fechaNacimiento());
        existingUser.setEmail(userDTOReq.email());
        existingUser.setTelefono(userDTOReq.telefono());
        existingUser.setDocumentoIdentidad(userDTOReq.documentoIdentidad());
        existingUser.setPassword(userDTOReq.password());
        if (userDTOReq.activo() != null) {
            existingUser.setActivo(userDTOReq.activo());
        }
        existingUser.setRol(userDTOReq.rol());


        existingUser = usuarioRepository.save(existingUser);
        return usuarioMapper.toDto(existingUser);
    }

    @Override
    public void deleteUser(Long id) throws ChangeSetPersister.NotFoundException {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        usuarioRepository.delete(user);

    }

    @Override
    public List<UsuarioDtoRes> getAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();
        return users.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }


}
