package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.TurnoDto;
import com.dh.parcialPractica.entity.Turno;
import com.dh.parcialPractica.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

  @Autowired
  private TurnoService service;

  @PostMapping
  public ResponseEntity<TurnoDto> guardar(@RequestBody Turno turno) {
    TurnoDto dto = service.guardar(turno);
    return null;
  }
}
