package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.Exception.ApiNotFoundException;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.services.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public Payment updatePaymentStatus (PaymentStatus statut, Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) throw new RuntimeException();
        payment.setStatus(statut);
        return paymentRepository.save(payment);
    }

    public Payment savePayment(MultipartFile file, PaymentDTO paymentDTO) throws IOException {

        //Création d'un dossier(Folder) en local: ou on va ajouter les fichiers après
        Path folderPath = Paths.get(System.getProperty("user.home"), "projet_Back_Font", "Payments");

        // Vérifier si le dossier existe, sinon il faut le créer.
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName = UUID.randomUUID().toString();
        //Création de path de fichier qu'on va ajouter dans le dossier en local
        Path filePath = Paths.get(System.getProperty("user.home"), "projet_Back_Font", "Payments", fileName+".pdf");

        //Copier le fichier a ajouter dans le path qu'on a crée.
        Files.copy(file.getInputStream(), filePath);

        //chercher le student par son code.
        Student student = studentRepository.findByCode(paymentDTO.getStudentCode());

        // Création d'un objet Payment a sauvegarder.
        Payment payment = Payment.builder()
                .file(filePath.toUri().toString())
                .date(paymentDTO.getDate())
                .amount(paymentDTO.getAmount())
                .type(paymentDTO.getType())
                .status(PaymentStatus.CREATED)
                .student(student)
                .build();
        return paymentRepository.save(payment);

    }


    public Payment updatePayment(PaymentDTO paymentDTO, Long id) throws ApiNotFoundException {

        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Pas de payment avec cet id"));

        //chercher le student par son code.
        Student student = studentRepository.findByCode(paymentDTO.getStudentCode());

        if (student == null) {
            throw new ApiNotFoundException("Il n'y a pas un étudient qui appartient à ce payment");
        }

        payment.setStatus(paymentDTO.getStatus());
        payment.setType(paymentDTO.getType());
        payment.setDate(paymentDTO.getDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setStudent(student);
        return paymentRepository.save(payment);

    }

    public byte[] getFilePayment (Long paymentId) throws ApiNotFoundException, IOException {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new ApiNotFoundException("Pas de payment avec cet id"));
        if (payment.getFile() == null) {
            throw new ApiNotFoundException("Ce payment ne contient pas un fichier");
        }
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

    public void deletePayment(Long PaymentId){
        Payment payment = paymentRepository.findById(PaymentId).orElseThrow(() -> new ApiNotFoundException("Pas de payment avec cet id"));;
        paymentRepository.deleteById(PaymentId);
    }

    @Override
    public List<Payment> listPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getPaymentsByStudent(String code) throws ApiNotFoundException {
        return paymentRepository.findByStudentCode(code);
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    public List<Payment> getPaymentsByType(PaymentType type) {
        return paymentRepository.findByType(type);
    }

    @Override
    public Payment getPaymentByID(Long id) throws ApiNotFoundException {
        return paymentRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Pas de payment avec cet id"));
    }
}
