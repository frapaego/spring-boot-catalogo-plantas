package com.botanica.catalogo.exceptions;

/**
 * Excepción personalizada lanzada cuando no se encuentra una planta por su ID.
 * Hereda de RuntimeException para su uso en capas inferiores sin necesidad de
 * declararla en los métodos.
 */
public class PlantaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long id;

    public PlantaNotFoundException(Long id) {
        super("Planta no encontrada con id: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
