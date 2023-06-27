package com.dh.parcialPractica.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurnoDto {

  private Integer id;
  private PacienteDto paciente;
  private OdontologoDto odontologo;
  private LocalDate fecha;
  private LocalTime hora;

}
