package com.example.tutoria002.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.tutoria002.models.Faculty;

@Repository
public interface IFacultyRepository extends CrudRepository<Faculty, Integer> {

    boolean existsByCodeIgnoreCase(String code);

    // Para validar el código en una actualización (otra facultad con el mismo código)
    boolean existsByCodeIgnoreCaseAndIdNot(String code, int id);
}