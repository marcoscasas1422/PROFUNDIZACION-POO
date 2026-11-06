# API Gestión Académica (PPOO) · ms-ppoo-g3

Por ahora están implementados los módulos de profesores (`/teacher`) y estudiantes (`/student`).

---

## CIPA

- Marco Antonio Casas
- Angela Yulieth Reyes
- Andres Felipe Castro
- Yury Andrea Delgado
- Paula Nataly Sierra

---

## Ejecución

Requisitos: JDK 17 o superior y MySQL en `127.0.0.1:3306` (la base `tutoria004` se crea sola).

Si tu usuario o clave de MySQL son distintos, edítalos en `tutoria002/src/main/resources/application.properties` (por defecto `root` / `2031`).

Para levantar la aplicación (puerto **8089**):

```bash
cd tutoria002
./mvnw spring-boot:run
```

En Windows: `mvnw.cmd spring-boot:run`

---

## Endpoints

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/teacher` | Listar profesores | 200 |
| POST | `/teacher` | Crear profesor | 201 / 400 / 409 |
| PUT | `/teacher` | Actualizar profesor (id en el body) | 200 / 400 / 404 / 409 |
| DELETE | `/teacher/{id}` | Eliminar profesor | 200 / 404 |
| GET | `/student` | Listar estudiantes | 200 |
| POST | `/student` | Crear estudiante | 201 / 400 / 409 |
| PUT | `/student` | Actualizar estudiante (id en el body) | 200 / 400 / 404 / 409 |
| DELETE | `/student/{id}` | Eliminar estudiante | 200 / 404 |

Todas las respuestas usan el mismo formato: `success` es `true` si todo salió bien y `false` si no, `message` explica el resultado y `teacher` o `student` trae el registro cuando aplica.

```json
{
  "success": true,
  "message": "Profesor creado correctamente",
  "teacher": { "id": 1, "names": "Juan", "lastNames": "Pérez", "email": "juan.perez@correo.com" }
}
```

---

## Stack

- Java 17 / Spring Boot 4
- MySQL + Spring Data JPA
- Maven
- Swagger (springdoc-openapi)
- Postman (colección disponible en `tutoria002/docs/tutoria002.postman_collection.json`)

---

## Documentación

- Swagger UI: `/swagger-ui.html`
- OpenAPI (JSON en vivo): `/v3/api-docs`
- OpenAPI estático: `tutoria002/docs/openapi.json`