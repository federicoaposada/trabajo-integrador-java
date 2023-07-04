package com.dh.parcialPractica.repository;

import com.dh.parcialPractica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    List<Turno> findByFechaAndOdontologoIdAndHora(LocalDate fecha, Integer odontologoId, LocalTime hora);
}
