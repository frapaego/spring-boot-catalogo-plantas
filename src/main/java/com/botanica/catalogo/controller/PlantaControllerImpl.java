package com.botanica.catalogo.controller;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import com.botanica.catalogo.service.PlantaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plantas")
@RequiredArgsConstructor
public class PlantaControllerImpl implements PlantaController {

    private final PlantaService plantaService;

    @PostMapping
    @Override
    public ResponseEntity<PlantaResponseDTO> crearPlanta(@Valid @RequestBody PlantaRequestDTO requestDto) {
        PlantaResponseDTO planta = plantaService.crearPlanta(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(planta);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PlantaResponseDTO> obtenerPlantaPorId(@PathVariable Long id) {
        PlantaResponseDTO planta = plantaService.obtenerPorId(id);
        return ResponseEntity.ok(planta);
    }

    @GetMapping
    @Override
    public ResponseEntity<Iterable<PlantaResponseDTO>> listarTodas() {
        Iterable<PlantaResponseDTO> plantas = plantaService.listarTodas();
        return ResponseEntity.ok(plantas);
    }
}
