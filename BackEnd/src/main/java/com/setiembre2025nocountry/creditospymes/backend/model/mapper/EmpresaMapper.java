package com.setiembre2025nocountry.creditospymes.backend.model.mapper;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {
    public EmpresaDtoRes toDto(Empresa e){
        Long administradorId = e.getAdministradorResponsable() != null ? e.getAdministradorResponsable().getId() : null;
        String administradorNombre = e.getAdministradorResponsable() != null
                ? e.getAdministradorResponsable().getNombre() + " " + e.getAdministradorResponsable().getApellido()
                : null;

        return new EmpresaDtoRes(
                e.getId(),
                e.getNombre(),
                e.getRazonSocial(),
                e.getCuit(),
                e.getEmail(),
                e.getDireccion(),
                e.getTelefono(),
                e.getSector(),
                e.getAnioFundacion(),
                e.getNumeroEmpleados(),
                e.getFacturacionAnualPromedio(),
                e.getSitioWeb(),
                e.getDescripcion(),
                e.getFechaCreacion(),
                e.getFechaActualizacion(),
                administradorId,
                administradorNombre
        );
    }
    public Empresa toEntity(EmpresaDtoReq r){
        Empresa empresa = new Empresa();
        empresa.setNombre(r.nombre());
        empresa.setRazonSocial(r.razonSocial());
        empresa.setCuit(r.cuit());
        empresa.setEmail(r.email());
        empresa.setDireccion(r.direccion());
        empresa.setTelefono(r.telefono());
        empresa.setSector(r.sector());
        empresa.setAnioFundacion(r.anioFundacion());
        empresa.setNumeroEmpleados(r.numeroEmpleados());
        empresa.setFacturacionAnualPromedio(r.facturacionAnualPromedio());
        empresa.setSitioWeb(r.sitioWeb());
        empresa.setDescripcion(r.descripcion());
        return empresa;
    }
}
