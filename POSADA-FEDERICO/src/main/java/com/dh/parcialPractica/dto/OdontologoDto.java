package com.dh.parcialPractica.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OdontologoDto {
  private Integer id;
  private int matricula;
  private String nombre;
  private String apellido;
  private String sueldo;

}
