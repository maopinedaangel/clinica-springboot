package com.example.clinica.service;

import com.example.clinica.exceptions.ResourceNotFoundException;
import com.example.clinica.model.Domicilio;
import com.example.clinica.model.Paciente;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;


@SpringBootTest
public class PacienteServiceTest {


    @Autowired
    PacienteService pacienteService;
    private static final Logger logger = Logger.getLogger(PacienteServiceTest.class);
    Long idPaciente;



    /* Crea un paciente de prueba en la base de datos*/
    @BeforeEach
    public void cargarDatos() {
        logger.info("Cargando datos de prueba a la base de datos...");
        Domicilio domicilio = new Domicilio("Farallones", 850L, "Envigado", "Antioquia");
        Paciente paciente = new Paciente("Felipe", "Pirela", "31520462", "fpirela@gmail.com", LocalDate.of(2020, 4, 30), domicilio);
        Paciente pacienteEnBD = pacienteService.guardar(paciente);
        idPaciente = pacienteEnBD.getId();
    }


    /* Crea un paciente, guarda su Id y a continuación verifica que está en la base de datos y el nombre coincide con el guardado */
    @Test
    public void testCrearYBuscarPaciente() throws ResourceNotFoundException {
        logger.info("Test: Buscar Paciente");
        Assert.assertEquals(pacienteService.buscar(idPaciente).getNombre(), "Felipe");
    }


    /* Elimina el paciente guardado como dato de prueba y comprueba que ya no se encuentra en la base de datos */
    @Test
    public void testEliminarYBuscarPaciente() throws ResourceNotFoundException {
        logger.info("Test: Eliminar y Buscar Paciente");
        pacienteService.eliminar(idPaciente);
        Paciente pacienteEnBD;
        try {
            pacienteEnBD = pacienteService.buscar(idPaciente);
        } catch(ResourceNotFoundException e) {
            logger.error(e.getMessage());
            pacienteEnBD = null;
        }
        Assert.assertNull(pacienteEnBD);
    }

}
