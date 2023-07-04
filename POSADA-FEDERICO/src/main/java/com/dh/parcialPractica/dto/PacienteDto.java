package com.dh.parcialPractica.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PacienteDto {

  private Integer id;
  private String nombre;
  private String apellido;
  private String mail;
  private Integer dni;
  private DomicilioDto domicilio;
  private LocalDate fechaIngreso;

}