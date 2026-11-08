package com.botanica.catalogo.mapper;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import com.botanica.catalogo.model.Planta;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PlantaMapper {

    PlantaResponseDTO toResponseDTO(Planta planta);

    Planta toEntity(PlantaRequestDTO requestDto);
}
