package com.botanica.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Schema(name = "PlantaRequestDTO", description = "DTO para crear una nueva planta")
public record PlantaRequestDTO(
    @NotBlank(message = "El nombre común es obligatorio")
    @Schema(description = "Nombre común de la planta", example = "Tomate")
    String nombreComun,

    @NotBlank(message = "El nombre científico es obligatorio")
    @Schema(description = "Nombre científico de la planta", example = "Solanum lycopersicum")
    String nombreCientifico,

    @Min(value = 1, message = "La frecuencia de riego debe ser al menos 1 día")
    @Schema(description = "Frecuencia de riego en días", example = "3", minimum = "1")
    Integer frecuenciaRiegoDias,

    @Schema(description = "Fecha del último riego", example = "2026-11-07")
    LocalDate ultimoRiego
) {
}

