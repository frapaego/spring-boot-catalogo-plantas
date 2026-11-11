package com.botanica.catalogo.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * Respuesta estandarizada con los detalles públicos de un error HTTP.
 */
@Data
@Builder
public class ErrorResponseDTO {

    /** Fecha y hora en que se generó la respuesta. */
    private final LocalDateTime timestamp = LocalDateTime.now();

    /** Código numérico de estado HTTP. */
    private final int status;

    /** Código estable que identifica el tipo de error. */
    private final String error;

    /** Mensaje legible del error. */
    private final String message;

    /** Ruta que originó el error. */
    private final String path;
}
