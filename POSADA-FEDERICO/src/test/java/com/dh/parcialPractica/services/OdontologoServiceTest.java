package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.repository.OdontologoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OdontologoServiceTest {

    @Autowired
    private OdontologoRepository odontologoRepository;
    private OdontologoService odontologoService;

    @BeforeEach
    void setUp() {
        odontologoService = new OdontologoService (odontologoRepository);
    }

    @AfterEach
    void tearDown() {
        odontologoRepository.deleteAll();
    }

    @Test
    void testObtenerNombres() {
        // Arrange
        Odontologo odontologo1 = new Odontologo(1234, "John", "Doe");
        Odontologo odontologo2 = new Odontologo(5678, "Jane", "Smith");
        Odontologo odontologo3 = new Odontologo(9012, "Michael", "Johnson");
        List<String> expectedNombres = new ArrayList<>();
        expectedNombres.add("John");
        expectedNombres.add("Jane");
        expectedNombres.add("Michael");

        // Act
        odontologoRepository.save(odontologo1);
        odontologoRepository.save(odontologo2);
        odontologoRepository.save(odontologo3);
        List<String> nombresRegistrados = odontologoService.obtenerTodosPorNombres();

        // Assert
        assertEquals(expectedNombres, nombresRegistrados);
    }

    @Test
    void testObtenerOdontologos() {
        // Arrange
        Odontologo odontologo1 = new Odontologo(1234, "John", "Doe");
        Odontologo odontologo2 = new Odontologo(5678, "Jane", "Smith");
        Odontologo odontologo3 = new Odontologo(9012, "Michael", "Johnson");
        Odontologo odontologo4 = new Odontologo(3456, "David", "Brown");
        List<Odontologo> expectedOdontologos = new ArrayList<>();
        expectedOdontologos.add(odontologo1);
        expectedOdontologos.add(odontologo2);
        expectedOdontologos.add(odontologo3);
        expectedOdontologos.add(odontologo4);

        // Act
        odontologoRepository.save(odontologo1);
        odontologoRepository.save(odontologo2);
        odontologoRepository.save(odontologo3);
        odontologoRepository.save(odontologo4);
        List<OdontologoDto> odontologosRegistrados = odontologoService.obtenerTodosLosOdontologos();

        // Assert
        assertEquals(expectedOdontologos.size(), odontologosRegistrados.size());
    }
}