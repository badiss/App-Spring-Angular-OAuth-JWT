package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByCode(String Code);
    List<Student> findByProgramId(String programId);
}
