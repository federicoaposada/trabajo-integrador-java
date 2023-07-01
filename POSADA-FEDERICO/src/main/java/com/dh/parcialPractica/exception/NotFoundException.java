package com.dh.parcialPractica.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception{

    private String codigoError;

    public NotFoundException(String codigoError, String mensaje) {
        super(mensaje);
        this.codigoError = codigoError;
    }
}

