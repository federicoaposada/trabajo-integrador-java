package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.PacienteDto;
import com.dh.parcialPractica.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

  private final PacienteService pacienteService;

  @Autowired
  public PacienteController(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  // Obtener todos los pacientes
  @GetMapping
  public ResponseEntity<List<PacienteDto>> obtenerTodos() {
    List<PacienteDto> pacientes = pacienteService.listarPacientes();
    return new ResponseEntity<>(pacientes, HttpStatus.OK);
  }

  // Crear un nuevo paciente
  @PostMapping
  public ResponseEntity<PacienteDto> guardarPaciente(@RequestBody PacienteDto pacienteDto) {
    PacienteDto paciente = pacienteService.guardar(pacienteDto);
    return new ResponseEntity<>(paciente, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PacienteDto> modificarPaciente(@PathVariable Integer id, @RequestBody PacienteDto pacienteDto) {
    PacienteDto pacienteModificado = pacienteService.modificarPaciente(id, pacienteDto);
    if (pacienteModificado != null) {
      return new ResponseEntity<>(pacienteModificado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Eliminar un paciente existente
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
    boolean eliminado = pacienteService.eliminarPaciente(id);
    if (eliminado) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Obtener un paciente por su ID
  @GetMapping("/{id}")
  public ResponseEntity<PacienteDto> obtenerPorId(@PathVariable Integer id) {
    PacienteDto paciente = pacienteService.buscarPorId(id);
    return new ResponseEntity<>(paciente, HttpStatus.OK);
  }
}
