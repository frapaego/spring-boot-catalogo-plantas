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

/**
 * Implementa los casos de uso del catálogo y sus reglas de negocio.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlantaServiceImpl implements PlantaService {

    private final PlantaRepository plantaRepository;
    private final PlantaMapper plantaMapper;

    /**
     * Registra una planta y asigna sus fechas iniciales.
     *
     * @param requestDto datos validados de la planta
     * @return representación de la planta persistida
     */
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

    /**
     * Actualiza una planta existente sin modificar su identidad ni fecha de alta.
     *
     * @param id identificador de la planta
     * @param requestDto nuevos datos validados de la planta
     * @return representación de la planta actualizada
     * @throws PlantaNotFoundException si no existe una planta con ese identificador
     * @throws IllegalArgumentException si la fecha de último riego es futura
     */
    @Override
    public PlantaResponseDTO actualizarPlanta(Long id, @Valid PlantaRequestDTO requestDto) {
        log.info("Solicitando actualización de planta con id: {}", id);
        Planta planta = plantaRepository.findById(id)
                .orElseThrow(() -> new PlantaNotFoundException(id));

        LocalDate ultimoRiego = validarFechaUltimoRiego(requestDto.ultimoRiego());
        plantaMapper.actualizarDesdeRequest(requestDto, planta);
        planta.setUltimoRiego(ultimoRiego);

        Planta actualizada = plantaRepository.save(planta);
        log.info("Planta actualizada exitosamente con id: {}", actualizada.getId());
        return plantaMapper.toResponseDTO(actualizada);
    }

    /**
     * Elimina la planta indicada si existe.
     *
     * @param id identificador de la planta
     * @throws PlantaNotFoundException si no existe una planta con ese identificador
     */
    @Override
    public void eliminarPlanta(Long id) {
        log.info("Solicitando eliminación de planta con id: {}", id);
        Planta planta = plantaRepository.findById(id)
                .orElseThrow(() -> new PlantaNotFoundException(id));
        plantaRepository.delete(planta);
        log.info("Planta eliminada exitosamente con id: {}", id);
    }

    /**
     * Busca una planta por su identificador.
     *
     * @param id identificador de la planta
     * @return representación de la planta encontrada
     * @throws PlantaNotFoundException si no existe una planta con ese identificador
     */
    @Override
    public PlantaResponseDTO obtenerPorId(Long id) {
        log.debug("Solicitando obtener planta por id: {}", id);
        
        Planta planta = plantaRepository.findById(id)
                .orElseThrow(() -> new PlantaNotFoundException(id));
        
        log.debug("Planta encontrada: {}", planta);
        return plantaMapper.toResponseDTO(planta);
    }

    /**
     * Lista las plantas persistidas en el catálogo.
     *
     * @return representaciones de todas las plantas encontradas
     */
    @Override
    public Iterable<PlantaResponseDTO> listarTodas() {
        log.debug("Solicitando listar todas las plantas");
        return plantaRepository.findAll().stream()
                .map(plantaMapper::toResponseDTO)
                .toList();
    }

    /**
     * Comprueba que la fecha de riego no sea posterior al día actual.
     *
     * @param fecha fecha recibida; puede ser {@code null}
     * @return la fecha recibida o la fecha actual cuando no se especificó
     * @throws IllegalArgumentException si la fecha recibida es futura
     */
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
