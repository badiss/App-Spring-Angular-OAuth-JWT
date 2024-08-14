package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.StudentInformations;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

//@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentInformationsRepository studentInformationsRepository;
    @Autowired
    CoursRepository coursRepository;

    Student student = null;

    // cette méthode permet de créer un nouveau student pour l'utiliser après dans plusieurs test et pour éviter
    // la création dans plusieurs test.
    @BeforeEach
    void setUp() {
        Cours cour1 = Cours.builder()
                .titre("Francais")
                .date("22/05/2024")
                .heure("10h")
                .obligatoire(true)
                .build();
        coursRepository.save(cour1);

        Cours cour2 = Cours.builder()
                .titre("Anglais")
                .date("18/06/2024")
                .heure("15h")
                .obligatoire(false)
                .build();
        coursRepository.save(cour2);

        StudentInformations infosSt = StudentInformations.builder()
                .address("22 av du tartar")
                .city("cChatenay Malabry")
                .email("badis@gmail.com")
                .phoneNumber("0269854715")
                .classe("5")
                .build();
        studentInformationsRepository.save(infosSt);

        Student student1 = Student.builder()
                .firstName("Hasni")
                .lastName("Badis")
                .code("51")
                .programId("EEEE")
                .studentInformations(infosSt)
                .cours(List.of(cour1, cour2))
                .build();
        student = studentRepository.save(student1);
    }

    @AfterEach
    void tearDown() {
        studentRepository.delete(student);
    }

    @Test
    public void SaveStudent_ReturnSaved() {

        // Given
        Cours cour1 = Cours.builder()
                .titre("Gestion")
                .date("15/06/2024")
                .heure("14h")
                .obligatoire(true)
                .build();
        coursRepository.save(cour1);

        Cours cour2 = Cours.builder()
                .titre("Informatique")
                .date("18/06/2024")
                .heure("18h")
                .obligatoire(false)
                .build();
        coursRepository.save(cour2);

        StudentInformations infosSt = StudentInformations.builder()
                .address("15 av du général toutou")
                .city("Antony")
                .email("mohamed@gmail.com")
                .phoneNumber("0685411215")
                .classe("6")
                .build();
        studentInformationsRepository.save(infosSt);

        Student student = Student.builder()
                .firstName("Nouri")
                .lastName("Mohamed")
                .code("20")
                .programId("AAAA")
                .studentInformations(infosSt)
                .cours(List.of(cour1, cour2))
                .build();

        //When
        Student saveStudent = studentRepository.save(student);

        //Assert (then)
        Assertions.assertThat(saveStudent).isNotNull();
        Assertions.assertThat(saveStudent.getStudentId()).isGreaterThan(0);

    }

    @Test
    public void UpdateStudent_ReturnSaved() {

        //Given : on va utiliser le student cérer au haut de la classe.

        //When
        Student findStudent = studentRepository.findByCode("51");
        findStudent.setFirstName("Kamyl");
        findStudent.setLastName("Tatou");
        Student updateStudent = studentRepository.save(findStudent);

        //Then
        Assertions.assertThat(updateStudent).isNotNull();
        Assertions.assertThat(updateStudent.getFirstName()).isNotNull();
        Assertions.assertThat(updateStudent.getFirstName()).isEqualTo("Kamyl");
    }

    @Test
    public void DeleteStudent_ReturnEmpty() {

        //Given : on va utiliser le student cérer au haut de la classe.

        //When
        studentRepository.deleteById(student.getStudentId());
        Optional<Student> findStudent = studentRepository.findById(student.getStudentId());

        //Then
        Assertions.assertThat(findStudent).isEmpty();
    }

    @Test
    public void findStudentByCode_ReturnPresent() {

        //Given : on va utiliser le student cérer au haut de la classe.

        //When
        Student findStudent = studentRepository.findByCode("51");

        //Then
        Assertions.assertThat(findStudent).isNotNull();
        Assertions.assertThat(findStudent.getCode()).isEqualTo("51");
    }

    @Test
    public void findStudentByCode_ReturnNotPresent() {

        //Given : on va utiliser le student cérer au haut de la classe.

        //When
        Student findStudent = studentRepository.findByCode("99");

        //Then
        Assertions.assertThat(findStudent).isNull();
    }

    @Test
    public void findStudentByProgramID_ReturnPresent() {

        //Given : on va utiliser le student cérer au haut de la classe.

        //When
        List<Student> listStudents = studentRepository.findByProgramId("EEEE");

        //Then
        Assertions.assertThat(listStudents).isNotNull();
        Assertions.assertThat(listStudents.size()).isEqualTo(1);
    }
}
