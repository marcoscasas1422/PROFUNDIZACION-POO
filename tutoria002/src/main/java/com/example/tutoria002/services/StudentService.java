package com.example.tutoria002.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tutoria002.models.Student;
import com.example.tutoria002.repositories.IStudentRepository;

@Service
public class StudentService {

    @Autowired
    private IStudentRepository studentRepository;

    /** Crea un estudiante. Devuelve null si el email ya está registrado. */
    public Student createStudent(Student student) {
        if (existStudentByEmail(student.getEmail())) {
            return null;
        }
        student.setId(0); // fuerza INSERT
        return studentRepository.save(student);
    }

    public ArrayList<Student> getAll() {
        return (ArrayList<Student>) studentRepository.findAll();
    }

    /** Actualiza un estudiante. Devuelve null si no existe. */
    public Student updateStudent(Student student) {
        return existStudent(student.getId()) ? studentRepository.save(student) : null;
    }

    /** Elimina por id. true si lo eliminó, false si no existía. */
    public boolean deleteStudent(int id) {
        if (!existStudent(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    /** Validación: ¿existe un estudiante con ese id? */
    public boolean existStudent(int id) {
        return studentRepository.existsById(id);
    }

    /** Validación: ¿ya hay un estudiante con ese email? */
    public boolean existStudentByEmail(String email) {
        return email != null && studentRepository.existsByEmailIgnoreCase(email.trim());
    }

    /** Validación: ¿otro estudiante distinto de este id usa ese email? */
    public boolean existEmailInOtherStudent(String email, int id) {
        return email != null && studentRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), id);
    }
}
