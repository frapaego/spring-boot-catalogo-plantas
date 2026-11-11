package com.botanica.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * Representación de una planta devuelta por la API.
 *
 * @param id identificador único de la planta
 * @param nombreComun nombre común de la planta
 * @param nombreCientifico nombre científico de la planta
 * @param frecuenciaRiegoDias frecuencia de riego expresada en días
 * @param fechaAlta fecha en que se registró la planta
 * @param ultimoRiego fecha del último riego registrado
 */
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
