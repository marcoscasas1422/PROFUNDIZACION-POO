package com.example.tutoria002.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.tutoria002.exceptions.DuplicateStudentEmailException;
import com.example.tutoria002.exceptions.StudentNotFoundException;
import com.example.tutoria002.models.Student;
import com.example.tutoria002.repositories.IStudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final IStudentRepository studentRepository;

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Student save(Student student) {
        if (studentRepository.existsByEmailIgnoreCase(student.getEmail())) {
            throw new DuplicateStudentEmailException(student.getEmail());
        }
        student.setId(0); // para que siempre cree uno nuevo
        return studentRepository.save(student);
    }

    public Student update(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new StudentNotFoundException(student.getId());
        }
        if (studentRepository.existsByEmailIgnoreCaseAndIdNot(student.getEmail(), student.getId())) {
            throw new DuplicateStudentEmailException(student.getEmail());
        }
        return studentRepository.save(student);
    }

    public Student delete(int id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.deleteById(id);
        return student;
    }
}