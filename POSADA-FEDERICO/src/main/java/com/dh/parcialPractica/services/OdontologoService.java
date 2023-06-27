package com.dh.parcialPractica.services;

import com.dh.parcialPractica.dto.OdontologoDto;
import com.dh.parcialPractica.entity.Odontologo;
import com.dh.parcialPractica.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OdontologoService {

  //private static final Logger LOG = Logger.getLogger(OdontologoService.class);

  @Autowired
  private OdontologoRepository odontologoRepository;
  @Autowired
  ObjectMapper mapper;

  public OdontologoService(OdontologoRepository odontologoRepository) {
    this.odontologoRepository = odontologoRepository;
  }

  public Odontologo guardar(Odontologo odontologo) {

    // Primera letra del nombre en mayúsculas
    String inicialN = odontologo.getNombre().substring(0, 1);
    String restoN = odontologo.getNombre().substring(1);
    odontologo.setNombre(inicialN.toUpperCase() + restoN.toLowerCase());

    // Primera letra del apellido en mayúsculas
    String inicialA = odontologo.getApellido().substring(0, 1);
    String restoA = odontologo.getApellido().substring(1);
    odontologo.setApellido(inicialA.toUpperCase() + restoA.toLowerCase());

    if (odontologo.getMatricula() > 0 && odontologo.getMatricula() <= 10000) {
      odontologoRepository.save(odontologo);
    } else {
      throw new IllegalArgumentException("El número de matrícula debe estar entre 0 y 10000");
    }
    return odontologo;
  }

  public List<OdontologoDto> listar() {

    List<OdontologoDto> listaOdontologosDto = odontologoRepository.findAll()
        .stream()
        .map(odontologo -> mapper.convertValue(odontologo, OdontologoDto.class))
        .collect(Collectors.toList());

    return listaOdontologosDto;
  }

  public List<OdontologoDto> buscarPorApellido(String apellido) {
    //TODO FALTA
    //return odontologoRepository.buscarPorApellido(apellido);
    return null;
  }

  public void eliminar(int id) {
    // TODO EL MANEJO EXCECTIONS
    odontologoRepository.deleteById(id);
  }


  public OdontologoDto buscarPorID(Integer id) throws NoSuchElementException {
    Optional<Odontologo> odontologo = odontologoRepository.findById(id);
    if (odontologo.isPresent()) {
      return mapper.convertValue(odontologo.get(), OdontologoDto.class);
    } else {
      throw new NoSuchElementException("El odontologo no existe");
    }

  }


}
