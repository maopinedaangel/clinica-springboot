package com.example.clinica.controller;

import com.example.clinica.DTO.TurnoDTO;
import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.service.OdontologoService;
import com.example.clinica.service.PacienteService;
import com.example.clinica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;


    /* Buscar un turno por Id */
    @GetMapping("/{id}")
    public ResponseEntity buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response = null;
        if (turnoService.buscar(id) != null) {
            response = new ResponseEntity(turnoService.buscar(id), HttpStatus.OK);
        } else {
            response = new ResponseEntity("No se encontró el turno.", HttpStatus.NOT_FOUND);
        }
        return response;
    }



    /* Buscar todos los turnos almacenados */
    @GetMapping
    public ResponseEntity listarTurnos() {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }



    /* Crear un nuevo turno */
    @PostMapping
    public ResponseEntity agregarTurno(@RequestBody TurnoDTO turnoDTO) {
        return ResponseEntity.ok(turnoService.guardar(turnoDTO));
    }



    /* Actualizar los datos de un turno existente */
    @PutMapping
    public ResponseEntity editarTurno(@RequestBody TurnoDTO turnoDTO) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.actualizar(turnoDTO));
    }



    /* Eliminar un turno por su id */
    @DeleteMapping("/{id}")
    public ResponseEntity borrarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response;
        try {
            turnoService.eliminar(id);
            response = new ResponseEntity("Turno eliminado correctamente.", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            response = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return response;

    }
}
