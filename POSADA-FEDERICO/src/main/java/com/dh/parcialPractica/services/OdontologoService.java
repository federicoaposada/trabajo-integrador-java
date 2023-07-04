package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.repository.OdontologoRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OdontologoService {

    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<OdontologoDto> obtenerTodosLosOdontologos() throws NotFoundException {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        if (odontologos.isEmpty()) {
            throw new NotFoundException("Código 101", "No se encontraron odontólogos");
        }
        return modelMapper.map(odontologos, new TypeToken<List<OdontologoDto>>() {}.getType());
    }

    public OdontologoDto guardarOdontologo(OdontologoDto odontologoDto) {
        Odontologo odontologo = mapToEntity(odontologoDto);

        if (odontologo.getNombre().isEmpty() || odontologo.getApellido().isEmpty()) {
            throw new BadRequestException("Código 102", "El nombre y apellido no pueden estar vacíos");
        }

        if (odontologo.getNombre().length() < 3 || odontologo.getApellido().length() < 3) {
            throw new BadRequestException("Código 103", "El nombre y apellido deben tener al menos 2 letras");
        }

        odontologo.setNombre(capitalizarString(odontologo.getNombre()));
        odontologo.setApellido(capitalizarString(odontologo.getApellido()));

        if (odontologo.getMatricula() >= 0 && odontologo.getMatricula() <= 10000) {
            if (odontologoRepository.existsByMatricula(odontologo.getMatricula())) {
                throw new BadRequestException("Código 104", "Ya existe un odontólogo con la misma matrícula");
            }

            Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
            return mapToDto(odontologoGuardado);
        } else {
            throw new BadRequestException("Código 105", "El número de matrícula debe estar entre 0 y 10000");
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
            throw new NotFoundException("Código 106", "No se encontró el odontólogo con el ID: " + id);
        }
    }

    public boolean eliminarOdontologo(Integer id) throws NotFoundException {
        if (odontologoRepository.existsById(id)) {
            odontologoRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException("Código 106", "No se encontró el odontólogo con el ID: " + id);
        }
    }

    public OdontologoDto buscarPorId(Integer id) throws NotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        return odontologoOptional.map(this::mapToDto)
                .orElseThrow(() -> new NotFoundException("Código 106", "No se encontró el odontólogo con el ID: " + id));
    }

    private String capitalizarString(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public List<String> obtenerTodosPorNombres() throws NotFoundException {
        try {
            return odontologoRepository.findAll()
                    .stream()
                    .map(Odontologo::getNombre)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new NotFoundException("Código 107", "Error al obtener todos los nombres de odontólogos");
        }
    }

    private OdontologoDto mapToDto(Odontologo odontologo) {
        return modelMapper.map(odontologo, OdontologoDto.class);
    }

    private Odontologo mapToEntity(OdontologoDto odontologoDto) {
        return modelMapper.map(odontologoDto, Odontologo.class);
    }

}
