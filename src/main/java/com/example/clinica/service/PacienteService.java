package com.example.clinica.service;

import com.example.clinica.DTO.TurnoOutDTO;
import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Paciente;
import com.example.clinica.model.Turno;
import com.example.clinica.repository.IPacienteRepository;
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
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(PacienteService.class);



    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteService() {
    }


    /* Busca un paciente por su id */
    public Paciente buscar(Long id) throws ResourceNotFoundException {
        logger.info("Buscando paciente con id " + id + "...");
        if (!pacienteRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el paciente.");
        } else {
            return pacienteRepository.findById(id).orElse(null);
        }
    }



    /* Buscar todos los pacientes registrados en la base de datos */
    public List<Paciente> buscarTodos() {
        logger.info("Buscando todos los pacientes...");
        return pacienteRepository.findAll();
    }



    /* Buscar todos los turnos para un paciente determinado, con base en su Id */
    public List<TurnoOutDTO> buscarTurnosPorPaciente(Long id) throws ResourceNotFoundException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        logger.info("Buscando los turnos para el paciente " + id);
        if (!pacienteRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el paciente.");
        } else {
            Set<Turno> listaTurnos = pacienteRepository.findById(id).get().getTurnos();
            List<TurnoOutDTO> listaTurnosOut = new ArrayList<>();
            for (Turno t: listaTurnos) {
                TurnoOutDTO turnoOut = mapper.convertValue(t, TurnoOutDTO.class);
                listaTurnosOut.add(turnoOut);
            }
            return listaTurnosOut;
        }
    }



    /* Elimina un paciente a partir de su id */
    public boolean eliminar(Long id) throws ResourceNotFoundException {
        boolean existePaciente = false;
        logger.info("Se eliminará el paciente con id " + id + "...");
        if (!pacienteRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el paciente.");
        } else {
            pacienteRepository.deleteById(id);
            logger.info("El paciente fue eliminado exitosamente.");
            existePaciente = true;
        }
        return existePaciente;
    }



    /* Crea un nuevo paciente en la base de datos */
    public Paciente guardar(Paciente paciente) {
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        logger.info("Paciente agregado a la base de datos. id = " + pacienteGuardado.getId());
        return pacienteGuardado;
    }



    /* Actualiza los datos de un paciente */
    public Paciente actualizar(Paciente paciente) {
        Optional<Paciente> pacienteAEditar = pacienteRepository.findById(paciente.getId());
        Paciente pacienteGuardado = null;
        if (pacienteAEditar.isPresent()) {
            pacienteGuardado = pacienteAEditar.get();
            pacienteGuardado.setNombre(paciente.getNombre());
            pacienteGuardado.setApellido(paciente.getApellido());
            pacienteGuardado.setDni(paciente.getDni());
            pacienteGuardado.setEmail(paciente.getEmail());
            pacienteGuardado.setFechaDeIngreso(paciente.getFechaDeIngreso());
            pacienteGuardado.setDomicilio(paciente.getDomicilio());
            pacienteGuardado.setTurnos(paciente.getTurnos());
            pacienteRepository.save(pacienteGuardado);
        }
         return pacienteGuardado;
    }



    /* Permite agregar un nuevo turno a la lista de turnos del paciente */
    public void agregarTurno(Long pacienteId, Turno turno) throws ResourceNotFoundException {
        Optional<Paciente> pacienteEnBD = pacienteRepository.findById(pacienteId);
        if (!pacienteEnBD.isPresent()) {
            throw new ResourceNotFoundException("No se encontró el paciente.");
        } else {
            Paciente pacienteEncontrado = pacienteEnBD.get();
            Set<Turno> listaTurnos = pacienteEncontrado.getTurnos();
            listaTurnos.add(turno);
            pacienteEncontrado.setTurnos(listaTurnos);
            pacienteRepository.save(pacienteEncontrado);
        }
    }

}
