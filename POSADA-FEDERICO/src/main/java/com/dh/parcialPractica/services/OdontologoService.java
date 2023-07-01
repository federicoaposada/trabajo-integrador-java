package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OdontologoService {

    private final OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public OdontologoDto guardarOdontologo(OdontologoDto odontologoDto) {
        Odontologo odontologo = mapToEntity(odontologoDto);

        // Verificar que el nombre y apellido no estén vacíos
        if (odontologo.getNombre().isEmpty() || odontologo.getApellido().isEmpty()) {
            throw new BadRequestException("Código 103", "El nombre y apellido no pueden estar vacíos");
        }

        // Verificar que el nombre y apellido tengan al menos 2 letras
        if (odontologo.getNombre().length() < 2 || odontologo.getApellido().length() < 2) {
            throw new BadRequestException("Código 104", "El nombre y apellido deben tener al menos 2 letras");
        }

        odontologo.setNombre(capitalizarString(odontologo.getNombre()));
        odontologo.setApellido(capitalizarString(odontologo.getApellido()));

        if (odontologo.getMatricula() >= 0 && odontologo.getMatricula() <= 10000) {
            // Verificar si ya existe un odontólogo con la misma matrícula
            if (odontologoRepository.existsByMatricula(odontologo.getMatricula())) {
                throw new BadRequestException("Código 102", "Ya existe un odontólogo con la misma matrícula");
            }

            Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
            return mapToDto(odontologoGuardado);
        } else {
            throw new BadRequestException("Código 101", "El número de matrícula debe estar entre 0 y 10000");
        }
    }

    public OdontologoDto modificarOdontologo(Integer id, OdontologoDto odontologoDto) throws NotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if (odontologoOptional.isPresent()) {
            Odontologo odontologo = odontologoOptional.get();
            odontologo.setMatricula(odontologoDto.getMatricula());
            odontologo.setNombre(odontologoDto.getNombre());
            odontologo.setApellido(odontologoDto.getApellido());
            odontologo.setSueldo(odontologoDto.getSueldo());

            Odontologo odontologoModificado = odontologoRepository.save(odontologo);

            return mapToDto(odontologoModificado);
        } else {
            throw new NotFoundException("Código 105", "No se encontró el odontólogo con el ID: " + id);
        }
    }

    public List<OdontologoDto> obtenerTodosLosOdontologos() {
        try {
            List<Odontologo> odontologos = odontologoRepository.findAll();
            return odontologos.stream().map(this::mapToDto).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException("Error al obtener todos los odontólogos", exception);
        }
    }

    public boolean eliminarOdontologo(Integer id) {
        if (odontologoRepository.existsById(id)) {
            odontologoRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("No se encontró el odontólogo con el ID: " + id);
        }
    }

    public OdontologoDto buscarPorId(Integer id) {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if (odontologoOptional.isPresent()) {
            Odontologo odontologo = odontologoOptional.get();
            return mapToDto(odontologo);
        } else {
            throw new IllegalArgumentException("No se encontró el odontólogo con el ID: " + id);
        }
    }

    private String capitalizarString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    public List<String> obtenerTodosPorNombres() {
        try {
            return odontologoRepository.findAll()
                    .stream()
                    .map(Odontologo::getNombre)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException("Error al obtener todos los nombres de odontólogos", exception);
        }
    }

    private OdontologoDto mapToDto(Odontologo odontologo) {
        OdontologoDto odontologoDto = new OdontologoDto();
        odontologoDto.setId(odontologo.getId());
        odontologoDto.setMatricula(odontologo.getMatricula());
        odontologoDto.setNombre(odontologo.getNombre());
        odontologoDto.setApellido(odontologo.getApellido());
        odontologoDto.setSueldo(odontologo.getSueldo());
        return odontologoDto;
    }

    private Odontologo mapToEntity(OdontologoDto odontologoDto) {
        Odontologo odontologo = new Odontologo();
        odontologo.setId(odontologoDto.getId());
        odontologo.setMatricula(odontologoDto.getMatricula());
        odontologo.setNombre(odontologoDto.getNombre());
        odontologo.setApellido(odontologoDto.getApellido());
        odontologo.setSueldo(odontologoDto.getSueldo());
        return odontologo;
    }
}
