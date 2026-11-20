package com.example.tutoria002.controllers;

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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// localhost:8089/teacher
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Gestión de profesores")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(summary = "Listar todos los profesores")
    @GetMapping
    public ResponseEntity<TeacherResponse> getAll() {
        return ResponseEntity.ok(TeacherResponse.ok("Listado de profesores", teacherService.getAll()));
    }

    @Operation(summary = "Crear un profesor",
            description = "Requiere facultyId. success=false si faltan datos o exceden la longitud (400), la facultad no existe (404) o el email ya existe (409).")
    @PostMapping
    public ResponseEntity<TeacherResponse> save(@Valid @RequestBody Teacher teacher) {
        Teacher saved = teacherService.save(teacher);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TeacherResponse.ok("Profesor creado correctamente", saved));
    }

    @Operation(summary = "Actualizar un profesor",
            description = "success=false si faltan datos (400), el profesor o la facultad no existen (404) o el email lo usa otro profesor (409).")
    @PutMapping
    public ResponseEntity<TeacherResponse> update(@Valid @RequestBody Teacher teacher) {
        Teacher updated = teacherService.update(teacher);
        return ResponseEntity.ok(TeacherResponse.ok("Profesor actualizado", updated));
    }

    @Operation(summary = "Eliminar un profesor por id",
            description = "success=false si el id no existe (404).")
    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherResponse> delete(@PathVariable int id) {
        Teacher deleted = teacherService.delete(id);
        return ResponseEntity.ok(TeacherResponse.ok("Profesor eliminado", deleted));
    }
}