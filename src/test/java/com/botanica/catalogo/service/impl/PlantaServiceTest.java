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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de las reglas de negocio del servicio de plantas.
 */
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

    /**
     * Prepara los DTOs y la entidad utilizados por las pruebas.
     */
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

    /**
     * Comprueba el registro de una planta y la conversión de su respuesta.
     */
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

    /**
     * Comprueba que la fecha del último riego toma el día actual cuando se omite.
     */
    @Test
    @DisplayName("Debe usar la fecha actual cuando no se informa el último riego")
    void debeUsarFechaActualSiNoSeInformaUltimoRiego() {
        PlantaRequestDTO requestSinFecha = new PlantaRequestDTO(
                "Tomate",
                "Solanum lycopersicum",
                3,
                null
        );
        when(plantaMapper.toEntity(requestSinFecha)).thenReturn(plantaEntity);
        when(plantaRepository.save(any(Planta.class))).thenReturn(plantaEntity);
        when(plantaMapper.toResponseDTO(plantaEntity)).thenReturn(
                new com.botanica.catalogo.dto.PlantaResponseDTO(
                        1L, "Tomate", "Solanum lycopersicum", 3, LocalDate.now(), LocalDate.now()
                )
        );

        plantaService.crearPlanta(requestSinFecha);

        assertThat(plantaEntity.getUltimoRiego()).isEqualTo(LocalDate.now());
    }

    /**
     * Comprueba que una fecha de riego futura impide crear la planta.
     */
    @Test
    @DisplayName("No debe crear una planta con fecha de riego futura")
    void noDebeCrearPlantaConFechaDeRiegoFutura() {
        PlantaRequestDTO requestConFechaFutura = new PlantaRequestDTO(
                "Tomate",
                "Solanum lycopersicum",
                3,
                LocalDate.now().plusDays(1)
        );
        when(plantaMapper.toEntity(requestConFechaFutura)).thenReturn(plantaEntity);

        assertThatThrownBy(() -> plantaService.crearPlanta(requestConFechaFutura))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no puede ser posterior");

        verify(plantaRepository, never()).save(any(Planta.class));
    }

    /**
     * Comprueba la actualización de una planta encontrada.
     */
    @Test
    @DisplayName("Debe actualizar una planta existente")
    void debeActualizarPlanta() {
        var responseDTO = new com.botanica.catalogo.dto.PlantaResponseDTO(
                1L, "Tomate", "Solanum lycopersicum", 3, LocalDate.now(), LocalDate.now()
        );
        when(plantaRepository.findById(1L)).thenReturn(Optional.of(plantaEntity));
        when(plantaRepository.save(plantaEntity)).thenReturn(plantaEntity);
        when(plantaMapper.toResponseDTO(plantaEntity)).thenReturn(responseDTO);

        var resultado = plantaService.actualizarPlanta(1L, requestDTO);

        verify(plantaMapper).actualizarDesdeRequest(requestDTO, plantaEntity);
        verify(plantaRepository).save(plantaEntity);
        assertThat(resultado).isEqualTo(responseDTO);
    }

    /**
     * Comprueba que actualizar un ID inexistente produce la excepción de dominio.
     */
    @Test
    @DisplayName("Debe lanzar excepción al actualizar una planta inexistente")
    void debeLanzarExcepcionAlActualizarPlantaInexistente() {
        when(plantaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantaService.actualizarPlanta(999L, requestDTO))
                .isInstanceOf(PlantaNotFoundException.class)
                .hasMessageContaining("no encontrada");
    }

    /**
     * Comprueba que una fecha de riego futura impide persistir la actualización.
     */
    @Test
    @DisplayName("No debe guardar una planta si la fecha de riego es futura")
    void noDebeActualizarPlantaConFechaDeRiegoFutura() {
        PlantaRequestDTO requestConFechaFutura = new PlantaRequestDTO(
                "Tomate",
                "Solanum lycopersicum",
                3,
                LocalDate.now().plusDays(1)
        );
        when(plantaRepository.findById(1L)).thenReturn(Optional.of(plantaEntity));

        assertThatThrownBy(() -> plantaService.actualizarPlanta(1L, requestConFechaFutura))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no puede ser posterior");

        verify(plantaRepository, never()).save(any(Planta.class));
    }

    /**
     * Comprueba la eliminación de una planta encontrada.
     */
    @Test
    @DisplayName("Debe eliminar una planta existente")
    void debeEliminarPlanta() {
        when(plantaRepository.findById(1L)).thenReturn(Optional.of(plantaEntity));

        plantaService.eliminarPlanta(1L);

        verify(plantaRepository).delete(plantaEntity);
    }

    /**
     * Comprueba que eliminar un ID inexistente produce la excepción de dominio.
     */
    @Test
    @DisplayName("Debe lanzar excepción al eliminar una planta inexistente")
    void debeLanzarExcepcionAlEliminarPlantaInexistente() {
        when(plantaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantaService.eliminarPlanta(999L))
                .isInstanceOf(PlantaNotFoundException.class)
                .hasMessageContaining("no encontrada");
    }

    /**
     * Comprueba que el servicio devuelve una planta encontrada por ID.
     */
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

    /**
     * Comprueba que consultar un ID inexistente produce la excepción de dominio.
     */
    @Test
    @DisplayName("Debe lanzar excepción cuando la planta no existe")
    void debeLanzarExcepcionCuandoNoExiste() {
        when(plantaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> plantaService.obtenerPorId(999L))
                .isInstanceOf(PlantaNotFoundException.class)
                .hasMessageContaining("no encontrada");
    }

    /**
     * Comprueba que el servicio devuelve todas las plantas mapeadas.
     */
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
