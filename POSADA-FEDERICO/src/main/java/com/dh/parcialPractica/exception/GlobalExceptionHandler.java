package com.dh.parcialPractica.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ClinicaErrorResponse> emitirBadRequestException(BadRequestException exception) {
        exception.printStackTrace();
        logger.error("Mensaje de error de BadRequestException --> " + exception.getMessage());
        ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ClinicaErrorResponse> emitirNotFoundException(NotFoundException exception) {
        exception.printStackTrace();
        logger.error("Mensaje de error de NotFoundException --> " + exception.getMessage());
        ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
