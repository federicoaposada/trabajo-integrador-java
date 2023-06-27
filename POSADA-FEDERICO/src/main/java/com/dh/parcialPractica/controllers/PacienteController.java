package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.TurnoDto;
import com.dh.parcialPractica.entity.Paciente;
import com.dh.parcialPractica.services.PacienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
  @Autowired
  private PacienteService service;

  @GetMapping
  public List<TurnoDto> listar() {
    return service.listar();
  }

  @PostMapping()
  public ResponseEntity<TurnoDto> guardar(@RequestBody Paciente paciente) {
    return ResponseEntity.ok(service.guardar(paciente));
  }
}
