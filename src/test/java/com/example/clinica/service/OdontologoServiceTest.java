package com.example.clinica.service;

import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class OdontologoServiceTest {


    @Autowired
    OdontologoService odontologoService;
    private static final Logger logger = Logger.getLogger(PacienteServiceTest.class);
    Long idOdontologo;



    /* Crea un odontólogo de prueba en la base de datos*/
    @BeforeEach
    public void cargarDatos() {
        logger.info("Cargando datos de prueba a la base de datos...");
        Odontologo odontologo = new Odontologo("Martha", "Alvarez", "204060");
        Odontologo odontologoEnBD = odontologoService.guardar(odontologo);
        idOdontologo = odontologoEnBD.getId();
    }


    /* Crea un odontólogo, guarda su Id y a continuación verifica que está en la base de datos y el nombre coincide con el guardado */
    @Test
    public void testCrearYBuscarOdontologo() throws ResourceNotFoundException {
        logger.info("Test: Crear y Buscar Odontólogo");
        Assert.assertEquals(odontologoService.buscar(idOdontologo).getNombre(), "Martha");
    }


    /* Elimina el odontólogo guardado como dato de prueba y comprueba que ya no se encuentra en la base de datos */
    @Test
    public void testEliminarYBuscarOdontologo() throws ResourceNotFoundException {
        logger.info("Test: Eliminar y Buscar Odontólogo");
        odontologoService.eliminar(idOdontologo);
        Odontologo odontologoEnBD;
        try {
            odontologoEnBD = odontologoService.buscar(idOdontologo);
        } catch(ResourceNotFoundException e) {
            logger.error(e.getMessage());
            odontologoEnBD = null;
        }
        Assert.assertNull(odontologoEnBD);
    }

}