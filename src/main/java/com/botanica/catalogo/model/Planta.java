package com.botanica.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa una planta persistida en el catálogo.
 */
@Entity
@Table(name = "plantas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planta {

    /** Identificador generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre común de la planta. */
    @Column(nullable = false)
    private String nombreComun;

    /** Nombre científico de la planta. */
    @Column(nullable = false)
    private String nombreCientifico;

    /** Cantidad de días entre riegos. */
    @Column(nullable = false)
    private Integer frecuenciaRiegoDias;

    /** Fecha de registro, inmutable después de su creación. */
    @Column(name = "fecha_alta", nullable = false, updatable = false)
    private LocalDate fechaAlta;

    /** Fecha del último riego registrado. */
    @Column(name = "ultimo_riego", nullable = false)
    private LocalDate ultimoRiego;
}
