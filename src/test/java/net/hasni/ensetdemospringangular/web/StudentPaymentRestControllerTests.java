package net.hasni.ensetdemospringangular.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.hasni.ensetdemospringangular.dto.AffectationStudentCoursDTO;
import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentInformationsRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.servicesImpl.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = StudentPaymentRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudentPaymentRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private PaymentServiceImpl paymentService;

    @MockBean
    private StudentServiceImpl studentService;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CoursRepository coursRepository;

    @MockBean
    private StudentInformationsRepository studentInformationsRepository;

    @MockBean
    private LoginServiceImpl loginService;

    @MockBean
    private CoursServiceImpl coursService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private EmailServiceImpl emailService;

    @MockBean
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private ObjectMapper objectMapper;

    private Payment payment1;
    private Payment payment2;
    private Student student1;
    private Cours cour1;

    @BeforeEach
    void setUp() {

        cour1 = Cours.builder()
                .titre("Francais")
                .date("22/05/2024")
                .heure("10h")
                .obligatoire(true)
                .build();

        student1 = Student.builder()
                .firstName("Hasni")
                .lastName("Badis")
                .code("51")
                .programId("EEEE")
                .build();

        payment1 = Payment.builder()
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .student(student1)
                .build();
        payment2 = Payment.builder()
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CASH)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .student(student1)
                .build();
    }

    @Test
    public void getListPayment_ReturnListPayment() throws Exception {

        //Given
        List<Payment> listPayment = List.of(payment1, payment2);
        given(paymentService.listPayments()).willReturn(listPayment);

        //When
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/listPayment").accept(MediaType.APPLICATION_JSON)).andReturn();

        //Then
        int status = response.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(response.getResponse().getContentAsString().length() > 0);
    }

    @Test
    public void getListPaymentByStudent_ReturnListPayment() throws Exception {

        //Given
        List<Payment> listPayment = List.of(payment1, payment2);
        given(paymentService.getPaymentsByStudent(Mockito.any(String.class))).willReturn(listPayment);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/students/51/payments")
                        .contentType(MediaType.APPLICATION_JSON));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));


    }

    @Test
    public void getStudentByCode_ReturnStudent() throws Exception {

        //Given
        given(studentService.findByCode(Mockito.any(String.class))).willReturn(student1);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/51")
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Hasni")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Badis")));

    }

    @Test
    public void createCour_ReturnCour() throws Exception {

        //Given
        CoursDTO courDTO = CoursDTO.builder()
                .titre("Francais")
                .date("22/05/2024")
                .heure("10h")
                .obligatoire(true)
                .build();
        given(coursService.saveCour(Mockito.any(CoursDTO.class))).willReturn(cour1);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/addCour")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courDTO)));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titre", CoreMatchers.is(cour1.getTitre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.heure", CoreMatchers.is("10h")));

    }

    @Test
    public void affectationStudentCours_ReturnStudent() throws Exception {

        //Given
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

        List<Integer> listIdsCours = List.of(1, 2);
        Integer SudentId = 1;

        AffectationStudentCoursDTO ASC = AffectationStudentCoursDTO.builder()
                .coursIds(listIdsCours)
                .studentId(SudentId)
                        .build();

        given(studentService.affectation(listIdsCours, SudentId)).willReturn(student1);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/affectation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ASC)));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Hasni")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Badis")));

    }

    @Test
    public void updatePayment_ReturnPayment() throws Exception {

        //Given
        Payment payment = Payment.builder()
                .id(1L)
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .student(student1)
                .build();

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .studentCode("EEEE")
                .build();
        given(paymentService.updatePayment(Mockito.any(PaymentDTO.class), Mockito.any(Long.class))).willReturn(payment);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/updatePayment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDTO)));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is("CHECK")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("CREATED")));

    }

    @Test
    public void deletePayment_ReturnVoid() throws Exception {

        //Given
        Payment payment = Payment.builder()
                .id(1L)
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .student(student1)
                .build();

        doNothing().when(paymentService).deletePayment(1L);

        //When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/deletePayment/1")
                .contentType(MediaType.APPLICATION_JSON));


        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
