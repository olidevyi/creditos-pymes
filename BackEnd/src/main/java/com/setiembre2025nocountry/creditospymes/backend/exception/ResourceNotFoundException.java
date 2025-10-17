package com.setiembre2025nocountry.creditospymes.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    // Este metodo es para controlar el error en una uri que devuelve un objeto por id
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no fue encontrado con: %s='%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // Este metodo es para controlar el error en una uri que devuelve una lista de objetos
    public ResourceNotFoundException(String resourceName) {
        super(String.format("No hay registros de %s en el sistema", resourceName));
        this.resourceName = resourceName;
    }

    // Se debe crear tantos métodos como sea necesario según la lógica del negocio lo requiera
    // ...
}