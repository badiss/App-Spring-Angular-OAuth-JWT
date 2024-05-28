package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByCode(String Code);
    List<Student> findByProgramId(String programId);
}
