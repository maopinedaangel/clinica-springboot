package com.example.clinica.controller;

import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Paciente;
import com.example.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;



    /* Buscar paciente por Id */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response = null;
        if (pacienteService.buscar(id) != null) {
            response = new ResponseEntity(pacienteService.buscar(id), HttpStatus.OK);
        } else {
            response = new ResponseEntity("No se encontró el paciente", HttpStatus.NOT_FOUND);
        }
        return response;
    }



    /* Buscar todos los pacientes */
    @GetMapping
    public ResponseEntity listarPacientes() {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }



    /* Buscar todos los turnos correspondientes a un paciente */
    @GetMapping("/turnos/{id}")
    public ResponseEntity listarTurnosPorPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarTurnosPorPaciente(id));
    }



    /* Crear un nuevo paciente */
    @PostMapping
    public ResponseEntity agregarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardar(paciente));
    }



    /* Actualizar los datos de un oaciente existente */
    @PutMapping
    public ResponseEntity editarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.actualizar(paciente));
    }



    /* Eliminar un paciente por su id */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response;
        try {
            pacienteService.eliminar(id);
            response = new ResponseEntity("Paciente eliminado correctamente.", HttpStatus.OK);
        } catch(ResourceNotFoundException e) {
            response = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
