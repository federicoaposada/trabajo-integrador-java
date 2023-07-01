package com.dh.parcialPractica.exception;

import lombok.Getter;

@Getter
public class PacienteNotFoundException extends NotFoundException {
    public PacienteNotFoundException(String codigoError, String mensajeError) {
        super(codigoError, mensajeError);
    }
}