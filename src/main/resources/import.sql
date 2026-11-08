-- Datos de carga inicial para el catálogo de plantas botánicas
-- Compatible con H2 Database
-- Las columnas usan snake_case tal como Hibernate genera a partir de la entidad Planta

-- El nombre de la tabla es 'plantas' según la anotación @Table(name = "plantas")
-- Las columnas son: id, nombre_comun, nombre_cientifico, frecuencia_riego_dias, fecha_alta, ultimo_riego

INSERT INTO plantas (nombre_comun, nombre_cientifico, frecuencia_riego_dias, fecha_alta, ultimo_riego) VALUES ('Gardenia', 'Gardenia jasminoides', 3, CURRENT_DATE(), DATEADD('DAY', -3, CURRENT_DATE()));
INSERT INTO plantas (nombre_comun, nombre_cientifico, frecuencia_riego_dias, fecha_alta, ultimo_riego) VALUES ('Limonero Caviar', 'Citrus australasica', 5, CURRENT_DATE(), DATEADD('DAY', -5, CURRENT_DATE()));
INSERT INTO plantas (nombre_comun, nombre_cientifico, frecuencia_riego_dias, fecha_alta, ultimo_riego) VALUES ('Madroño', 'Arbutus unedo', 7, CURRENT_DATE(), DATEADD('DAY', -7, CURRENT_DATE()));
