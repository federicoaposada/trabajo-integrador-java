package com.dh.parcialPractica.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Odontologo {

  public Odontologo(Integer matricula, String nombre, String apellido) {
    this.matricula = matricula;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer matricula;
  private String nombre;
  private String apellido;
  private String sueldo;

}
