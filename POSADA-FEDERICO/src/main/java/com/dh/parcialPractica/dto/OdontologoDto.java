package com.dh.parcialPractica.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OdontologoDto {

    private Integer id;
    private Integer matricula;
    private String nombre;
    private String apellido;
    private String sueldo;

    // Constructor

    public OdontologoDto(Integer id, Integer matricula, String nombre, String apellido, String sueldo) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sueldo = sueldo;
    }

}
