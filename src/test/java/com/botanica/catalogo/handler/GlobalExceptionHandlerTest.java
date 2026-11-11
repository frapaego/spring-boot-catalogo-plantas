package com.botanica.catalogo.handler;

import com.botanica.catalogo.exceptions.PlantaNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas unitarias de las respuestas HTTP de errores del catálogo.
 */
class GlobalExceptionHandlerTest {

    /**
     * Comprueba que una planta inexistente se transforma en HTTP 404.
     */
    @Test
    @DisplayName("Debe convertir planta inexistente en respuesta 404")
    void debeConvertirPlantaInexistenteEn404() {
        MockHttpServletRequest servletRequest =
                new MockHttpServletRequest("DELETE", "/api/v1/plantas/55");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        var response = handler.handlePlantaNotFoundException(
                new PlantaNotFoundException(55L),
                new ServletWebRequest(servletRequest)
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).satisfies(body -> {
            assertThat(body.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
            assertThat(body.getError()).isEqualTo("PLANTA_NOT_FOUND");
            assertThat(body.getMessage()).contains("55");
            assertThat(body.getPath()).isEqualTo("/api/v1/plantas/55");
        });
    }
}
