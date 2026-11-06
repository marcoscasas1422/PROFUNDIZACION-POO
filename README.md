# ms-ppoo-g3 · API de Profesores (tutoria002)

Microservicio Spring Boot (Java 17, Spring Data JPA, MySQL) con CRUD de profesores.

## Requisitos
- JDK 17 o superior
- MySQL en `127.0.0.1:3306` (la BD `tutoria004` se crea sola)
- Maven no es necesario: se usa el wrapper `mvnw`

## Configuración
Editar `tutoria002/src/main/resources/application.properties` si tu usuario/clave de MySQL son distintos
(por defecto `root` / `2031`). Puerto de la app: **8089**.

## Ejecutar
```bash
cd tutoria002
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```
O generando el jar: `./mvnw clean package -DskipTests` y `java -jar target/tutoria002-0.0.1-SNAPSHOT.jar`

## Documentación
- Swagger UI: http://localhost:8089/swagger-ui.html
- OpenAPI vivo: http://localhost:8089/v3/api-docs
- OpenAPI estático: `tutoria002/docs/openapi.json`
- Colección Postman: `tutoria002/docs/tutoria002.postman_collection.json`
  (Postman → Import; variable `baseUrl` = `http://localhost:8089`)

## Endpoints
| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/teacher` | Listar profesores | 200 |
| POST | `/teacher` | Crear profesor | 201 / 400 / 409 |
| PUT | `/teacher` | Actualizar (id en el body) | 200 / 400 / 404 / 409 |
| DELETE | `/teacher/{id}` | Eliminar | 200 / 404 |

## Formato de respuesta
`success` es `true` si todo salió bien y `false` si no. `teacher` o `student` trae el registro cuando aplica.

Crear OK (201):
```json
{ "success": true, "message": "Profesor creado correctamente",
  "teacher": { "id": 1, "names": "Juan", "lastNames": "Pérez", "email": "juan.perez@correo.com" } }
```
Email repetido (409):
```json
{ "success": false, "message": "No se creó el profesor: ya existe uno con el email juan.perez@correo.com" }
```

## Estructura
```
controllers/  TeacherController   (HTTP + validación de body)
services/     TeacherService      (reglas: existTeacher, existTeacherByEmail, delete boolean)
repositories/ ITeacherRepository  (existsByEmailIgnoreCase...)
dto/          TeacherResponse     (success, message, teacher)
config/       OpenApiConfig       (Swagger)
```
