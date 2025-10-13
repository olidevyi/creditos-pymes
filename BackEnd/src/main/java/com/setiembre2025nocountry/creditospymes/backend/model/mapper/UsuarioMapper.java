package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.ennum.Rol;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDtoRes toDto(Usuario u){
        return new UsuarioDtoRes(
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getFechaNacimiento(),
                u.getEmail(),
                u.getTelefono(),
                u.getDocumentoIdentidad(),
                u.getFechaRegistro(),
                u.getFechaActualizacion(),
                u.getUltimoAcceso(),
                u.isActivo(),
                u.getRol()
        );
    }
    public Usuario toEntity(UsuarioDtoReq r){
        Usuario usuario = new Usuario();
        usuario.setNombre(r.nombre());
        usuario.setApellido(r.apellido());
        usuario.setFechaNacimiento(r.fechaNacimiento());
        usuario.setEmail(r.email());
        usuario.setTelefono(r.telefono());
        usuario.setDocumentoIdentidad(r.documentoIdentidad());
        usuario.setPassword(r.password());
        usuario.setActivo(r.activo() == null || r.activo());
        usuario.setRol(r.rol() != null ? r.rol() : Rol.USUARIO_EMPRESA);
        return usuario;
    }
}
