// src/main/java/.../model/mapper/EmpresaMapper.java
package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {
    public EmpresaDtoRes toDto(Empresa e){
        return EmpresaDtoRes.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .razonSocial(e.getRazonSocial())
                .email(e.getEmail())
                .cuit(e.getCuit())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .build();
    }
    public Empresa toEntity(EmpresaDtoReq r){
        return Empresa.builder()
                .nombre(r.nombre())
                .razonSocial(r.razonSocial())
                .email(r.email())
                .cuit(r.cuit())
                .direccion(r.direccion())
                .telefono(r.telefono())
                .build();
    }
}
