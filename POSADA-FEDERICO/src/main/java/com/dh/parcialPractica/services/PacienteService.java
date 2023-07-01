package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.PacienteDto;
import com.dh.parcialPractica.entity.Paciente;
import com.dh.parcialPractica.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ObjectMapper mapper;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, ObjectMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    // Obtener todos los pacientes
    public List<PacienteDto> listarPacientes() {
        try {
            List<Paciente> listaEntidades = pacienteRepository.findAll();
            return listaEntidades.stream()
                    .map(paciente -> mapper.convertValue(paciente, PacienteDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Manejar la excepción aquí
            // Por ejemplo, puedes lanzar una excepción personalizada, registrar el error, etc.
            throw new RuntimeException("Error al obtener la lista de pacientes", e);
        }
    }

    // Guardar un nuevo paciente
    public PacienteDto guardar(PacienteDto pacienteDto) {
        if (pacienteDto.getNombre() == null || pacienteDto.getApellido() == null) {
            throw new IllegalArgumentException("Es ilegal dejar nombre o apellido vacío");
        }

        Paciente pacienteEntidad = mapToEntity(pacienteDto);
        Paciente pacienteGuardado = pacienteRepository.save(pacienteEntidad);
        return mapToDto(pacienteGuardado);
    }

    // Eliminar un paciente por su id
    public boolean eliminarPaciente(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el paciente con el ID proporcionado");
        }

        pacienteRepository.deleteById(id);
        return true;
    }

    // Buscar un paciente por su ID
    public PacienteDto buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .map(paciente -> mapToDto(paciente))
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el paciente con el ID proporcionado"));
    }

    // Modificar un paciente existente
    public PacienteDto modificarPaciente(Integer id, PacienteDto pacienteDto) {
        if (!pacienteRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el paciente con el ID proporcionado");
        }

        // Verificar si el paciente existe en la base de datos
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el paciente con el ID proporcionado"));

        // Actualizar los campos del paciente existente con los valores del pacienteDto
        pacienteExistente.setNombre(pacienteDto.getNombre());
        pacienteExistente.setApellido(pacienteDto.getApellido());
        pacienteExistente.setDni(pacienteDto.getDni());
        pacienteExistente.setMail(pacienteDto.getMail());

        // Guardar los cambios en la base de datos
        Paciente pacienteModificado = pacienteRepository.save(pacienteExistente);

        // Retornar el paciente modificado convertido a PacienteDto
        return mapToDto(pacienteModificado);
    }

    // Mapear un objeto Paciente a PacienteDto
    private PacienteDto mapToDto(Paciente paciente) {
        return mapper.convertValue(paciente, PacienteDto.class);
    }

    // Mapear un objeto PacienteDto a Paciente
    private Paciente mapToEntity(PacienteDto pacienteDto) {
        return mapper.convertValue(pacienteDto, Paciente.class);
    }

}
