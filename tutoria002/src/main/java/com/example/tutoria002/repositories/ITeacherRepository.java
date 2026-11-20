package com.example.tutoria002.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.tutoria002.models.Teacher;

@Repository
public interface ITeacherRepository extends CrudRepository<Teacher, Integer> {

    // Select count(*) > 0 from teacher where lower(email) = lower(?)
    boolean existsByEmailIgnoreCase(String email);

    // Para validar el email en una actualización (otro profesor con el mismo email)
    boolean existsByEmailIgnoreCaseAndIdNot(String email, int id);

    // Para saber si una facultad todavía tiene docentes
    boolean existsByFacultyId(Integer facultyId);
}