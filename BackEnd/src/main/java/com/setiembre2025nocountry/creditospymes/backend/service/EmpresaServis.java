package com.setiembre2025nocountry.creditospymes.backend.service;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;

import java.util.List;

public interface EmpresaServis {
    //metodo para crear empresa
    EmpresaDtoRes createEmpresas(EmpresaDtoReq userDTOReq);

    //método para obtener una empresa por su ID
    EmpresaDtoRes getEmpresasById(Long id);

    //método para actualizar una empresa
    EmpresaDtoRes updateEmpresa(Long id, EmpresaDtoReq userDTOReq);

    //método para eliminar una empresa por su ID
    void deleteEmpresas(Long id);

    //método para obtener todas las empresas
    List<EmpresaDtoRes> getAllEmpresas();


}
