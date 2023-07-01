package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.TurnoDto;
import com.dh.parcialPractica.entity.Turno;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.ClinicaErrorResponse;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.exception.PacienteNotFoundException;
import com.dh.parcialPractica.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

  private final TurnoService turnoService;

  @Autowired
  public TurnoController(TurnoService turnoService) {
    this.turnoService = turnoService;
  }

  // Obtener todos los turnos
  @GetMapping
  public ResponseEntity<?> obtenerTodos() {
    try {
      List<TurnoDto> turnos = turnoService.obtenerTodos();
      return new ResponseEntity<>(turnos, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  // Crear un nuevo turno
  @PostMapping
  public ResponseEntity<?> guardarTurno(@RequestBody TurnoDto turnoDto) {
    try {
      TurnoDto turnoGuardado = turnoService.guardarTurno(turnoDto);
      return new ResponseEntity<>(turnoGuardado, HttpStatus.CREATED);
    } catch (BadRequestException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (PacienteNotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  // Actualizar un turno existente
  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarTurno(@PathVariable Integer id, @RequestBody TurnoDto turnoDto) {
    try {
      Turno turnoActualizado = turnoService.actualizarTurno(id, turnoDto);
      return new ResponseEntity<>(turnoActualizado, HttpStatus.OK);
    } catch (BadRequestException badRequestException) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(badRequestException.getCodigoError(), badRequestException.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (NotFoundException notFoundException) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(notFoundException.getCodigoError(), notFoundException.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  // Eliminar un turno existente
  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarTurno(@PathVariable Integer id) {
    try {
      boolean eliminado = turnoService.eliminarTurno(id);
      if (eliminado) {
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  // Buscar un turno por su ID
  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
    try {
      TurnoDto turno = turnoService.buscarPorId(id);
      return new ResponseEntity<>(turno, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

}
