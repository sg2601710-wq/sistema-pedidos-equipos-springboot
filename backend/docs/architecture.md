# Arquitectura del Backend

Este backend usa una arquitectura en capas típica de Spring Boot:

- `controllers`: entrada HTTP. En Spring no hace falta una carpeta `routes`; los endpoints se definen con `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc. dentro de los controllers.
- `services`: lógica de negocio y coordinación de casos de uso.
- `repositories`: acceso a datos mediante Spring Data JPA.
- `models`: entidades JPA.
- `dtos`: objetos para entrada y salida de la API, separados de las entidades.
- `config`: configuración de beans, CORS, Swagger/OpenAPI, seguridad u otros componentes transversales.
- `exceptions`: manejo de errores de dominio/API. Equivale parcialmente al manejo centralizado que en Express suele resolverse con middlewares.
- `utils`: utilidades generales sin estado ni dependencia fuerte del framework.

Carpetas fuera de `src/main/java`:

- `data`: archivo SQLite local. Los archivos de base de datos se ignoran en Git.
- `docs`: documentación técnica del proyecto.
- `scripts`: scripts de inicialización, migraciones manuales o seeders.

En Spring Boot no suele existir una carpeta `middlewares` como en Express. Cuando haga falta comportamiento transversal se puede usar `filters`, `interceptors`, `exceptions` con `@ControllerAdvice`, o configuración específica dentro de `config`.
