package com.literalura.literalura;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private int descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    // Constructor vacío requerido por JPA
    public Libro() {}

    // Constructor completo
    public Libro(String titulo, String idioma, int descargas, Autor autor,
                 Integer anioNacimiento, Integer anioFallecimiento) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autor = autor;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public int getDescargas() { return descargas; }
    public void setDescargas(int descargas) { this.descargas = descargas; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }

    public Integer getAnioFallecimiento() { return anioFallecimiento; }
    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }

    @Override
    public String toString() {
        return "Título: " + titulo +
                ", Idioma: " + idioma +
                ", Descargas: " + descargas +
                (autor != null ? ", Autor: " + autor.getNombre() : "") +
                (anioNacimiento != null ? ", Nacido: " + anioNacimiento : "") +
                (anioFallecimiento != null ? ", Fallecido: " + anioFallecimiento : "");
    }
}