package com.dh.parcialPractica.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacienteDto {

  private Integer id;
  private String nombre;
  private String apellido;
  private String mail;
  private String dni;

  private DomicilioDto domicilio;

  public PacienteDto(Integer id, String nombre, String apellido, String mail, String dni) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.mail = mail;
    this.dni = dni;

  }

}
