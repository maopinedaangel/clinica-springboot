package com.example.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Paciente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private String email;

    @Column(name="fecha_de_ingreso")
    private LocalDate fechaDeIngreso;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="domicilio_id", referencedColumnName="id")
    private Domicilio domicilio;

    @OneToMany(mappedBy="paciente", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
    @JsonIgnore
    private Set<Turno> turnos;


    public Paciente(String nombre, String apellido, String dni, String email, LocalDate fechaDeIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaDeIngreso = fechaDeIngreso;
        this.domicilio = domicilio;
        this.turnos = new HashSet<Turno>();
    }

    public Paciente(Long id, String nombre, String apellido, String dni, String email, LocalDate fechaDeIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaDeIngreso = fechaDeIngreso;
        this.domicilio = domicilio;
        this.turnos = new HashSet<Turno>();
    }

    public Paciente() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaDeIngreso() {
        return fechaDeIngreso;
    }

    public void setFechaDeIngreso(LocalDate fechaDeIngreso) {
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", fechaDeIngreso=" + fechaDeIngreso +
                ", domicilio=" + domicilio +
                '}';
    }
}
