package com.dh.parcialPractica.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final String codigoError;
    private final String mensaje;

    public BadRequestException(String codigoError, String mensaje) {
        super(mensaje);
        this.codigoError = codigoError;
        this.mensaje = mensaje;
    }

}
