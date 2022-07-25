package com.example.clinica.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {

    private Long id;
    private Long pacienteId;
    private Long odontologoId;
    private LocalDate fecha;
    private LocalTime hora;

    public TurnoDTO(Long id, Long pacienteId, Long odontologoId, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.odontologoId = odontologoId;
        this.fecha = fecha;
        this.hora = hora;
    }

    public TurnoDTO(Long pacienteId, Long odontologoId, LocalDate fecha, LocalTime hora) {
        this.pacienteId = pacienteId;
        this.odontologoId = odontologoId;
        this.fecha = fecha;
        this.hora = hora;
    }

    public TurnoDTO() {
    }

    public Long getId() {
        return id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
