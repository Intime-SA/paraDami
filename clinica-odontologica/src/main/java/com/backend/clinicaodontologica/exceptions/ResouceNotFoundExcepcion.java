package com.backend.clinicaodontologica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResouceNotFoundExcepcion extends Exception{

    public ResouceNotFoundExcepcion() {
    }

    public ResouceNotFoundExcepcion(String message) {
        super(message);
    }
}

