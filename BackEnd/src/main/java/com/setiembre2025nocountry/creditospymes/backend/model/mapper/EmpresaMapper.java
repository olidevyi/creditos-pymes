package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Empresa;

public class EmpresaMapper {
    public  EmpresaDtoRes toDto(Empresa empresa){
        return EmpresaDtoRes.builder()
                .cuit(empresa.getCuit())
                .direccion(empresa.getDireccion())
                .razonSocial(empresa.getRazonSocial())
                .telefono(empresa.getTelefono())
                .build();
    }

    public Empresa toEmpresa(EmpresaDtoReq empresaDtoReq){
        return Empresa.builder()
                .cuit(empresaDtoReq.cuit())
                .direccion(empresaDtoReq.direccion())
                .telefono(empresaDtoReq.telefono())
                .razonSocial(empresaDtoReq.razonSocial())
                .build();
    }
}