package com.example.clinica.controller;

import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Odontologo;
import com.example.clinica.model.Paciente;
import com.example.clinica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;


    @GetMapping("/{id}")
    public ResponseEntity buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response = null;
        if (odontologoService.buscar(id) != null) {
            response = new ResponseEntity(odontologoService.buscar(id), HttpStatus.OK);
        } else {
            response = new ResponseEntity("No se encontró el paciente", HttpStatus.NOT_FOUND);
        }
        return response;
    }



    /* Buscar todos los odontólogos */
    @GetMapping
    public ResponseEntity listarOdontologo() {
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }


    /* Buscar todos los turnos correspondientes a un odontólogo */
    @GetMapping("/turnos/{id}")
    public ResponseEntity listarTurnosPorOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarTurnosPorOdontologo(id));
    }



    /* Crear un nuevo odontólogo" */
    @PostMapping
    public ResponseEntity agregarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardar(odontologo));
    }



    /* Actualizar los datos de un odontólogo existente */
    @PutMapping
    public ResponseEntity editarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.actualizar(odontologo));
    }



    /* Eliminar un odontólogo por su id */
    @DeleteMapping("/{id}")
    public ResponseEntity <?> borrarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response;
        try {
            odontologoService.eliminar(id);
            response = new ResponseEntity("Odontólogo eliminado correctamente.", HttpStatus.OK);
        } catch(ResourceNotFoundException e) {
            response = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
