package com.example.tutoria002.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.tutoria002.models.Student;

@Repository
public interface IStudentRepository extends CrudRepository<Student, Integer> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, int id);
}
