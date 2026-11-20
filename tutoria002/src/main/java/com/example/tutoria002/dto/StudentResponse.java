package com.example.tutoria002.dto;

import java.util.List;
import com.example.tutoria002.models.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private boolean success;
    private String message;
    private Student student;
    private List<Student> students;

    public static StudentResponse ok(String message, Student student) {
        return new StudentResponse(true, message, student, null);
    }

    public static StudentResponse ok(String message, List<Student> students) {
        return new StudentResponse(true, message, null, students);
    }

    public static StudentResponse fail(String message) {
        return new StudentResponse(false, message, null, null);
    }
}