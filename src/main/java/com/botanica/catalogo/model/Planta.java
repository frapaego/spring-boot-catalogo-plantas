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

@Entity
@Table(name = "plantas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreComun;

    @Column(nullable = false)
    private String nombreCientifico;

    @Column(nullable = false)
    private Integer frecuenciaRiegoDias;

    @Column(name = "fecha_alta", nullable = false, updatable = false)
    private LocalDate fechaAlta;

    @Column(name = "ultimo_riego", nullable = false)
    private LocalDate ultimoRiego;
}
