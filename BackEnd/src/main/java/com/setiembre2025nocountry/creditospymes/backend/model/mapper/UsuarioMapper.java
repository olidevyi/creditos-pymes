package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.UsuarioDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.UsuarioDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDtoRes toDto(Usuario u){
        return UsuarioDtoRes.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .email(u.getEmail())
                .fechaRegistro(u.getFechaRegistro())
                .fechaActualizacion(u.getFechaActualizacion())
                .rol(u.getRol())
                .build();
    }
    public Usuario toEntity(UsuarioDtoReq r){
        return Usuario.builder()
                .nombre(r.nombre())
                .email(r.email())
                .passWord(r.passWord())
                .rol(r.rol())
                .build();
    }
}
