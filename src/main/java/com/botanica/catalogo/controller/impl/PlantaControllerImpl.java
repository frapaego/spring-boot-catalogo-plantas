package com.botanica.catalogo.controller.impl;

import com.botanica.catalogo.controller.PlantaController;
import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import com.botanica.catalogo.service.PlantaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación REST de las operaciones disponibles para el catálogo de plantas.
 */
@RestController
@RequestMapping("/api/v1/plantas")
@RequiredArgsConstructor
public class PlantaControllerImpl implements PlantaController {

    private final PlantaService plantaService;

    /**
     * Crea una planta a partir del cuerpo validado de la solicitud.
     *
     * @param requestDto datos de la planta
     * @return respuesta HTTP 201 con la planta creada
     */
    @PostMapping
    @Override
    public ResponseEntity<PlantaResponseDTO> crearPlanta(@Valid @RequestBody PlantaRequestDTO requestDto) {
        PlantaResponseDTO planta = plantaService.crearPlanta(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(planta);
    }

    /**
     * Actualiza una planta y devuelve su representación actualizada.
     *
     * @param id identificador de la planta que se va a actualizar
     * @param requestDto datos nuevos de la planta
     * @return respuesta HTTP 200 con la planta actualizada
     */
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<PlantaResponseDTO> actualizarPlanta(
            @PathVariable Long id,
            @Valid @RequestBody PlantaRequestDTO requestDto) {
        PlantaResponseDTO planta = plantaService.actualizarPlanta(id, requestDto);
        return ResponseEntity.ok(planta);
    }

    /**
     * Elimina la planta indicada y devuelve una respuesta sin contenido.
     *
     * @param id identificador de la planta que se va a eliminar
     * @return respuesta HTTP 204 sin cuerpo
     */
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> eliminarPlanta(@PathVariable Long id) {
        plantaService.eliminarPlanta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recupera una planta por su identificador.
     *
     * @param id identificador de la planta
     * @return respuesta HTTP 200 con la planta encontrada
     */
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PlantaResponseDTO> obtenerPlantaPorId(@PathVariable Long id) {
        PlantaResponseDTO planta = plantaService.obtenerPorId(id);
        return ResponseEntity.ok(planta);
    }

    /**
     * Recupera todas las plantas del catálogo.
     *
     * @return respuesta HTTP 200 con la colección de plantas
     */
    @GetMapping
    @Override
    public ResponseEntity<Iterable<PlantaResponseDTO>> listarTodas() {
        Iterable<PlantaResponseDTO> plantas = plantaService.listarTodas();
        return ResponseEntity.ok(plantas);
    }
}
