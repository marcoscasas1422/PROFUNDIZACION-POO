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
import com.example.tutoria002.dto.FacultyResponse;
import com.example.tutoria002.models.Faculty;
import com.example.tutoria002.services.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// localhost:8089/faculty
@RequestMapping("/faculty")
@Tag(name = "Faculty", description = "Gestión de facultades")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @Operation(summary = "Listar todas las facultades")
    @GetMapping
    public ResponseEntity<FacultyResponse> getAll() {
        return ResponseEntity.ok(FacultyResponse.ok("Listado de facultades", facultyService.getAll()));
    }

    @Operation(summary = "Crear una facultad",
            description = "success=false si faltan datos o exceden la longitud (400) o el code ya existe (409).")
    @PostMapping
    public ResponseEntity<FacultyResponse> save(@Valid @RequestBody Faculty faculty) {
        Faculty saved = facultyService.save(faculty);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FacultyResponse.ok("Facultad creada correctamente", saved));
    }

    @Operation(summary = "Actualizar una facultad",
            description = "success=false si faltan datos (400), el id no existe (404) o el code lo usa otra facultad (409).")
    @PutMapping
    public ResponseEntity<FacultyResponse> update(@Valid @RequestBody Faculty faculty) {
        Faculty updated = facultyService.update(faculty);
        return ResponseEntity.ok(FacultyResponse.ok("Facultad actualizada", updated));
    }

    @Operation(summary = "Eliminar una facultad por id",
            description = "success=false si el id no existe (404) o si todavía tiene docentes (409).")
    @DeleteMapping("/{id}")
    public ResponseEntity<FacultyResponse> delete(@PathVariable int id) {
        Faculty deleted = facultyService.delete(id);
        return ResponseEntity.ok(FacultyResponse.ok("Facultad eliminada", deleted));
    }
}