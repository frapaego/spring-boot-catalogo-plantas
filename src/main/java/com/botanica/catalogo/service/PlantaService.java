package com.botanica.catalogo.service;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;

public interface PlantaService {

    PlantaResponseDTO crearPlanta(PlantaRequestDTO requestDto);

    PlantaResponseDTO obtenerPorId(Long id);

    Iterable<PlantaResponseDTO> listarTodas();
}
