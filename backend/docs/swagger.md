# Documentacion Swagger

La documentacion OpenAPI se genera automaticamente con `springdoc-openapi`.

## URLs

- Swagger UI: `http://localhost:8080/api/docs`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Autenticacion

Los endpoints protegidos usan JWT con el esquema `bearerAuth`.

En Swagger UI:

1. Iniciar sesion con `POST /usuarios/sesion`.
2. Copiar el token devuelto en `data.token`.
3. Presionar `Authorize`.
4. Ingresar el token como Bearer JWT.

## Notas

- `GET /health-check`, `POST /usuarios` y `POST /usuarios/sesion` son publicos.
- Los endpoints administrativos de equipos, estados, roles y usuarios requieren rol `ADMIN`.
