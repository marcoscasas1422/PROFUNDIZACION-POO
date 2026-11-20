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
import com.example.tutoria002.dto.StudentResponse;
import com.example.tutoria002.models.Student;
import com.example.tutoria002.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// localhost:8089/student
@RequestMapping("/student")
@Tag(name = "Student", description = "Gestión de estudiantes")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Listar todos los estudiantes")
    @GetMapping
    public ResponseEntity<StudentResponse> getAll() {
        return ResponseEntity.ok(StudentResponse.ok("Listado de estudiantes", studentService.getAll()));
    }

    @Operation(summary = "Crear un estudiante",
            description = "success=true si se creó; success=false si faltan datos o exceden la longitud (400) o el email ya existe (409).")
    @PostMapping
    public ResponseEntity<StudentResponse> save(@Valid @RequestBody Student student) {
        Student saved = studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StudentResponse.ok("Estudiante creado correctamente", saved));
    }

    @Operation(summary = "Actualizar un estudiante",
            description = "success=false si faltan datos (400), el id no existe (404) o el email lo usa otro estudiante (409).")
    @PutMapping
    public ResponseEntity<StudentResponse> update(@Valid @RequestBody Student student) {
        Student updated = studentService.update(student);
        return ResponseEntity.ok(StudentResponse.ok("Estudiante actualizado", updated));
    }

    @Operation(summary = "Eliminar un estudiante por id",
            description = "success=false si el id no existe (404).")
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponse> delete(@PathVariable int id) {
        Student deleted = studentService.delete(id);
        return ResponseEntity.ok(StudentResponse.ok("Estudiante eliminado", deleted));
    }
}