package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.PacienteDto;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.ClinicaErrorResponse;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

  private final PacienteService pacienteService;

  @Autowired
  public PacienteController(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @GetMapping
  public ResponseEntity<?> obtenerTodosLosPacientes() {
    try {
      List<PacienteDto> pacientes = pacienteService.obtenerTodosLosPacientes();
      return new ResponseEntity<>(pacientes, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  @PostMapping
  public ResponseEntity<?> guardarPaciente(@RequestBody PacienteDto pacienteDto) {
    try {
      PacienteDto paciente = pacienteService.guardaPaciente(pacienteDto);
      return new ResponseEntity<>(paciente, HttpStatus.CREATED);
    } catch (BadRequestException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMensaje());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<PacienteDto> modificarPaciente(@PathVariable Integer id, @RequestBody PacienteDto pacienteDto) {
    try {
      PacienteDto pacienteModificado = pacienteService.modificarPaciente(id, pacienteDto);
      return new ResponseEntity<>(pacienteModificado, HttpStatus.OK);
    } catch (NotFoundException exception) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    } catch (BadRequestException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id) {
    try {
      pacienteService.eliminarPaciente(id);
      return ResponseEntity.ok("Paciente eliminado correctamente");
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<PacienteDto> buscarPorId(@PathVariable Integer id) {
    try {
      PacienteDto paciente = pacienteService.buscarPorId(id);
      return ResponseEntity.ok(paciente);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

}
