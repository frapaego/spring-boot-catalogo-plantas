package com.botanica.catalogo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CatalogoPlantasApplicationTests {

    @Test
    @DisplayName("Debe cargar la aplicación Spring con éxito")
    void contextLoads() {
        assertThat(true).isTrue();
    }
}
