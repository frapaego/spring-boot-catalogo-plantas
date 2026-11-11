package com.botanica.catalogo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de arranque e integración del contexto principal de Spring.
 */
@SpringBootTest
@ActiveProfiles("test")
class CatalogoPlantasApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Comprueba que Spring inicia el contexto y registra el controlador REST.
     */
    @Test
    @DisplayName("Debe cargar la aplicación Spring con éxito")
    void contextLoads() {
        assertThat(applicationContext.getBean("plantaControllerImpl")).isNotNull();
    }
}
