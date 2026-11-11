package com.botanica.catalogo.handler;

import com.botanica.catalogo.dto.ErrorResponseDTO;
import com.botanica.catalogo.exceptions.PlantaNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura las excepciones en todos los controladores y las transforma
 * en respuestas HTTP estandarizadas con formato corporativo.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Convierte errores de validación de cuerpos de solicitud en HTTP 400.
     *
     * @param ex excepción de validación producida por Spring MVC
     * @param request solicitud que originó el error
     * @return respuesta de error HTTP 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("VALIDATION_ERROR")
                .message("Una o más validaciones fallaron")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Convierte una búsqueda fallida de planta en HTTP 404.
     *
     * @param ex excepción con el identificador no encontrado
     * @param request solicitud que originó el error
     * @return respuesta de error HTTP 404
     */
    @ExceptionHandler(PlantaNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePlantaNotFoundException(
            PlantaNotFoundException ex,
            WebRequest request) {

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("PLANTA_NOT_FOUND")
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Convierte violaciones de Bean Validation en HTTP 400.
     *
     * @param ex excepción con las restricciones incumplidas
     * @param request solicitud que originó el error
     * @return respuesta de error HTTP 400
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request) {

        Map<String, String> constraintViolations = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            constraintViolations.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("CONSTRAINT_VIOLATION")
                .message("Una o más restricciones de datos no se cumplieron")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Convierte excepciones no controladas en una respuesta genérica HTTP 500.
     *
     * @param ex excepción no controlada
     * @param request solicitud que originó el error
     * @return respuesta genérica de error HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex,
            WebRequest request) {

        // Logging interno del error (en un entorno de producción se usaría un logger proper)
        System.err.println("Error interno no controlado: " + ex.getClass().getName() + " - " + ex.getMessage());

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("INTERNAL_SERVER_ERROR")
                .message("Ocurrió un error interno inesperado. Por favor, inténtalo más tarde.")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
