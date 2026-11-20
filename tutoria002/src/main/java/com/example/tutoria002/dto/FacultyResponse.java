package com.example.tutoria002.dto;

import java.util.List;
import com.example.tutoria002.models.Faculty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponse {
    private boolean success;
    private String message;
    private Faculty faculty;
    private List<Faculty> faculties;

    public static FacultyResponse ok(String message, Faculty faculty) {
        return new FacultyResponse(true, message, faculty, null);
    }

    public static FacultyResponse ok(String message, List<Faculty> faculties) {
        return new FacultyResponse(true, message, null, faculties);
    }

    public static FacultyResponse fail(String message) {
        return new FacultyResponse(false, message, null, null);
    }
}