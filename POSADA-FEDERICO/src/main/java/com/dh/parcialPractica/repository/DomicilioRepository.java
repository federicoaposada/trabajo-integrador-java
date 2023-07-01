package com.dh.parcialPractica.repository;

import com.dh.parcialPractica.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {
}

// La interfaz DomicilioRepository extiende la interfaz JpaRepository proporcionada por Spring Data JPA.
// Al hacerlo, hereda métodos y funcionalidades para realizar operaciones de CRUD (Crear, Leer, Actualizar, Eliminar) en la entidad Domicilio.
// No es necesario agregar métodos adicionales en esta interfaz, ya que los métodos básicos de CRUD se heredan de JpaRepository.