package com.dh.parcialPractica.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurnoDto {

  private Integer id;
  private PacienteDto paciente;
  private OdontologoDto odontologo;
  private LocalDate fecha;
  private LocalTime hora;

}

