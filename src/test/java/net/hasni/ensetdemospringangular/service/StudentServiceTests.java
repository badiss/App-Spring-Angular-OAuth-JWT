package net.hasni.ensetdemospringangular.service;

import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.StudentInformations;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.repository.StudentInformationsRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.servicesImpl.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CoursRepository coursRepository;

    @Mock
    private StudentInformationsRepository studentInformationsRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        Cours cour1 = Cours.builder()
                .titre("Francais")
                .date("22/05/2024")
                .heure("10h")
                .obligatoire(true)
                .build();

        Cours cour2 = Cours.builder()
                .titre("Anglais")
                .date("18/06/2024")
                .heure("15h")
                .obligatoire(false)
                .build();

        StudentInformations infosSt1 = StudentInformations.builder()
                .address("22 av du tartar")
                .city("cChatenay Malabry")
                .email("badis@gmail.com")
                .phoneNumber("0269854715")
                .classe("5")
                .build();

        StudentInformations infosSt2 = StudentInformations.builder()
                .address("13 Av Général Leclerc")
                .city("Chatillons")
                .email("tart@gmail.com")
                .phoneNumber("0658741545")
                .classe("9")
                .build();

        student1 = Student.builder()
                .firstName("Hasni")
                .lastName("Badis")
                .code("51")
                .programId("EEEE")
                .studentInformations(infosSt1)
                .cours(List.of(cour1, cour2))
                .build();

        student2 = Student.builder()
                .firstName("tartar")
                .lastName("karim")
                .code("51")
                .programId("POPO")
                .studentInformations(infosSt2)
                .cours(List.of(cour1, cour2))
                .build();
    }


    @Test
    public void getAllStudents_ReturnListStudents() {

        // Given: les objets students dans BeforEach
        List<Student> listStudents = List.of(student1, student2);
        Mockito.when(studentRepository.findAll()).thenReturn(listStudents);

        // When
        List<Student> list = studentService.listStudents();

        //Then
        Assertions.assertThat(list).isNotEmpty();
        Assertions.assertThat(list.size()).isEqualTo(2);

    }

    @Test
    public void getStudentByCode_ReturnStudent() {

        // Given
        String code = "51";
        Student st = Student.builder()
                .firstName("Hasni")
                .lastName("Badis")
                .code(code)
                .programId("EEEE")
                .build();
        Mockito.when(studentRepository.findByCode(Mockito.any(String.class))).thenReturn(st);

        // When
        Student student = studentService.findByCode(code);

        //Then
        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getCode()).isEqualTo("51");
    }

    @Test
    public void getStudentByProgramId_ReturnStudent() {

        // Given
        List<Student> listStudents = List.of(student1, student2);
        Mockito.when(studentRepository.findByProgramId(Mockito.any(String.class))).thenReturn(listStudents);

        // When
        List<Student> list = studentService.findByProgramId("EEEE");

        //Then
        Assertions.assertThat(list).isNotEmpty();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void affectationStudentCour_ReturnStudent() {

        // Given
        Cours C1 = Cours.builder()
                .idCours(1)
                .titre("Francais")
                .date("22/05/2024")
                .heure("10h")
                .obligatoire(true)
                .build();

        Cours C2 = Cours.builder()
                .idCours(2)
                .titre("Anglais")
                .date("18/06/2024")
                .heure("15h")
                .obligatoire(false)
                .build();

        Cours C3 = Cours.builder()
                .idCours(2)
                .titre("Test")
                .date("18/06/2024")
                .heure("15h")
                .obligatoire(false)
                .build();

        Student S1 = Student.builder()
                .studentId(1)
                .firstName("Hasni")
                .lastName("Badis")
                .code("51")
                .programId("EEEE")
                .cours(List.of(C3))
                .build();


        Mockito.when(coursRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(C1));
        Mockito.when(studentRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(S1));
        Mockito.when(studentRepository.saveAndFlush(Mockito.any(Student.class))).thenReturn(S1);

        // When
        List<Integer> listIdsCours = List.of(1, 2);
        Integer SudentId = 1;
        Student student = studentService.affectation(listIdsCours, SudentId);

        //Then
        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getCours().size()).isEqualTo(3);

    }

}
