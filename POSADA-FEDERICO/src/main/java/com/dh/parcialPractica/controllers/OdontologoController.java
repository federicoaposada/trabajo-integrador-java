package com.dh.parcialPractica.controllers;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.services.OdontologoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

  @Autowired
  private OdontologoService service;

  @GetMapping("/{id}")
  public ResponseEntity<OdontologoDto> getById(@PathVariable Integer id) {
    OdontologoDto odontologoDto = service.buscarPorID(id);

    if (odontologoDto != null) {
      return ResponseEntity.status(HttpStatus.OK).body(odontologoDto);
    }

    ResponseEntity<OdontologoDto> response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    return response;
  }

  @GetMapping
  public List<OdontologoDto> listar() {
    return service.listar();
  }

  @GetMapping("/busqueda/")
  public List<OdontologoDto> buscarPorApellido(@RequestParam String apellido) {
    String inicial = apellido.substring(0, 1);
    String resto = apellido.substring(1);
    apellido = inicial.toUpperCase() + resto.toLowerCase();
    return service.buscarPorApellido(apellido);
  }

  @PostMapping()
  public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo o) {
    return ResponseEntity.ok(service.guardar(o));
  }

  @DeleteMapping("/eliminar/")
  public void eliminar(@RequestParam int id) {
    service.eliminar(id);
  }

}
