package com.example.tutoria002.dto;

import java.util.List;

import com.example.tutoria002.models.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

/** Respuesta estándar para estudiantes: success, message, student. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta estándar de la API de estudiantes")
public class StudentResponse {

    @Schema(description = "true si la operación fue exitosa, false si no", example = "true")
    private boolean success;

    @Schema(description = "Mensaje descriptivo del resultado", example = "Estudiante creado correctamente")
    private String message;

    @Schema(description = "Estudiante creado/actualizado (si aplica)")
    private Student student;

    @Schema(description = "Listado de estudiantes (solo en el GET)")
    private List<Student> students;

    public StudentResponse() {
    }

    public StudentResponse(boolean success, String message, Student student, List<Student> students) {
        this.success = success;
        this.message = message;
        this.student = student;
        this.students = students;
    }

    public static StudentResponse ok(String message, Student student) {
        return new StudentResponse(true, message, student, null);
    }

    public static StudentResponse ok(String message, List<Student> students) {
        return new StudentResponse(true, message, null, students);
    }

    public static StudentResponse fail(String message) {
        return new StudentResponse(false, message, null, null);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
