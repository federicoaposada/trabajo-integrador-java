package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.ClinicaErrorResponse;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.services.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

  private final OdontologoService odontologoService;

  @Autowired
  public OdontologoController(OdontologoService odontologoService) {
    this.odontologoService = odontologoService;
  }

  @GetMapping
  public ResponseEntity<?> obtenerTodosLosOdontologos() {
    try {
      List<OdontologoDto> odontologos = odontologoService.obtenerTodosLosOdontologos();
      return new ResponseEntity<>(odontologos, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  @PostMapping
  public ResponseEntity<?> guardarOdontologo(@RequestBody OdontologoDto odontologoDto) {
    try {
      OdontologoDto odontologo = odontologoService.guardarOdontologo(odontologoDto);
      return new ResponseEntity<>(odontologo, HttpStatus.CREATED);
    } catch (BadRequestException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> modificarOdontologo(@PathVariable Integer id, @RequestBody OdontologoDto odontologoDto) {
    try {
      OdontologoDto odontologo = odontologoService.modificarOdontologo(id, odontologoDto);
      return new ResponseEntity<>(odontologo, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarOdontologo(@PathVariable Integer id) {
    try {
      boolean eliminado = odontologoService.eliminarOdontologo(id);
      if (eliminado) {
        return ResponseEntity.ok("Odontólogo eliminado correctamente");
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
    try {
      OdontologoDto odontologo = odontologoService.buscarPorId(id);
      return new ResponseEntity<>(odontologo, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  @GetMapping("/nombres")
  public ResponseEntity<?> obtenerTodosPorNombres() {
    try {
      List<String> nombres = odontologoService.obtenerTodosPorNombres();
      return new ResponseEntity<>(nombres, HttpStatus.OK);
    } catch (NotFoundException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

}
