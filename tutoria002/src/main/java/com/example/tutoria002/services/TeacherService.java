package com.example.tutoria002.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tutoria002.models.Teacher;
import com.example.tutoria002.repositories.ITeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private ITeacherRepository teacherRepository;

    /** Crea un profesor. Devuelve null si el email ya está registrado. */
    public Teacher createTeacher(Teacher teacher) {
        if (existTeacherByEmail(teacher.getEmail())) {
            return null;
        }
        teacher.setId(0); // fuerza INSERT: nunca sobrescribe un registro existente
        return teacherRepository.save(teacher);
    }

    public ArrayList<Teacher> getAll() {
        // Select * from teacher;
        return (ArrayList<Teacher>) teacherRepository.findAll();
    }

    /** Actualiza un profesor. Devuelve null si no existe. */
    public Teacher updateTeacher(Teacher teacher) {
        return existTeacher(teacher.getId()) ? teacherRepository.save(teacher) : null;
    }

    /** Elimina por id. true si lo eliminó, false si no existía. */
    public boolean deleteTeacher(int id) {
        if (!existTeacher(id)) {
            return false;
        }
        teacherRepository.deleteById(id);
        return true;
    }

    /** Validación: ¿existe un profesor con ese id? */
    public boolean existTeacher(int id) {
        return teacherRepository.existsById(id);
    }

    /** Validación: ¿ya hay un profesor con ese email? */
    public boolean existTeacherByEmail(String email) {
        return email != null && teacherRepository.existsByEmailIgnoreCase(email.trim());
    }

    /** Validación: ¿otro profesor distinto de este id usa ese email? */
    public boolean existEmailInOtherTeacher(String email, int id) {
        return email != null && teacherRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), id);
    }
}
