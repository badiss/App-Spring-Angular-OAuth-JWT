package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Student;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    List<Student> listStudents ();

    Student findByCode(String Code);

    List<Student> findByProgramId(String programId);

    Student affectation(List<Integer> coursIds, Integer StudentId);
}
