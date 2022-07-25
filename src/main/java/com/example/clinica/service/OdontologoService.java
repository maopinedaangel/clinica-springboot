package com.example.clinica.service;

import com.example.clinica.DTO.TurnoOutDTO;
import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Odontologo;
import com.example.clinica.model.Turno;
import com.example.clinica.repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(OdontologoService.class);


    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public OdontologoService() {
    }


    /* Busca un odontólogo por su id*/
    public Odontologo buscar(Long id) throws ResourceNotFoundException {
        logger.info("Buscando odontologo con id " + id + "...");
        if (!odontologoRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el odontólogo.");
        } else {
            return odontologoRepository.findById(id).orElse(null);
        }

    }

    /* Busca todos los odontólogos registrados en la base de datos */
    public List<Odontologo> buscarTodos() {
        logger.info("Buscando todos los odontólogos...");
        return odontologoRepository.findAll();
    }

    /* Buscar todos los turnos para un odontólogo determinado, con base en su Id */
    public List<TurnoOutDTO> buscarTurnosPorOdontologo(Long id) throws ResourceNotFoundException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        logger.info("Buscando los turnos para el odontólogo " + id);
        if (!odontologoRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el odontólogo.");
        } else {
            Set<Turno> listaTurnos = odontologoRepository.findById(id).get().getTurnos();
            List<TurnoOutDTO> listaTurnosOut = new ArrayList<>();
            for (Turno t: listaTurnos) {
                TurnoOutDTO turnoOut = mapper.convertValue(t, TurnoOutDTO.class);
                listaTurnosOut.add(turnoOut);
            }
            return listaTurnosOut;
        }
    }

    /* Elimina un odontólogo a partir de su id */
    public boolean eliminar(Long id) throws ResourceNotFoundException {
        boolean existeOdontologo = false;
        logger.info("Buscando odontólogo con id " + id + "...");
        if (!odontologoRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el odontólogo.");
        } else {
            odontologoRepository.deleteById(id);
            logger.info("El odontólogo con id " + id + " fue eliminado exitosamente.");
            existeOdontologo = true;
        }
        return existeOdontologo;
    }

    /* Crea un nuevo odontólogo en la base de datos */
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        logger.info("Odontólogo agregado a la base de datos. id = " + odontologoGuardado.getId());
        return odontologoGuardado;
    }

    /* Actualiza los datos de un odontólogo */
    public Odontologo actualizar(Odontologo odontologo) {
        Optional<Odontologo> odontologoAEditar = odontologoRepository.findById(odontologo.getId());
        Odontologo odontologoGuardado = null;
        if (odontologoAEditar.isPresent()) {
            odontologoGuardado = odontologoAEditar.get();
            odontologoGuardado.setNombre(odontologo.getNombre());
            odontologoGuardado.setApellido(odontologo.getApellido());
            odontologoGuardado.setMatricula(odontologo.getMatricula());
            odontologoGuardado.setTurnos(odontologo.getTurnos());
            odontologoRepository.save(odontologoGuardado);
        }
        return odontologoGuardado;
    }

    /* Permite agregar un nuevo turno a la lista de turnos del odontólogo */
    public void agregarTurno(Long odontologoId, Turno turno) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoEnBD = odontologoRepository.findById(odontologoId);
        if (!odontologoEnBD.isPresent()) {
            throw new ResourceNotFoundException("No se encontró el odontólogo.");
        } else {
            Odontologo odontologoEncontrado = odontologoEnBD.get();
            Set<Turno> listaTurnos = odontologoEncontrado.getTurnos();
            listaTurnos.add(turno);
            odontologoEncontrado.setTurnos(listaTurnos);
            odontologoRepository.save(odontologoEncontrado);
        }
    }
}
