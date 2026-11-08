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

@Tag(name = "Planta", description = "Operaciones CRUD para gestión de plantas del catálogo")
public interface PlantaController {

    @Operation(summary = "Crear una nueva planta", description = "Crea una nueva planta en el catálogo con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Planta creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlantaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    ResponseEntity<PlantaResponseDTO> crearPlanta(PlantaRequestDTO requestDto);

    @Operation(summary = "Obtener planta por ID", description = "Obtiene los detalles de una planta específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlantaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Planta no encontrada")
    })
    ResponseEntity<PlantaResponseDTO> obtenerPlantaPorId(
            @Parameter(description = "ID de la planta a consultar", required = true) Long id);

    @Operation(summary = "Listar todas las plantas", description = "Obtiene la lista completa de plantas del catálogo")
    @ApiResponse(responseCode = "200", description = "Lista de plantas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlantaResponseDTO.class))))
    ResponseEntity<Iterable<PlantaResponseDTO>> listarTodas();
}
