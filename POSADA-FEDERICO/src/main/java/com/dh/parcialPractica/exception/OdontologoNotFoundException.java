package com.dh.parcialPractica.exception;

import lombok.Getter;

@Getter
public class OdontologoNotFoundException extends NotFoundException {
    public OdontologoNotFoundException(String codigoError, String mensajeError) {
        super(codigoError, mensajeError);
    }
}