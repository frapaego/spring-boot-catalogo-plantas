package com.botanica.catalogo.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de las restricciones de Bean Validation del DTO de solicitud.
 */
class PlantaRequestDTOTest {

    private static Validator validator;
    private static AutoCloseable validatorFactory;

    /**
     * Inicializa el validador para las pruebas de restricciones.
     */
    @BeforeAll
    static void setUpValidator() {
        var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        validatorFactory = factory::close;
    }

    /**
     * Libera los recursos del proveedor de Bean Validation.
     *
     * @throws Exception si falla el cierre del proveedor
     */
    @AfterAll
    static void closeValidatorFactory() throws Exception {
        validatorFactory.close();
    }

    /**
     * Comprueba que los nombres y la frecuencia son obligatorios y válidos.
     */
    @Test
    @DisplayName("Debe validar nombres y frecuencia de riego obligatorios")
    void debeValidarCamposObligatorios() {
        PlantaRequestDTO request = new PlantaRequestDTO(" ", "", null, LocalDate.now());

        Set<String> invalidFields = validator.validate(request).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());

        assertThat(invalidFields).contains(
                "nombreComun",
                "nombreCientifico",
                "frecuenciaRiegoDias"
        );
    }

    /**
     * Comprueba que la frecuencia de riego debe ser de al menos un día.
     */
    @Test
    @DisplayName("Debe rechazar frecuencia de riego menor que un día")
    void debeRechazarFrecuenciaDeRiegoNoPositiva() {
        PlantaRequestDTO request = new PlantaRequestDTO(
                "Tomate",
                "Solanum lycopersicum",
                0,
                LocalDate.now()
        );

        assertThat(validator.validate(request))
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("frecuenciaRiegoDias");
    }
}
