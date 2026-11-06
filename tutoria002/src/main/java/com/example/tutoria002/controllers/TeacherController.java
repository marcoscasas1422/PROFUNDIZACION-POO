package com.example.tutoria002.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tutoria002.dto.TeacherResponse;
import com.example.tutoria002.models.Teacher;
import com.example.tutoria002.services.TeacherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
// localhost:8089/teacher
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Gestión de profesores")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "Listar todos los profesores")
    @GetMapping
    public ResponseEntity<TeacherResponse> getAll() {
        return ResponseEntity.ok(TeacherResponse.ok("Listado de profesores", teacherService.getAll()));
    }

    @Operation(summary = "Crear un profesor",
            description = "Responde success=true si se creó; success=false si faltan datos (400) o el email ya existe (409).")
    @PostMapping
    public ResponseEntity<TeacherResponse> save(@RequestBody Teacher teacher) {
        String error = validate(teacher);
        if (error != null) {
            return ResponseEntity.badRequest().body(TeacherResponse.fail(error));
        }
        Teacher created = teacherService.createTeacher(teacher);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(TeacherResponse.fail("No se creó el profesor: ya existe uno con el email " + teacher.getEmail()));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TeacherResponse.ok("Profesor creado correctamente", created));
    }

    @Operation(summary = "Actualizar un profesor",
            description = "Requiere el id en el body. 404 si no existe, 409 si el email lo usa otro profesor.")
    @PutMapping
    public ResponseEntity<TeacherResponse> update(@RequestBody Teacher teacher) {
        String error = validate(teacher);
        if (error != null) {
            return ResponseEntity.badRequest().body(TeacherResponse.fail(error));
        }
        if (!teacherService.existTeacher(teacher.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(TeacherResponse.fail("No se actualizó: no existe un profesor con id " + teacher.getId()));
        }
        if (teacherService.existEmailInOtherTeacher(teacher.getEmail(), teacher.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(TeacherResponse.fail("No se actualizó: el email " + teacher.getEmail() + " ya lo usa otro profesor"));
        }
        return ResponseEntity.ok(TeacherResponse.ok("Profesor actualizado correctamente",
                teacherService.updateTeacher(teacher)));
    }

    @Operation(summary = "Eliminar un profesor por id",
            description = "success=true si lo eliminó; 404 con success=false si no existe.")
    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherResponse> delete(@PathVariable int id) {
        return teacherService.deleteTeacher(id)
                ? ResponseEntity.ok(TeacherResponse.ok("Profesor eliminado correctamente", (Teacher) null))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(TeacherResponse.fail("No se eliminó: no existe un profesor con id " + id));
    }

    private String validate(Teacher t) {
        if (t == null) return "El body es obligatorio";
        if (isBlank(t.getNames())) return "El campo names es obligatorio";
        if (isBlank(t.getLastNames())) return "El campo lastNames es obligatorio";
        if (isBlank(t.getEmail())) return "El campo email es obligatorio";
        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
