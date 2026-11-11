package com.botanica.catalogo.repository;

import com.botanica.catalogo.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Acceso CRUD a las plantas persistidas.
 */
@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {
}
