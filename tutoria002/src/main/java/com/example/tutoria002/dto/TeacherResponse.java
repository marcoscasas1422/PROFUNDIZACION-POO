package com.example.tutoria002.dto;

import java.util.List;
import com.example.tutoria002.models.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {
    private boolean success;
    private String message;
    private Teacher teacher;
    private List<Teacher> teachers;

    public static TeacherResponse ok(String message, Teacher teacher) {
        return new TeacherResponse(true, message, teacher, null);
    }

    public static TeacherResponse ok(String message, List<Teacher> teachers) {
        return new TeacherResponse(true, message, null, teachers);
    }

    public static TeacherResponse fail(String message) {
        return new TeacherResponse(false, message, null, null);
    }
}