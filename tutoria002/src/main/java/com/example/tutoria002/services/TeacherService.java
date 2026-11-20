package com.example.tutoria002.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.tutoria002.exceptions.DuplicateTeacherEmailException;
import com.example.tutoria002.exceptions.FacultyNotFoundException;
import com.example.tutoria002.exceptions.TeacherNotFoundException;
import com.example.tutoria002.models.Teacher;
import com.example.tutoria002.repositories.IFacultyRepository;
import com.example.tutoria002.repositories.ITeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final ITeacherRepository teacherRepository;
    private final IFacultyRepository facultyRepository;

    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        teacherRepository.findAll().forEach(teachers::add);
        return teachers;
    }

    public Teacher save(Teacher teacher) {
        validateFaculty(teacher.getFacultyId());
        if (teacherRepository.existsByEmailIgnoreCase(teacher.getEmail())) {
            throw new DuplicateTeacherEmailException(teacher.getEmail());
        }
        teacher.setId(0); // para que siempre cree uno nuevo
        return teacherRepository.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        if (!teacherRepository.existsById(teacher.getId())) {
            throw new TeacherNotFoundException(teacher.getId());
        }
        validateFaculty(teacher.getFacultyId());
        if (teacherRepository.existsByEmailIgnoreCaseAndIdNot(teacher.getEmail(), teacher.getId())) {
            throw new DuplicateTeacherEmailException(teacher.getEmail());
        }
        return teacherRepository.save(teacher);
    }

    public Teacher delete(int id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
        teacherRepository.deleteById(id);
        return teacher;
    }

    // Regla: no se crea ni se actualiza un docente si el facultyId no existe
    private void validateFaculty(Integer facultyId) {
        if (!facultyRepository.existsById(facultyId)) {
            throw new FacultyNotFoundException(facultyId);
        }
    }
}