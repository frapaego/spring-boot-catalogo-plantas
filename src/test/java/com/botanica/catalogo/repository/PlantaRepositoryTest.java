package com.botanica.catalogo.repository;

import com.botanica.catalogo.model.Planta;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de persistencia JPA para las operaciones CRUD de plantas.
 */
@DataJpaTest
class PlantaRepositoryTest {

    @Autowired
    private PlantaRepository plantaRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Planta plantaEjemplo;

    /**
     * Prepara una entidad válida antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        plantaEjemplo = Planta.builder()
                .nombreComun("Tomate")
                .nombreCientifico("Solanum lycopersicum")
                .frecuenciaRiegoDias(3)
                .fechaAlta(LocalDate.now())
                .ultimoRiego(LocalDate.now())
                .build();
    }

    /**
     * Comprueba que una planta nueva se persiste y recibe un identificador.
     */
    @Test
    @DisplayName("Debe guardar una planta correctamente")
    void debeGuardarPlanta() {
        Planta guardada = plantaRepository.save(plantaEjemplo);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getNombreComun()).isEqualTo("Tomate");
        assertThat(guardada.getNombreCientifico()).isEqualTo("Solanum lycopersicum");
    }

    /**
     * Comprueba que una planta persistida puede recuperarse por su ID.
     */
    @Test
    @DisplayName("Debe encontrar una planta por su ID")
    void debeEncontrarPlantaPorId() {
        Planta guardada = plantaRepository.save(plantaEjemplo);
        entityManager.flush();
        entityManager.clear();

        Planta encontrada = plantaRepository.findById(guardada.getId()).orElse(null);

        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getId()).isEqualTo(guardada.getId());
    }

    /**
     * Comprueba que el repositorio devuelve las plantas registradas.
     */
    @Test
    @DisplayName("Debe listar todas las plantas")
    void debeListarTodasLasPlantas() {
        plantaRepository.save(plantaEjemplo);

        var plantas = plantaRepository.findAll();

        assertThat(plantas).isNotNull();
        assertThat(plantas).hasSizeGreaterThanOrEqualTo(1);
    }

    /**
     * Comprueba que los cambios en una planta se guardan.
     */
    @Test
    @DisplayName("Debe actualizar una planta existente")
    void debeActualizarPlanta() {
        Planta guardada = plantaRepository.save(plantaEjemplo);

        guardada.setFrecuenciaRiegoDias(5);
        guardada.setNombreComun("Tomate Cherry");
        Planta actualizada = plantaRepository.save(guardada);

        assertThat(actualizada.getFrecuenciaRiegoDias()).isEqualTo(5);
        assertThat(actualizada.getNombreComun()).isEqualTo("Tomate Cherry");
    }

    /**
     * Comprueba que una planta persistida puede eliminarse.
     */
    @Test
    @DisplayName("Debe eliminar una planta existente")
    void debeEliminarPlanta() {
        Planta guardada = plantaRepository.save(plantaEjemplo);

        plantaRepository.delete(guardada);
        entityManager.flush();
        entityManager.clear();

        assertThat(plantaRepository.findById(guardada.getId())).isEmpty();
    }
}
