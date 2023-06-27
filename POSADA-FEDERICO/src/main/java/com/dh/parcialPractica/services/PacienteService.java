package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.TurnoDto;
import com.dh.parcialPractica.entity.Paciente;
import com.dh.parcialPractica.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

  @Autowired
  PacienteRepository pacienteRepository;
  @Autowired
  ObjectMapper mapper;

  public List<TurnoDto> listar() {

    List<Paciente> listaEntidades = pacienteRepository.findAll();
    List<TurnoDto> listaPacientesDto = listaEntidades
        .stream()
        .map(paciente -> mapper.convertValue(paciente, TurnoDto.class))
        .collect(Collectors.toList());

    return listaPacientesDto;
  }

  public TurnoDto guardar(Paciente paciente) {

    if (paciente.getNombre() == null || paciente.getApellido() == null) {
      throw new IllegalArgumentException("Es ilegal dejar nombre o apellido vacío");
    } else {
      Paciente pacienteEntidad = pacienteRepository.save(paciente);
      return mapper.convertValue(pacienteEntidad, TurnoDto.class);
    }
  }


}
