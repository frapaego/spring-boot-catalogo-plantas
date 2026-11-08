# Catálogo de Plantas

API REST para registrar y consultar plantas de un catálogo. El proyecto está desarrollado con Java 21 y Spring Boot 3.5.2.

## Arquitectura

La aplicación organiza el código por capas y responsabilidades:

```text
Cliente HTTP
    │
    ▼
Controller ──► Service ──► Repository ──► Base de datos
    │              │
    └── DTO ◄── Mapper ◄── Modelo JPA
```

- **Controller**: expone los endpoints REST y convierte sus resultados en respuestas HTTP. El contrato de `PlantaController` contiene la documentación OpenAPI; `PlantaControllerImpl` implementa las operaciones.
- **Service**: concentra los casos de uso y reglas de negocio, como asignar fechas y validar que la fecha del último riego no sea futura.
- **Repository**: abstrae el acceso a datos mediante Spring Data JPA.
- **Model**: representa la entidad `Planta` persistida en la base de datos.
- **DTO**: separa los datos de entrada y salida de la entidad de persistencia.
- **Mapper**: transforma entre DTOs y entidades con MapStruct.
- **GlobalExceptionHandler**: convierte excepciones y errores de validación en respuestas HTTP de error.

## Patrones y principios aplicados

- **Arquitectura en capas**: separa el transporte HTTP, los casos de uso y la persistencia.
- **Repository**: delega las operaciones de persistencia a `PlantaRepository`, que extiende `JpaRepository`.
- **Data Transfer Object (DTO)**: utiliza records para los DTOs de solicitud y respuesta, sin exponer la entidad JPA desde los endpoints.
- **Mapper**: usa MapStruct para centralizar la conversión entre entidad y DTO.
- **Inyección de dependencias**: Spring administra los componentes; Lombok genera el constructor requerido en las clases que usan `@RequiredArgsConstructor`.
- **Manejo centralizado de excepciones**: `@RestControllerAdvice` mantiene las respuestas de error en un punto común.
- **Contrato API separado**: las anotaciones OpenAPI de los endpoints se mantienen en la interfaz `PlantaController`.

## Endpoints disponibles

El context path configurado es `/catalogo-plantas` y la base de la API es `/api/v1/plantas`.

| Método | Ruta | Descripción | Respuesta esperada |
|---|---|---|---|
| `POST` | `/api/v1/plantas` | Crea una planta | `201 Created` |
| `GET` | `/api/v1/plantas/{id}` | Consulta una planta por ID | `200 OK` |
| `GET` | `/api/v1/plantas` | Lista las plantas | `200 OK` |

Por ejemplo, con la aplicación en `localhost:8080`, la ruta completa para listar plantas es:

```text
http://localhost:8080/catalogo-plantas/api/v1/plantas
```

La interfaz Swagger UI y el documento OpenAPI se sirven bajo el context path de la aplicación:

```text
http://localhost:8080/catalogo-plantas/swagger-ui.html
http://localhost:8080/catalogo-plantas/v3/api-docs
```

## Validación y errores

`PlantaRequestDTO` aplica Bean Validation para exigir nombres no vacíos y una frecuencia de riego mínima de un día. El controlador valida el cuerpo de la solicitud al crear una planta. `GlobalExceptionHandler` genera respuestas de error con estado HTTP, código, mensaje, fecha y ruta.

## Tecnologías

- Java 21
- Spring Boot 3.5.2
- Spring Web
- Spring Data JPA
- Jakarta Bean Validation
- H2 en memoria, configurada en `application.yml`
- MapStruct
- Lombok
- SpringDoc OpenAPI
- JUnit 5, Mockito, AssertJ y Spring Boot Test
- Gradle Wrapper

## Ejecutar el proyecto

Se requiere un JDK 21. Para iniciar la aplicación:

```bash
./gradlew bootRun
```

En Windows:

```bat
gradlew.bat bootRun
```

La configuración actual usa una base H2 en memoria, por lo que los datos no están destinados a persistir entre reinicios.

## Ejecutar las pruebas

```bash
./gradlew test
```

Las pruebas existentes cubren la lógica del servicio, el repositorio JPA y la carga del contexto Spring. No hay actualmente pruebas específicas del controlador.

## Estructura principal

```text
src/
├── main/
│   ├── java/com/botanica/catalogo/
│   │   ├── config/       # Configuración de OpenAPI
│   │   ├── controller/   # Contrato REST, implementación y manejo de errores
│   │   ├── dto/          # DTOs de solicitud, respuesta y error
│   │   ├── exceptions/   # Excepciones de dominio
│   │   ├── mapper/       # Mapeo MapStruct
│   │   ├── model/        # Entidades JPA
│   │   ├── repository/   # Persistencia Spring Data
│   │   └── service/      # Casos de uso
│   └── resources/        # Configuración de Spring y datos iniciales
└── test/
    └── java/             # Pruebas de servicio, repositorio y contexto
```

## Alcance actual

La API implementa creación, consulta por ID y listado. No incluye todavía actualización ni eliminación.
