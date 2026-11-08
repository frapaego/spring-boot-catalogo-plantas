package com.botanica.catalogo.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * DTO de respuesta corporativa para errores estandarizados.
 * Sigue el patrón RFC 7807 (Problem Details) adaptado a Java 21 records.
 */
@Data
@Builder
public class ErrorResponseDTO {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
