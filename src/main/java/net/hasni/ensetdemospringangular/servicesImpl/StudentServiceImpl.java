package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.services.StudentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private CoursRepository coursRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CoursRepository coursRepository) {

        this.studentRepository = studentRepository;
        this.coursRepository = coursRepository;
    }
    @Override
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findByCode(String Code) {
        Student student = studentRepository.findByCode(Code);
       // if (student.getStudentId() == null) throw new RuntimeException();
        return student;
    }

    @Override
    public List<Student> findByProgramId(String programId) {
        return studentRepository.findByProgramId(programId);
    }

    @Override
    public Student affectation(List<Integer> coursIds, Integer studentId) {
        List<Cours> listCours = new ArrayList<>();
        if (coursIds.size() != 0 && studentId != null) {

            for(Integer item :coursIds) {
                Cours cour = coursRepository.findById(item).get();
                if (cour.getIdCours() == null) throw new RuntimeException();
                listCours.add(cour);
            }

            Student student = studentRepository.findById(studentId).get();
            if (student.getStudentId() == null) throw new RuntimeException();
            if (student.getCours().size() != 0) {
                listCours.addAll(student.getCours());
            }
            student.setCours(listCours);
            return studentRepository.saveAndFlush(student);
        } else {
            return null;
        }
    }
}
