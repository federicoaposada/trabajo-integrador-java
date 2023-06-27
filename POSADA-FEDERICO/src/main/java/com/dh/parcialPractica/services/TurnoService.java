package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.TurnoDto;
import com.dh.parcialPractica.entity.Turno;
import com.dh.parcialPractica.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoService {

  @Autowired
  TurnoRepository turnoRepository;
  @Autowired
  ObjectMapper mapper;

  public List<TurnoDto> listar() {

    List<Turno> listaEntidades = turnoRepository.findAll();
    List<TurnoDto> listaPacientesDto = listaEntidades
        .stream()
        .map(turno -> mapper.convertValue(turno, TurnoDto.class))
        .collect(Collectors.toList());

    return listaPacientesDto;
  }

  public TurnoDto guardar(Turno turno) {

    Turno turno1 = turnoRepository.save(turno);
    return mapper.convertValue(turno1, TurnoDto.class);
  }
}