package com.botanica.catalogo.service;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;

/**
 * Casos de uso disponibles para la gestión de plantas.
 */
public interface PlantaService {

    /**
     * Registra una nueva planta en el catálogo.
     *
     * @param requestDto datos de la planta que se va a crear
     * @return representación de la planta persistida
     */
    PlantaResponseDTO crearPlanta(PlantaRequestDTO requestDto);

    /**
     * Actualiza los datos editables de una planta existente.
     *
     * @param id identificador de la planta
     * @param requestDto nuevos datos de la planta
     * @return representación de la planta actualizada
     * @throws com.botanica.catalogo.exceptions.PlantaNotFoundException si no existe una planta con ese ID
     */
    PlantaResponseDTO actualizarPlanta(Long id, PlantaRequestDTO requestDto);

    /**
     * Elimina una planta existente.
     *
     * @param id identificador de la planta
     * @throws com.botanica.catalogo.exceptions.PlantaNotFoundException si no existe una planta con ese ID
     */
    void eliminarPlanta(Long id);

    /**
     * Recupera una planta por su identificador.
     *
     * @param id identificador de la planta
     * @return representación de la planta encontrada
     * @throws com.botanica.catalogo.exceptions.PlantaNotFoundException si no existe una planta con ese ID
     */
    PlantaResponseDTO obtenerPorId(Long id);

    /**
     * Recupera todas las plantas del catálogo.
     *
     * @return representaciones de las plantas encontradas; vacío si no hay registros
     */
    Iterable<PlantaResponseDTO> listarTodas();
}
