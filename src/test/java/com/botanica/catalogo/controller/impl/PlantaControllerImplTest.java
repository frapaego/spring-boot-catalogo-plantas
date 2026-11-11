package com.botanica.catalogo.controller.impl;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import com.botanica.catalogo.service.PlantaService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de los códigos HTTP y delegación del controlador de plantas.
 */
@ExtendWith(MockitoExtension.class)
class PlantaControllerImplTest {

    @Mock
    private PlantaService plantaService;

    private PlantaControllerImpl controller;
    private PlantaRequestDTO requestDto;
    private PlantaResponseDTO responseDto;

    /**
     * Prepara el controlador y los DTOs comunes de las pruebas.
     */
    @BeforeEach
    void setUp() {
        controller = new PlantaControllerImpl(plantaService);
        requestDto = new PlantaRequestDTO(
                "Tomate",
                "Solanum lycopersicum",
                3,
                LocalDate.now()
        );
        responseDto = new PlantaResponseDTO(
                1L,
                "Tomate",
                "Solanum lycopersicum",
                3,
                LocalDate.now(),
                LocalDate.now()
        );
    }

    /**
     * Comprueba que crear una planta devuelve HTTP 201 y delega en el servicio.
     */
    @Test
    @DisplayName("Debe responder 201 y delegar la creación al servicio")
    void debeCrearPlanta() {
        when(plantaService.crearPlanta(requestDto)).thenReturn(responseDto);

        var response = controller.crearPlanta(requestDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(responseDto);
        verify(plantaService).crearPlanta(requestDto);
    }

    /**
     * Comprueba la respuesta y delegación de una actualización correcta.
     */
    @Test
    @DisplayName("Debe responder 200 y delegar la actualización al servicio")
    void debeActualizarPlanta() {
        when(plantaService.actualizarPlanta(1L, requestDto)).thenReturn(responseDto);

        var response = controller.actualizarPlanta(1L, requestDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(responseDto);
        verify(plantaService).actualizarPlanta(1L, requestDto);
    }

    /**
     * Comprueba la respuesta sin contenido y delegación de una eliminación correcta.
     */
    @Test
    @DisplayName("Debe responder 204 y delegar la eliminación al servicio")
    void debeEliminarPlanta() {
        var response = controller.eliminarPlanta(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(plantaService).eliminarPlanta(1L);
    }

    /**
     * Comprueba que consultar por ID devuelve HTTP 200 y delega en el servicio.
     */
    @Test
    @DisplayName("Debe responder 200 y delegar la consulta por ID al servicio")
    void debeObtenerPlantaPorId() {
        when(plantaService.obtenerPorId(1L)).thenReturn(responseDto);

        var response = controller.obtenerPlantaPorId(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(responseDto);
        verify(plantaService).obtenerPorId(1L);
    }

    /**
     * Comprueba que listar devuelve HTTP 200 y la colección del servicio.
     */
    @Test
    @DisplayName("Debe responder 200 y delegar el listado al servicio")
    void debeListarPlantas() {
        List<PlantaResponseDTO> plantas = List.of(responseDto);
        when(plantaService.listarTodas()).thenReturn(plantas);

        var response = controller.listarTodas();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(responseDto);
        verify(plantaService).listarTodas();
    }
}
