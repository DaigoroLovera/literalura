package com.literalura.literalura;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID único en la base de datos

    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    // Constructor vacío obligatorio para JPA
    public Autor() {}

    // Constructor con parámetros
    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }

    public Integer getAnioFallecimiento() { return anioFallecimiento; }
    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }

    @Override
    public String toString() {
        return "Autor: " + nombre +
                (anioNacimiento != null ? ", Nacido: " + anioNacimiento : "") +
                (anioFallecimiento != null ? ", Fallecido: " + anioFallecimiento : "");
    }
}