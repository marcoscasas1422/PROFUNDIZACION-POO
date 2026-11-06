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

import com.example.tutoria002.dto.StudentResponse;
import com.example.tutoria002.models.Student;
import com.example.tutoria002.services.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
// localhost:8089/student
@RequestMapping("/student")
@Tag(name = "Student", description = "Gestión de estudiantes")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Listar todos los estudiantes")
    @GetMapping
    public ResponseEntity<StudentResponse> getAll() {
        return ResponseEntity.ok(StudentResponse.ok("Listado de estudiantes", studentService.getAll()));
    }

    @Operation(summary = "Crear un estudiante",
            description = "success=true si se creó; success=false si faltan datos (400) o el email ya existe (409).")
    @PostMapping
    public ResponseEntity<StudentResponse> save(@RequestBody Student student) {
        String error = validate(student);
        if (error != null) {
            return ResponseEntity.badRequest().body(StudentResponse.fail(error));
        }
        Student created = studentService.createStudent(student);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(StudentResponse.fail("No se creó el estudiante: ya existe uno con el email " + student.getEmail()));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StudentResponse.ok("Estudiante creado correctamente", created));
    }

    @Operation(summary = "Actualizar un estudiante",
            description = "Requiere el id en el body. 404 si no existe, 409 si el email lo usa otro estudiante.")
    @PutMapping
    public ResponseEntity<StudentResponse> update(@RequestBody Student student) {
        String error = validate(student);
        if (error != null) {
            return ResponseEntity.badRequest().body(StudentResponse.fail(error));
        }
        if (!studentService.existStudent(student.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(StudentResponse.fail("No se actualizó: no existe un estudiante con id " + student.getId()));
        }
        if (studentService.existEmailInOtherStudent(student.getEmail(), student.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(StudentResponse.fail("No se actualizó: el email " + student.getEmail() + " ya lo usa otro estudiante"));
        }
        return ResponseEntity.ok(StudentResponse.ok("Estudiante actualizado correctamente",
                studentService.updateStudent(student)));
    }

    @Operation(summary = "Eliminar un estudiante por id",
            description = "success=true si lo eliminó; 404 con success=false si no existe.")
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponse> delete(@PathVariable int id) {
        return studentService.deleteStudent(id)
                ? ResponseEntity.ok(StudentResponse.ok("Estudiante eliminado correctamente", (Student) null))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(StudentResponse.fail("No se eliminó: no existe un estudiante con id " + id));
    }

    private String validate(Student s) {
        if (s == null) return "El body es obligatorio";
        if (isBlank(s.getNames())) return "El campo names es obligatorio";
        if (isBlank(s.getLastNames())) return "El campo lastNames es obligatorio";
        if (isBlank(s.getEmail())) return "El campo email es obligatorio";
        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
