package net.hasni.ensetdemospringangular.web;

import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.services.LoginService;
import net.hasni.ensetdemospringangular.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class StudentPaymentRestController {

    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;
    private PaymentService paymentService;
    private LoginService loginService;

    public StudentPaymentRestController(PaymentRepository paymentRepository, StudentRepository studentRepository,
                                        PaymentService paymentService, LoginService loginService) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.paymentService = paymentService;
        this.loginService = loginService;

    }

    /**
     * Consulter liste des payments
     * @return
     */
    @GetMapping(path="/listPayment")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> listPayment() {
        return paymentRepository.findAll();
    }

    /**
     * Consulter liste des payments de chaque étudiant.
     * @return
     */
    @GetMapping(path="/students/{code}/payments")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByStudent(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }

    /**
     * Consulter liste des payments par statut.
     * @return
     */
    @GetMapping(path="/paymentSatus")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByStatus(@RequestParam PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    /**
     * Consulter liste des payments par type.
     * @return
     */
    @GetMapping(path="/paymentType")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByStatus(@RequestParam PaymentType type) {
        return paymentRepository.findByType(type);
    }

    /**
     * Consulter liste des étudiants.
     * @return
     */
    @GetMapping(path="/listStudent")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Student> listStudent() {
        return studentRepository.findAll();
    }

    /**
     * Consulter le paiement par id.
     * @param id
     * @return
     */
    @GetMapping(path="/payment/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Payment getPaymentByID (@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }

    /**
     * Consulter un étudiant par Code
     */
    @GetMapping(path="/student/{code}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Student getStudentByCode (@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    /**
     * Consulter les étudiants par filière.
     */
    @GetMapping(path="/student/{programId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    List<Student> getStudentsByProgramID (@PathVariable String programId) {
        return studentRepository.findByProgramId(programId);
    }

    /**
     * Modifier le statut de paiment
     * @param statut
     * @param id
     * @return
     */
    @PutMapping(path ="/payments/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment updatePaymentStatus (@RequestParam PaymentStatus statut, @PathVariable Long id) {
        return paymentService.updatePaymentStatus(statut, id);
    }

    /**
     * Ajouter un payment avec un fichier.
     * @param file
     * @param paymentDTO
     * @return
     * @throws IOException
     */
    @PostMapping(path ="/addPayment", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment savePayment(@RequestParam("file") MultipartFile file, PaymentDTO paymentDTO) throws IOException {
        return paymentService.savePayment(file, paymentDTO);
    }

    @PutMapping(path ="/updatePayment/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment updatePayment(@RequestBody PaymentDTO paymentDTO, @PathVariable Long id) throws IOException {
        System.out.println("****************");
        return paymentService.updatePayment(paymentDTO, id);
    }

    /**
     * Récupérer le file de paymnt par payment ID (Rq: pour tester le service, il faut ajouter un paymentFile avec le service avant)
     * @param paymentId
     * @return
     * @throws IOException
     */
    @GetMapping(path ="/paymentFile/{paymentId}", produces = { MediaType.APPLICATION_PDF_VALUE })
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public byte[] getFilePayment (@PathVariable Long paymentId) throws IOException {
       return paymentService.getFilePayment(paymentId);
    }

    @DeleteMapping("/deletePayment/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
    }

    /**
     * service Login with front
     */
    @PostMapping(path ="/login")
    public Map<String, String> login(String username, String password) {
        return loginService.loginUser(username, password);
    }

    /* Authentification Yousfi*/

    /**
     * peut importe avec quel provider je fait l'authentification (gmail, github, keyclock), si je lance url vers cette api
     * alors je peut avoir le token jwt (id token, je le prend et je le mets dans jwt.io pour voir les infos)
     * @param authentication
     * @return
     */
    @GetMapping(path = "/auth")
    @ResponseBody
    public Authentication authentication(Authentication authentication) {
        System.out.println(authentication.getName());
        return authentication;
    }

    @GetMapping(path = "/")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/notAutorized")
    public String notAutorized() {
        return "notAutorized";
    }
}
