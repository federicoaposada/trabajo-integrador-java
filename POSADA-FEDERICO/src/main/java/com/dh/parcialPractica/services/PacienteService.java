package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.PacienteDto;
import com.dh.parcialPractica.entity.Paciente;
import com.dh.parcialPractica.exception.BadRequestException;
import com.dh.parcialPractica.exception.NotFoundException;
import com.dh.parcialPractica.repository.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<PacienteDto> obtenerTodosLosPacientes() throws NotFoundException {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            return pacientes.stream()
                    .map(paciente -> modelMapper.map(paciente, PacienteDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new NotFoundException("Código 201", "No se encontraron pacientes");
        }
    }

    public PacienteDto guardaPaciente(PacienteDto pacienteDto) {
        Paciente paciente = mapToEntity(pacienteDto);

        if (paciente.getNombre().isEmpty() || paciente.getApellido().isEmpty()) {
            throw new BadRequestException("Código 202", "El nombre o apellido no pueden estar vacíos");
        }

        if (paciente.getFechaIngreso() != null && paciente.getFechaIngreso().isBefore(LocalDate.now())) {
            throw new BadRequestException("Código 203", "La fecha de ingreso no puede ser anterior a la fecha actual");
        }

        validarDni(pacienteDto.getDni());
        validarDniUnico(pacienteDto.getDni(), pacienteDto.getId());

        Paciente pacienteEntidad = mapToEntity(pacienteDto);
        Paciente pacienteGuardado = pacienteRepository.save(pacienteEntidad);
        return mapToDto(pacienteGuardado);
    }

    public PacienteDto modificarPaciente(Integer id, PacienteDto pacienteDto) throws NotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            paciente.setDni(pacienteDto.getDni());
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setApellido(pacienteDto.getApellido());
            paciente.setFechaIngreso(pacienteDto.getFechaIngreso());

            validarDni(paciente.getDni());
            validarDniUnico(paciente.getDni(), id);

            Paciente pacienteModificado = pacienteRepository.save(paciente);
            return modelMapper.map(pacienteModificado, PacienteDto.class);
        } else {
            throw new NotFoundException("Código 208", "No se encontró el paciente con el ID: " + id);
        }
    }

    public void eliminarPaciente(Integer id) throws NotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isEmpty()) {
            throw new NotFoundException("Código 208", "No se encontró el paciente con el ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    public PacienteDto buscarPorId(Integer id) throws NotFoundException {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Código 208", "No se encontró el paciente con el ID: " + id));
        return modelMapper.map(paciente, PacienteDto.class);
    }

    public void validarDni(Integer dni) {
        if (dni == null) {
            throw new BadRequestException("Código 204", "El DNI no puede ser nulo");
        }

        if (dni <= 0) {
            throw new BadRequestException("Código 205", "El DNI debe ser mayor a 0");
        }

        String dniString = String.valueOf(dni);
        if (dniString.length() < 8) {
            throw new BadRequestException("Código 206", "El DNI debe tener al menos 8 dígitos");
        }

        if (!dniString.matches("\\d+")) {
            throw new BadRequestException("Código 209", "El DNI debe contener solamente números");
        }
    }

    public void validarDniUnico(Integer dni, Integer id) {
        if (dni != null && pacienteRepository.existsByDniAndIdNot(dni, id)) {
            throw new BadRequestException("Código 207", "Ya existe un paciente con el mismo DNI");
        }
    }

    private PacienteDto mapToDto(Paciente paciente) {
        return modelMapper.map(paciente, PacienteDto.class);
    }

    private Paciente mapToEntity(PacienteDto pacienteDto) {
        return modelMapper.map(pacienteDto, Paciente.class);
    }
}
