package com.example.clinica.service;

import com.example.clinica.DTO.TurnoDTO;
import com.example.clinica.DTO.TurnoOutDTO;
import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Odontologo;
import com.example.clinica.model.Paciente;
import com.example.clinica.model.Turno;
import com.example.clinica.repository.IOdontologoRepository;
import com.example.clinica.repository.IPacienteRepository;
import com.example.clinica.repository.ITurnoRepository;
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
public class TurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    private static final Logger logger = Logger.getLogger(TurnoService.class);

    @Autowired
    private ObjectMapper mapper;


    public TurnoService(ITurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public TurnoService() {
    }



    /* Busca un turno por su id */
    public Turno buscar(Long id) throws ResourceNotFoundException {
        logger.info("Buscando turno con id " + id + "...");
        if (!turnoRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el turno.");
        } else {
            return turnoRepository.findById(id).orElse(null);
        }
    }



    /* Busca todos los turnos registrados en la base de datos */
    public List<TurnoOutDTO> buscarTodos() {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        logger.info("Buscando todos los turnos...");
        List<Turno> listaTurnos = turnoRepository.findAll();
        List<TurnoOutDTO> listaTurnosOut = new ArrayList<>();
        for (Turno t: listaTurnos) {
            TurnoOutDTO turnoOut = mapper.convertValue(t, TurnoOutDTO.class);
            listaTurnosOut.add(turnoOut);
        }
        return listaTurnosOut;
    }




    /* Permite eliminar un turno por su id */
    public boolean eliminar(Long id) throws ResourceNotFoundException {
        boolean existeTurno = false;
        logger.info("Buscando turno con id " + id + "...");
        if (!turnoRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("No se encontró el turno.");
        } else {
            turnoRepository.deleteById(id);
            logger.info("El turno con id " + id + " fue eliminado exitosamente.");
            existeTurno = true;
        }
        return existeTurno;
    }




    /* Crea un turno nuevo en la base de datos */
    public Turno guardar(TurnoDTO turnoDTO) {
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        Paciente paciente = pacienteRepository.findById(turnoDTO.getPacienteId()).get();
        Odontologo odontologo = odontologoRepository.findById(turnoDTO.getOdontologoId()).get();

        /* Se agrega el turno a la lista de turnos del paciente */
        Set<Turno> listaTurnosPaciente = paciente.getTurnos();
        listaTurnosPaciente.add(turno);
        paciente.setTurnos(listaTurnosPaciente);
        turno.setPaciente(pacienteRepository.save(paciente));

        /* Se agrega el turno a la lista de turnos del odontólogo */
        Set<Turno> listaTurnosOdontologo = odontologo.getTurnos();
        listaTurnosOdontologo.add(turno);
        odontologo.setTurnos(listaTurnosOdontologo);
        turno.setOdontologo(odontologoRepository.save(odontologo));

        /* Se alamacena el turno en la base de datos */
        Turno turnoGuardado = turnoRepository.save(turno);
        logger.info("Turno agregado a la base de datos. id = " + turnoGuardado.getId());

        return turnoGuardado;
    }



    /* Actualiza los datos de un turno existente, y actualiza la lista de turnos del paciente y el odontólogo correspondientes */
    public Turno actualizar(TurnoDTO turnoDTO) throws ResourceNotFoundException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        Optional<Turno> turnoAEditar = turnoRepository.findById(turnoDTO.getId());
        Turno turnoGuardado = null;
        if (turnoAEditar.isPresent()) {
            turnoGuardado = turnoAEditar.get();

            /* Se agrega el turno a la lista de turnos del paciente */
            pacienteService.agregarTurno(turnoDTO.getPacienteId(), turno);
            if (pacienteRepository.existsById(turnoDTO.getPacienteId())) {
                turnoGuardado.setPaciente(pacienteRepository.findById(turnoDTO.getPacienteId()).get());
            }

            /* Se agrega el turno a la lista de turnos del odontólogo */
            odontologoService.agregarTurno(turnoDTO.getOdontologoId(), turno);
            if (odontologoRepository.existsById(turnoDTO.getOdontologoId())) {
                turnoGuardado.setOdontologo(odontologoRepository.findById(turnoDTO.getOdontologoId()).get());
            }
            turnoGuardado.setFecha(turno.getFecha());
            turnoGuardado.setHora(turno.getHora());
            turnoRepository.save(turnoGuardado);
        }
        //TurnoDTO turnoOut = mapper.convertValue(turnoGuardado, TurnoDTO.class);
        return turnoGuardado;
    }
}
