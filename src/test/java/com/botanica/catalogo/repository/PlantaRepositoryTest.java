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

@DataJpaTest
class PlantaRepositoryTest {

    @Autowired
    private PlantaRepository plantaRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Planta plantaEjemplo;

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

    @Test
    @DisplayName("Debe guardar una planta correctamente")
    void debeGuardarPlanta() {
        Planta guardada = plantaRepository.save(plantaEjemplo);

        assertThat(guardada).isNotNull();
        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getNombreComun()).isEqualTo("Tomate");
        assertThat(guardada.getNombreCientifico()).isEqualTo("Solanum lycopersicum");
    }

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

    @Test
    @DisplayName("Debe listar todas las plantas")
    void debeListarTodasLasPlantas() {
        plantaRepository.save(plantaEjemplo);

        var plantas = plantaRepository.findAll();

        assertThat(plantas).isNotNull();
        assertThat(plantas).hasSizeGreaterThanOrEqualTo(1);
    }

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
}
