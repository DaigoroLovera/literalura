package com.literalura.literalura;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Buscar autor por nombre ignorando mayúsculas/minúsculas
    Autor findByNombreIgnoreCase(String nombre);

    // Buscar autores vivos en un año específico
    // Incluye autores que aún no fallecieron (anioFallecimiento = null)
    // o que murieron después del año dado
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNullOrAnioFallecimientoGreaterThanEqual(int anioNacimiento, int anioFallecimiento);
}