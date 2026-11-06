package com.example.tutoria002.dto;

import java.util.List;

import com.example.tutoria002.models.Teacher;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Respuesta estándar de la API.
 * success = true si todo salió bien, false en caso contrario.
 * Los campos nulos no se envían en el JSON.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta estándar de la API de profesores")
public class TeacherResponse {

    @Schema(description = "true si la operación fue exitosa, false si no", example = "true")
    private boolean success;

    @Schema(description = "Mensaje descriptivo del resultado", example = "Profesor creado correctamente")
    private String message;

    @Schema(description = "Profesor creado/actualizado/eliminado (si aplica)")
    private Teacher teacher;

    @Schema(description = "Listado de profesores (solo en el GET)")
    private List<Teacher> teachers;

    public TeacherResponse() {
    }

    public TeacherResponse(boolean success, String message, Teacher teacher, List<Teacher> teachers) {
        this.success = success;
        this.message = message;
        this.teacher = teacher;
        this.teachers = teachers;
    }

    public static TeacherResponse ok(String message, Teacher teacher) {
        return new TeacherResponse(true, message, teacher, null);
    }

    public static TeacherResponse ok(String message, List<Teacher> teachers) {
        return new TeacherResponse(true, message, null, teachers);
    }

    public static TeacherResponse fail(String message) {
        return new TeacherResponse(false, message, null, null);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public List<Teacher> getTeachers() { return teachers; }
    public void setTeachers(List<Teacher> teachers) { this.teachers = teachers; }
}
