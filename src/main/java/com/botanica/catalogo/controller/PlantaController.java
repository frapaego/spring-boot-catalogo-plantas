package com.botanica.catalogo.controller;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.dto.PlantaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * Contrato REST para las operaciones CRUD del catálogo de plantas.
 */
@Tag(name = "Planta", description = "Operaciones CRUD para gestión de plantas del catálogo")
public interface PlantaController {

    /**
     * Crea una planta con los datos recibidos.
     *
     * @param requestDto datos de la planta que se va a crear
     * @return la planta creada con estado HTTP 201
     */
    @Operation(summary = "Crear una nueva planta", description = "Crea una nueva planta en el catálogo con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Planta creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlantaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    ResponseEntity<PlantaResponseDTO> crearPlanta(PlantaRequestDTO requestDto);

    /**
     * Reemplaza los datos editables de una planta existente.
     *
     * @param id identificador de la planta
     * @param requestDto nuevos datos de la planta
     * @return la planta actualizada con estado HTTP 200
     */
    @Operation(summary = "Actualizar una planta", description = "Actualiza los datos de una planta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planta actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlantaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Planta no encontrada")
    })
    ResponseEntity<PlantaResponseDTO> actualizarPlanta(
            @Parameter(description = "ID de la planta a actualizar", required = true) Long id,
            PlantaRequestDTO requestDto);

    /**
     * Elimina una planta existente del catálogo.
     *
     * @param id identificador de la planta que se va a eliminar
     * @return respuesta vacía con estado HTTP 204
     */
    @Operation(summary = "Eliminar una planta", description = "Elimina una planta existente del catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Planta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Planta no encontrada")
    })
    ResponseEntity<Void> eliminarPlanta(
            @Parameter(description = "ID de la planta a eliminar", required = true) Long id);

    /**
     * Busca una planta por su identificador.
     *
     * @param id identificador de la planta
     * @return la planta encontrada con estado HTTP 200
     */
    @Operation(summary = "Obtener planta por ID", description = "Obtiene los detalles de una planta específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlantaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Planta no encontrada")
    })
    ResponseEntity<PlantaResponseDTO> obtenerPlantaPorId(
            @Parameter(description = "ID de la planta a consultar", required = true) Long id);

    /**
     * Recupera todas las plantas del catálogo.
     *
     * @return la colección de plantas con estado HTTP 200
     */
    @Operation(summary = "Listar todas las plantas", description = "Obtiene la lista completa de plantas del catálogo")
    @ApiResponse(responseCode = "200", description = "Lista de plantas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlantaResponseDTO.class))))
    ResponseEntity<Iterable<PlantaResponseDTO>> listarTodas();
}
