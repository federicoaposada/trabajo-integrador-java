package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.TurnoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.entity.Paciente;
import com.dh.parcialPractica.entity.Turno;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.exception.OdontologoNotFoundException;
import com.dh.parcialPractica.exception.PacienteNotFoundException;
import com.dh.parcialPractica.repository.OdontologoRepository;
import com.dh.parcialPractica.repository.PacienteRepository;
import com.dh.parcialPractica.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    private final ObjectMapper mapper;
    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;

    public TurnoService(ObjectMapper mapper,TurnoRepository turnoRepository,
                        PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository) {
        this.mapper = mapper;
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
    }

    public TurnoDto guardarTurno(TurnoDto turnoDto) throws NotFoundException, BadRequestException {
        validarTurnoDto(turnoDto);

        Integer turnoId = turnoDto.getId();
        if (turnoId != null && turnoRepository.existsById(turnoId)) {
            throw new NotFoundException("Código 304", "No se puede crear un turno con un ID existente");
        }

        Turno turno = mapToEntity(turnoDto, Turno.class);
        turnoRepository.save(turno);
        return turnoDto;
    }


    public Turno actualizarTurno(Integer id, TurnoDto turnoDto) throws NotFoundException, BadRequestException {

        Optional<Turno> turnoExistenteOptional = turnoRepository.findById(id);
        if (turnoExistenteOptional.isPresent()) {
            Turno turnoExistente = turnoExistenteOptional.get();

            Integer pacienteId = turnoDto.getPaciente().getId();
            Integer odontologoId = turnoDto.getOdontologo().getId();

            validarTurnoDto(turnoDto);

            turnoExistente.setFecha(turnoDto.getFecha());
            turnoExistente.setHora(turnoDto.getHora());

            // Si los IDs de paciente y odontólogo no cambian, no es necesario actualizar los objetos completos
            if (!pacienteId.equals(turnoExistente.getPaciente().getId())) {
                Paciente paciente = mapToEntity(turnoDto.getPaciente(), Paciente.class);
                turnoExistente.setPaciente(paciente);
            }

            if (!odontologoId.equals(turnoExistente.getOdontologo().getId())) {
                Odontologo odontologo = mapToEntity(turnoDto.getOdontologo(), Odontologo.class);
                turnoExistente.setOdontologo(odontologo);
            }

            return turnoRepository.save(turnoExistente);
        } else {
            throw new NotFoundException("Código 303", "No se encontró el turno con el ID: " + id);
        }
    }


    public List<TurnoDto> obtenerTodos() throws NotFoundException {
        List<Turno> turnos = turnoRepository.findAll();
        if (turnos.isEmpty()) {
            throw new NotFoundException("Código 302", "No se encontraron turnos");
        }
        return turnos.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public boolean eliminarTurno(Integer id) throws NotFoundException {
        if (turnoRepository.existsById(id)) {
            turnoRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException("Código 303", "No se encontró el odontólogo con el ID: " + id);
        }
    }

    public TurnoDto buscarPorId(Integer id) throws NotFoundException {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        return turnoOptional.map(this::mapToDto)
                .orElseThrow(() -> new NotFoundException("Código 303", "No se encontró el odontólogo con el ID: " + id));
    }

    private void validarTurnoDto(TurnoDto turnoDto) throws BadRequestException, NotFoundException {
        if (turnoDto.getPaciente() == null || turnoDto.getOdontologo() == null ||
                turnoDto.getFecha() == null || turnoDto.getHora() == null) {
            throw new BadRequestException("Código 301", "No se pueden crear/modificar turnos con datos nulos");
        }

        Integer pacienteId = turnoDto.getPaciente().getId();
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);
        if (pacienteOptional.isEmpty()) {
            throw new PacienteNotFoundException("Código 305", "El paciente con el ID: " + pacienteId + " no existe");
        }

        Integer odontologoId = turnoDto.getOdontologo().getId();
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(odontologoId);
        if (odontologoOptional.isEmpty()) {
            throw new OdontologoNotFoundException("Código 306", "El odontólogo con el ID: " + odontologoId + " no existe");
        }
    }

    private <T> T mapToEntity(Object source, Class<T> targetClass) {
        return mapper.convertValue(source, targetClass);
    }

    private TurnoDto mapToDto(Turno turno) {
        return mapToEntity(turno, TurnoDto.class);
    }
}