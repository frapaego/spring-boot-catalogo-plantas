package com.botanica.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación Spring Boot del catálogo de plantas.
 */
@SpringBootApplication
public class CatalogoPlantasApplication {

    /**
     * Inicia el contexto de Spring y la aplicación web.
     *
     * @param args argumentos recibidos al iniciar el proceso
     */
    public static void main(String[] args) {
        SpringApplication.run(CatalogoPlantasApplication.class, args);
    }
}
