package com.botanica.catalogo.exceptions;

/**
 * Excepción personalizada lanzada cuando no se encuentra una planta por su ID.
 * Hereda de RuntimeException para su uso en capas inferiores sin necesidad de
 * declararla en los métodos.
 */
public class PlantaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Identificador de la planta que no se encontró. */
    private final Long id;

    /**
     * Crea la excepción asociada al identificador solicitado.
     *
     * @param id identificador de la planta inexistente
     */
    public PlantaNotFoundException(Long id) {
        super("Planta no encontrada con id: " + id);
        this.id = id;
    }

    /**
     * Devuelve el identificador de la planta que originó la excepción.
     *
     * @return identificador de la planta inexistente
     */
    public Long getId() {
        return id;
    }
}
