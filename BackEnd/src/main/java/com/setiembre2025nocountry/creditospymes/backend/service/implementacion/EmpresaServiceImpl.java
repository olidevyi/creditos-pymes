package com.setiembre2025nocountry.creditospymes.backend.service.implementacion;

import com.setiembre2025nocountry.creditospymes.backend.model.dto.EmpresaDtoRes;
import com.setiembre2025nocountry.creditospymes.backend.model.dto.dtoReq.EmpresaDtoReq;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Empresa;
import com.setiembre2025nocountry.creditospymes.backend.model.entity.Usuario;
import com.setiembre2025nocountry.creditospymes.backend.model.mapper.EmpresaMapper;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.EmpresaRepository;
import com.setiembre2025nocountry.creditospymes.backend.model.repository.UsuarioRepository;
import com.setiembre2025nocountry.creditospymes.backend.service.EmpresaServis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaServis {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public EmpresaDtoRes createEmpresas(EmpresaDtoReq empresaDtoReq) {
        Empresa empresa = empresaMapper.toEntity(empresaDtoReq);
        if (empresaDtoReq.administradorId() != null) {
            empresa.setAdministradorResponsable(obtenerAdministrador(empresaDtoReq.administradorId()));
        }
        Empresa saved = empresaRepository.save(empresa);
        return empresaMapper.toDto(saved);
    }

    @Override
    public EmpresaDtoRes getEmpresasById(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));
        return empresaMapper.toDto(empresa);
    }

    @Override
    public EmpresaDtoRes updateEmpresa(Long id, EmpresaDtoReq empresaDtoReq) {
        Empresa empresaExistente = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        empresaExistente.setNombre(empresaDtoReq.nombre());
        empresaExistente.setRazonSocial(empresaDtoReq.razonSocial());
        empresaExistente.setCuit(empresaDtoReq.cuit());
        empresaExistente.setDireccion(empresaDtoReq.direccion());
        empresaExistente.setTelefono(empresaDtoReq.telefono());
        empresaExistente.setEmail(empresaDtoReq.email());
        empresaExistente.setSector(empresaDtoReq.sector());
        empresaExistente.setAnioFundacion(empresaDtoReq.anioFundacion());
        empresaExistente.setNumeroEmpleados(empresaDtoReq.numeroEmpleados());
        empresaExistente.setFacturacionAnualPromedio(empresaDtoReq.facturacionAnualPromedio());
        empresaExistente.setSitioWeb(empresaDtoReq.sitioWeb());
        empresaExistente.setDescripcion(empresaDtoReq.descripcion());

        if (empresaDtoReq.administradorId() != null) {
            empresaExistente.setAdministradorResponsable(obtenerAdministrador(empresaDtoReq.administradorId()));
        } else {
            empresaExistente.setAdministradorResponsable(null);
        }

        Empresa actualizada = empresaRepository.save(empresaExistente);
        return empresaMapper.toDto(actualizada);
    }

    @Override
    public void deleteEmpresas(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));
        empresaRepository.delete(empresa);
    }

    @Override
    public List<EmpresaDtoRes> getAllEmpresas() {
        return empresaRepository.findAll()
                .stream()
                .map(empresaMapper::toDto)
                .collect(Collectors.toList());
    }

    private Usuario obtenerAdministrador(Long administradorId) {
        return usuarioRepository.findById(administradorId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el administrador con id " + administradorId));
    }
}
