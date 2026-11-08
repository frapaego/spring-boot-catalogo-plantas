package com.botanica.catalogo.service.impl;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import com.botanica.catalogo.exceptions.PlantaNotFoundException;
import com.botanica.catalogo.mapper.PlantaMapper;
import com.botanica.catalogo.model.Planta;
import com.botanica.catalogo.repository.PlantaRepository;
import com.botanica.catalogo.service.PlantaService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantaServiceImpl implements PlantaService {

    private final PlantaRepository plantaRepository;
    private final PlantaMapper plantaMapper;

    @Override
    public PlantaResponseDTO crearPlanta(@Valid PlantaRequestDTO requestDto) {
        log.info("Solicitando creación de planta: {}", requestDto.nombreComun());
        Planta planta = plantaMapper.toEntity(requestDto);
        planta.setFechaAlta(LocalDate.now());
        planta.setUltimoRiego(validarFechaUltimoRiego(requestDto.ultimoRiego()));

        Planta guardada = plantaRepository.save(planta);
        log.info("Planta creada exitosamente con id: {}", guardada.getId());
        return plantaMapper.toResponseDTO(guardada);
    }

    @Override
    public PlantaResponseDTO obtenerPorId(Long id) {
        log.debug("Solicitando obtener planta por id: {}", id);
        
        Planta planta = plantaRepository.findById(id)
                .orElseThrow(() -> new PlantaNotFoundException(id));
        
        log.debug("Planta encontrada: {}", planta);
        return plantaMapper.toResponseDTO(planta);
    }

    @Override
    public Iterable<PlantaResponseDTO> listarTodas() {
        log.debug("Solicitando listar todas las plantas");
        return plantaRepository.findAll().stream()
                .map(plantaMapper::toResponseDTO)
                .toList();
    }

    private LocalDate validarFechaUltimoRiego(LocalDate fecha) {
        if (fecha == null) {
            return LocalDate.now();
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del último riego no puede ser posterior al día actual");
        }
        return fecha;
    }
}
