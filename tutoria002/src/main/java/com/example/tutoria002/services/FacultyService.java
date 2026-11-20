package com.example.tutoria002.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.tutoria002.exceptions.DuplicateFacultyCodeException;
import com.example.tutoria002.exceptions.FacultyHasTeachersException;
import com.example.tutoria002.exceptions.FacultyNotFoundException;
import com.example.tutoria002.models.Faculty;
import com.example.tutoria002.repositories.IFacultyRepository;
import com.example.tutoria002.repositories.ITeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final IFacultyRepository facultyRepository;
    private final ITeacherRepository teacherRepository;

    public List<Faculty> getAll() {
        List<Faculty> faculties = new ArrayList<>();
        facultyRepository.findAll().forEach(faculties::add);
        return faculties;
    }

    public Faculty save(Faculty faculty) {
        if (facultyRepository.existsByCodeIgnoreCase(faculty.getCode())) {
            throw new DuplicateFacultyCodeException(faculty.getCode());
        }
        faculty.setId(0); // para que siempre cree uno nuevo
        return facultyRepository.save(faculty);
    }

    public Faculty update(Faculty faculty) {
        if (!facultyRepository.existsById(faculty.getId())) {
            throw new FacultyNotFoundException(faculty.getId());
        }
        if (facultyRepository.existsByCodeIgnoreCaseAndIdNot(faculty.getCode(), faculty.getId())) {
            throw new DuplicateFacultyCodeException(faculty.getCode());
        }
        return facultyRepository.save(faculty);
    }

    public Faculty delete(int id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        if (teacherRepository.existsByFacultyId(id)) {
            throw new FacultyHasTeachersException(id);
        }
        facultyRepository.deleteById(id);
        return faculty;
    }
}