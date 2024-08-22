package net.hasni.ensetdemospringangular.service;

import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.StudentInformations;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentInformationsRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.services.PaymentService;
import net.hasni.ensetdemospringangular.servicesImpl.PaymentServiceImpl;
import org.assertj.core.api.Assertions;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTests {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;


    Payment payment = null;
    Student student = null;

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

        StudentInformations infosSt = StudentInformations.builder()
                .address("22 av du tartar")
                .city("cChatenay Malabry")
                .email("badis@gmail.com")
                .phoneNumber("0269854715")
                .classe("5")
                .build();

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

    @Test
    public void CreatePayment_ReturnPayment() throws IOException {
        // Given
        Payment payment1 = Payment.builder()
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .build();

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .studentCode("X1")
                .build();

        Mockito.when(studentRepository.findByCode(Mockito.any(String.class))).thenReturn(student);
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment1);


        //When
        MultipartFile multipartFile = new MockMultipartFile("CourrierDeVotreCaisse.pdf", new FileInputStream(new File("C:\\Users\\Numeryx\\Desktop\\Perso Badis\\Workspace-back-front\\CourrierDeVotreCaisse.pdf")));
        Payment savedPayment = paymentServiceImpl.savePayment(multipartFile, paymentDTO);

        //Then
        Assertions.assertThat(savedPayment).isNotNull();
    }

    @Test
    public void UpdatePaymentStatus_ReturnPayment() throws IOException {
        // Given
        PaymentStatus status = PaymentStatus.VALIDATED;
        Payment payment = Payment.builder()
                .id(1L)
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .build();

        Mockito.when(paymentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(payment));
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);

        //When
        Payment updatePayment = paymentServiceImpl.updatePaymentStatus(status, payment.getId());

        //Then
        Assertions.assertThat(updatePayment).isNotNull();
        Assertions.assertThat(updatePayment.getStatus()).isEqualTo(status);
    }


    @Test
    public void UpdatePayment_ReturnPayment() throws IOException {
        // Given
        Student st = Student.builder()
                .firstName("Hasni")
                .lastName("Badis")
                .code("51")
                .programId("EEEE")
                .build();

        Payment payment = Payment.builder()
                .id(2L)
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .build();

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CASH)
                .status(PaymentStatus.VALIDATED)
                .date(LocalDate.now())
                .studentCode("51")
                .build();

        Mockito.when(studentRepository.findByCode(Mockito.any(String.class))).thenReturn(st);
        Mockito.when(paymentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(payment));
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);

        //When
        Payment updatePayment = paymentServiceImpl.updatePayment(paymentDTO, 2L);

        //Then
        Assertions.assertThat(updatePayment).isNotNull();
        Assertions.assertThat(updatePayment.getStatus()).isEqualTo(PaymentStatus.VALIDATED);
        Assertions.assertThat(updatePayment.getType()).isEqualTo(PaymentType.CASH);
    }

    @Test
    public void getFilePayment_ReturnPayment() throws IOException {

        // Given
        Payment payment = Payment.builder()
                .id(2L)
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .file("file:///C:/Users/Numeryx/projet_Back_Font/Payments/7a914768-0abb-411a-9e84-f83dc83dc8b2.pdf")
                .build();
        Mockito.when(paymentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(payment));

        //When
        byte[] filePayment = paymentServiceImpl.getFilePayment(2L);

        //Then
        Assertions.assertThat(filePayment).isNotNull();

    }

    @Test
    public void DeletePayment_ReturnTrue() throws IOException {

        // Given
        Payment payment = Payment.builder()
                .id(2L)
                .amount(1000+(int)(Math.random()*2000))
                .type(PaymentType.CHECK)
                .status(PaymentStatus.CREATED)
                .date(LocalDate.now())
                .file("file:///C:/Users/Numeryx/projet_Back_Font/Payments/7a914768-0abb-411a-9e84-f83dc83dc8b2.pdf")
                .build();
        Mockito.when(paymentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(payment));

        //When
        paymentServiceImpl.deletePayment(2L);

        //Then
        Mockito.verify(paymentRepository).deleteById(2L);
    }
}
