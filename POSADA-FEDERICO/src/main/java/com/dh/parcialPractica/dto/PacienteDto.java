package com.dh.parcialPractica.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacienteDto {
  private Integer id;
  private String nombre;
  private String apellido;
  private String mail;
  private String dni;
}
