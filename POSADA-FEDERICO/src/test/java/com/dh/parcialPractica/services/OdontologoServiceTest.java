package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.exception.NotFoundException;
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
    void testObtenerNombres() throws NotFoundException {
        // Arrange
        Odontologo odontologo1 = new Odontologo(1,1234, "John", "Doe", "1000000");
        Odontologo odontologo2 = new Odontologo(2, 5678, "Jane", "Smith", "2000000");
        Odontologo odontologo3 = new Odontologo(3, 9012, "Michael", "Johnson", "3000000");
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
    void testObtenerOdontologos() throws NotFoundException {
        // Arrange
        Odontologo odontologo1 = new Odontologo(1,1234, "John", "Doe", "1000000");
        Odontologo odontologo2 = new Odontologo(2, 5678, "Jane", "Smith", "2000000");
        Odontologo odontologo3 = new Odontologo(3, 9012, "Michael", "Johnson", "3000000");
        Odontologo odontologo4 = new Odontologo(4, 3456, "David", "Brown", "4000000");
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