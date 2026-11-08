package com.botanica.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "PlantaResponseDTO", description = "DTO para respuesta de la planta")
public record PlantaResponseDTO(
    @Schema(description = "ID de la planta", example = "1")
    Long id,

    @Schema(description = "Nombre común de la planta", example = "Tomate")
    String nombreComun,

    @Schema(description = "Nombre científico de la planta", example = "Solanum lycopersicum")
    String nombreCientifico,

    @Schema(description = "Frecuencia de riego en días", example = "3")
    Integer frecuenciaRiegoDias,

    @Schema(description = "Fecha de alta", example = "2026-11-07")
    LocalDate fechaAlta,

    @Schema(description = "Fecha del último riego", example = "2026-11-07")
    LocalDate ultimoRiego
) {
}

