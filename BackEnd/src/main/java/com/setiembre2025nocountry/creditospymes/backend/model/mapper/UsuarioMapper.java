package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;

public class UsuarioMapper {
    public UsuarioDtoRes toDto(Usuario usuario){
        return UsuarioDtoRes.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .fechaRegistro(usuario.getFechaRegistro())
                .passWord(usuario.getPassWord())
                .build();
    }

    public Usuario toUsuario(UsuarioDtoReq request){
        return Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .fechaRegistro(request.fechaRegistro())
                .build();
    }

}
