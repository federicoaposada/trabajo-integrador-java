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

  // Obtener todos los odontólogos
  @GetMapping
  public ResponseEntity<List<OdontologoDto>> obtenerTodos() {
    List<OdontologoDto> odontologos = odontologoService.obtenerTodosLosOdontologos();
    return new ResponseEntity<>(odontologos, HttpStatus.OK);
  }

  // Crear un nuevo odontólogo
  @PostMapping
  public ResponseEntity<?> guardarOdontologo(@RequestBody OdontologoDto odontologoDto) {
    try {
      OdontologoDto odontologo = odontologoService.guardarOdontologo(odontologoDto);
      return new ResponseEntity<>(odontologo, HttpStatus.CREATED);
    } catch (BadRequestException exception) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(exception.getCodigoError(), exception.getMensaje());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Modificar los atributos de un odontólogo existente
  @PutMapping("/{id}")
  public ResponseEntity<?> modificarOdontologo(@PathVariable Integer id, @RequestBody OdontologoDto odontologoDto) {
    try {
      OdontologoDto odontologo = odontologoService.modificarOdontologo(id, odontologoDto);
      return new ResponseEntity<>(odontologo, HttpStatus.OK);
    } catch (NotFoundException e) {
      ClinicaErrorResponse errorResponse = new ClinicaErrorResponse(e.getCodigoError(), e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
  }

  // Eliminar un odontólogo existente
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarOdontologo(@PathVariable Integer id) {
    boolean deleted = odontologoService.eliminarOdontologo(id);
    if (deleted) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Buscar un odontólogo por su ID
  @GetMapping("/{id}")
  public ResponseEntity<OdontologoDto> buscarPorId(@PathVariable Integer id) {
    try {
      OdontologoDto odontologo = odontologoService.buscarPorId(id);
      return new ResponseEntity<>(odontologo, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
