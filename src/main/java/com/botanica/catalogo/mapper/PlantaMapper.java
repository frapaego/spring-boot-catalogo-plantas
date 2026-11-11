package com.botanica.catalogo.mapper;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import com.botanica.catalogo.model.Planta;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Convierte entre la entidad de persistencia y los DTOs de la API.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PlantaMapper {

    /**
     * Convierte una entidad de planta en su respuesta para la API.
     *
     * @param planta entidad que se va a convertir
     * @return DTO de respuesta correspondiente
     */
    PlantaResponseDTO toResponseDTO(Planta planta);

    /**
     * Crea una entidad a partir de los datos de solicitud.
     *
     * @param requestDto datos recibidos
     * @return entidad con los campos mapeados
     */
    Planta toEntity(PlantaRequestDTO requestDto);

    /**
     * Copia los campos editables de la solicitud a una entidad existente.
     *
     * @param requestDto nuevos valores de la planta
     * @param planta entidad que se actualiza; se conservan su identificador y fecha de alta
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaAlta", ignore = true)
    void actualizarDesdeRequest(PlantaRequestDTO requestDto, @MappingTarget Planta planta);
}
