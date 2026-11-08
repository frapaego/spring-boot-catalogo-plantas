package com.botanica.catalogo.service.impl;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.mapper.PlantaMapper;
import com.botanica.catalogo.model.Planta;
import com.botanica.catalogo.repository.PlantaRepository;
import com.botanica.catalogo.service.PlantaService;
import com.botanica.catalogo.exceptions.PlantaNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlantaServiceTest {

    @Mock
    private PlantaRepository plantaRepository;

    @Mock
    private PlantaMapper plantaMapper;

    @InjectMocks
    private PlantaServiceImpl plantaService;

    private PlantaRequestDTO requestDTO;
    private Planta plantaEntity;

    @BeforeEach
    void setUp() {
        requestDTO = new PlantaRequestDTO(
                "Tomate",
                "Solanum lycopersicum",
                3,
                LocalDate.now()
        );

        plantaEntity = Planta.builder()
                .id(1L)
                .nombreComun("Tomate")
                .nombreCientifico("Solanum lycopersicum")
                .frecuenciaRiegoDias(3)
                .fechaAlta(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("Debe crear una planta correctamente")
    void debeCrearPlanta() {
        when(plantaMapper.toEntity(requestDTO)).thenReturn(plantaEntity);
        when(plantaRepository.save(any(Planta.class))).thenReturn(plantaEntity);
        when(plantaMapper.toResponseDTO(plantaEntity)).thenReturn(
                new com.botanica.catalogo.dto.PlantaResponseDTO(
                        1L, "Tomate", "Solanum lycopersicum", 3, LocalDate.now(), LocalDate.now()
                )
        );

        var resultado = plantaService.crearPlanta(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.nombreComun()).isEqualTo("Tomate");
        assertThat(resultado.nombreCientifico()).isEqualTo("Solanum lycopersicum");
    }

    @Test
    @DisplayName("Debe devolver una planta por su ID")
    void debeObtenerPlantaPorId() {
        when(plantaRepository.findById(1L)).thenReturn(Optional.of(plantaEntity));
        when(plantaMapper.toResponseDTO(plantaEntity)).thenReturn(
                new com.botanica.catalogo.dto.PlantaResponseDTO(
                        1L, "Tomate", "Solanum lycopersicum", 3, LocalDate.now(), LocalDate.now()
                )
        );

        var resultado = plantaService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la planta no existe")
    void debeLanzarExcepcionCuandoNoExiste() {
        when(plantaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantaService.obtenerPorId(999L))
                .isInstanceOf(PlantaNotFoundException.class)
                .hasMessageContaining("no encontrada");
    }

    @Test
    @DisplayName("Debe listar todas las plantas")
    void debeListarTodasLasPlantas() {
        var planta1 = Planta.builder()
                .id(1L)
                .nombreComun("Tomate")
                .nombreCientifico("Solanum lycopersicum")
                .frecuenciaRiegoDias(3)
                .fechaAlta(LocalDate.now())
                .build();

        var planta2 = Planta.builder()
                .id(2L)
                .nombreComun("Calendula")
                .nombreCientifico("Calendula officinalis")
                .frecuenciaRiegoDias(5)
                .fechaAlta(LocalDate.now())
                .build();

        when(plantaRepository.findAll()).thenReturn(java.util.List.of(planta1, planta2));
        when(plantaMapper.toResponseDTO(planta1)).thenReturn(
                new com.botanica.catalogo.dto.PlantaResponseDTO(
                        1L, "Tomate", "Solanum lycopersicum", 3, LocalDate.now(), LocalDate.now()
                )
        );
        when(plantaMapper.toResponseDTO(planta2)).thenReturn(
                new com.botanica.catalogo.dto.PlantaResponseDTO(
                        2L, "Calendula", "Calendula officinalis", 5, LocalDate.now(), LocalDate.now()
                )
        );

        var resultados = plantaService.listarTodas();

        assertThat(resultados).hasSize(2);
        assertThat(resultados).extracting(com.botanica.catalogo.dto.PlantaResponseDTO::nombreComun)
                .containsExactlyInAnyOrder("Tomate", "Calendula");
    }
}
