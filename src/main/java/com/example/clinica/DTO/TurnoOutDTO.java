package com.example.clinica.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoOutDTO {

    private Long id;
    private String nombrePaciente;
    private String nombreOdontologo;
    private String fecha;
    private String hora;

    public TurnoOutDTO() {
    }


    @SuppressWarnings("unchecked")
    @JsonProperty("paciente")
    private void unpackedPaciente(Map<String, Object> paciente) {
        this.nombrePaciente = (String) paciente.get("nombre") + " " + (String) paciente.get("apellido");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("odontologo")
    private void unpackedOdontologo(Map<String, Object> odontologo) {
        this.nombreOdontologo = (String) odontologo.get("nombre") + " " + (String) odontologo.get("apellido");
    }


}
