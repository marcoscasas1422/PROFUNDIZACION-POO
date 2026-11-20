# API Gestión Académica (PPOO) · ms-ppoo-g3

**Convocatoria 1 (evaluación final).** Implementados los módulos de profesores (`/teacher`), estudiantes (`/student`) y facultades (`/faculty`). Un profesor pertenece a una sola facultad.

---

## CIPA

- Marco Antonio Casas
- Angela Yulieth Reyes
- Andres Felipe Castro
- Yury Andrea Delgado
- Paula Nataly Sierra

---

## Ejecución

Requisitos: JDK 17 o superior y MySQL en `127.0.0.1:3306` (la base `tutoria004` y sus tablas se crean solas).

Si tu usuario o clave de MySQL son distintos, edítalos en `tutoria002/src/main/resources/application.properties` (por defecto `root` / `2031`).

Para levantar la aplicación (puerto **8089**):

```bash
cd tutoria002
./mvnw spring-boot:run
```

En Windows: `.\mvnw.cmd spring-boot:run`

---

## Endpoints

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/faculty` | Listar facultades | 200 |
| POST | `/faculty` | Crear facultad | 201 / 400 / 409 |
| PUT | `/faculty` | Actualizar facultad (id en el body) | 200 / 400 / 404 / 409 |
| DELETE | `/faculty/{id}` | Eliminar facultad | 200 / 404 / 409 |
| GET | `/teacher` | Listar profesores | 200 |
| POST | `/teacher` | Crear profesor (requiere `facultyId`) | 201 / 400 / 404 / 409 |
| PUT | `/teacher` | Actualizar profesor (id en el body) | 200 / 400 / 404 / 409 |
| DELETE | `/teacher/{id}` | Eliminar profesor | 200 / 404 |
| GET | `/student` | Listar estudiantes | 200 |
| POST | `/student` | Crear estudiante | 201 / 400 / 409 |
| PUT | `/student` | Actualizar estudiante (id en el body) | 200 / 400 / 404 / 409 |
| DELETE | `/student/{id}` | Eliminar estudiante | 200 / 404 |

Todas las respuestas usan el mismo formato: `success` es `true` si todo salió bien y `false` si no, `message` explica el resultado y `faculty`, `teacher` o `student` trae el registro cuando aplica.

Ejemplo de body para crear un profesor:

```json
{
  "names": "Ana",
  "lastNames": "Perez",
  "email": "ana.perez@correo.com",
  "facultyId": 1
}
```

---

## Reglas de negocio

Cada regla se cumple en el código. Si falla, el registro no se guarda y la respuesta trae `success: false` con el mensaje del error.

| Regla | Respuesta |
|---|---|
| No se crean dos facultades con el mismo `code` | 409 |
| No se actualiza una facultad si el id no existe | 404 |
| No se elimina una facultad si el id no existe | 404 |
| No se crea ni se actualiza un profesor si el `facultyId` no existe | 404 |
| No se elimina una facultad si todavía tiene profesores | 409 |
| No se repite el email entre profesores ni entre estudiantes | 409 |

## Validaciones de datos

Si un dato falta o excede la longitud, la respuesta es 400.

| Entidad | Campo | Regla |
|---|---|---|
| Faculty | `code` | Obligatorio, único, máximo 10 caracteres |
| Faculty | `name` | Obligatorio, máximo 100 caracteres |
| Teacher | `names`, `lastNames` | Obligatorios, máximo 50 caracteres |
| Teacher | `email` | Obligatorio, formato válido, máximo 100 caracteres |
| Teacher | `facultyId` | Obligatorio |
| Student | `names`, `lastNames` | Obligatorios, máximo 50 caracteres |
| Student | `email` | Obligatorio, formato válido, máximo 100 caracteres |

---

## Estructura del código

`tutoria002/src/main/java/com/example/tutoria002/`

| Paquete | Contenido |
|---|---|
| `models` | Entidades: `Faculty`, `Teacher`, `Student` |
| `repositories` | `IFacultyRepository`, `ITeacherRepository`, `IStudentRepository` |
| `services` | Lógica y reglas de negocio |
| `controllers` | Endpoints REST |
| `dto` | Respuestas: `FacultyResponse`, `TeacherResponse`, `StudentResponse`, `ErrorResponse` |
| `exceptions` | Excepciones de negocio y `GlobalExceptionHandler` |
| `config` | Configuración de Swagger |

## Cambios de esta convocatoria

- Nueva entidad **Faculty** y relación con **Teacher** mediante `facultyId`.
- Reglas de negocio implementadas con **excepciones propias**, convertidas en respuestas JSON por un manejador global.
- **Validaciones de longitud** y campos obligatorios con Bean Validation (`@Valid`).
- Uso de **Lombok** en entidades, DTO, servicios y controladores.
- Lógica movida de los controladores a los servicios.

---

## Stack

- Java 17 / Spring Boot 4
- MySQL + Spring Data JPA
- Lombok y Bean Validation
- Maven
- Swagger (springdoc-openapi)
- Postman (colección en `tutoria002/docs/tutoria002.postman_collection.json`)

---

## Documentación

- Swagger UI: `/swagger-ui.html`
- OpenAPI (JSON en vivo): `/v3/api-docs`
- OpenAPI estático: `tutoria002/docs/openapi.json`