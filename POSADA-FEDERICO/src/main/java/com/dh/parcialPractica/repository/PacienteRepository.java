package com.dh.parcialPractica.repository;

import com.dh.parcialPractica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    boolean existsByDniAndIdNot(Integer dni, Integer id);
}
