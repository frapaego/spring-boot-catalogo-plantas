package com.botanica.catalogo.mapper;

import com.botanica.catalogo.dto.PlantaRequestDTO;
import com.botanica.catalogo.model.Planta;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas unitarias de las conversiones del mapper de plantas.
 */
class PlantaMapperTest {

    private final PlantaMapper plantaMapper = Mappers.getMapper(PlantaMapper.class);

    /**
     * Comprueba que la actualización mapea los datos editables y conserva la identidad.
     */
    @Test
    @DisplayName("Debe actualizar los campos editables sin cambiar ID ni fecha de alta")
    void debeActualizarCamposEditablesYConservarDatosDeAlta() {
        LocalDate fechaAltaOriginal = LocalDate.of(2024, 3, 10);
        Planta planta = Planta.builder()
                .id(7L)
                .nombreComun("Tomate")
                .nombreCientifico("Solanum lycopersicum")
                .frecuenciaRiegoDias(3)
                .fechaAlta(fechaAltaOriginal)
                .ultimoRiego(LocalDate.of(2026, 11, 1))
                .build();
        PlantaRequestDTO requestDto = new PlantaRequestDTO(
                "Tomate cherry",
                "Solanum lycopersicum var. cerasiforme",
                5,
                LocalDate.of(2026, 11, 9)
        );

        plantaMapper.actualizarDesdeRequest(requestDto, planta);

        assertThat(planta.getId()).isEqualTo(7L);
        assertThat(planta.getFechaAlta()).isEqualTo(fechaAltaOriginal);
        assertThat(planta.getNombreComun()).isEqualTo("Tomate cherry");
        assertThat(planta.getNombreCientifico()).isEqualTo("Solanum lycopersicum var. cerasiforme");
        assertThat(planta.getFrecuenciaRiegoDias()).isEqualTo(5);
        assertThat(planta.getUltimoRiego()).isEqualTo(LocalDate.of(2026, 11, 9));
    }
}
