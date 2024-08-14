package net.hasni.ensetdemospringangular.web;

import net.hasni.ensetdemospringangular.dto.AffectationStudentCoursDTO;
import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.dto.ResetPasswordDTO;
import net.hasni.ensetdemospringangular.entities.*;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.servicesImpl.*;
import net.hasni.ensetdemospringangular.util.UserCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class StudentPaymentRestController {

    private PaymentRepository paymentRepository;
    private StudentServiceImpl studentService;
    private PaymentServiceImpl paymentService;
    private LoginServiceImpl loginService;
    private CoursServiceImpl coursService;
    private UserServiceImpl userService;
    private EmailServiceImpl emailService;

    public StudentPaymentRestController(PaymentRepository paymentRepository, StudentServiceImpl studentService,
                                        PaymentServiceImpl paymentService, LoginServiceImpl loginService,
                                        CoursServiceImpl coursService, UserServiceImpl userService,
                                        EmailServiceImpl emailService) {
        this.paymentRepository = paymentRepository;
        this.studentService = studentService;
        this.paymentService = paymentService;
        this.loginService = loginService;
        this.coursService = coursService;
        this.userService = userService;
        this.emailService = emailService;

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
        return studentService.listStudents();
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
        return studentService.findByCode(code);
    }

    /**
     * Consulter les étudiants par filière.
     */
    @GetMapping(path="/studentPg/{programId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    List<Student> getStudentsByProgramID (@PathVariable String programId) {
        return studentService.findByProgramId(programId);
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



    // Les apis des cours

    /**
     * Consulter liste des cours.
     * @return
     */
    @GetMapping(path="/listCours")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Cours> listCours() {
        return coursService.listCours();
    }

    @PostMapping(path ="/addCour")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Cours saveCour(CoursDTO coursDTO) throws IOException {
        return coursService.saveCour(coursDTO);
    }

    @PostMapping(path ="/resetPasswordUser", consumes = { "application/json" })
    public boolean resetPasswordUser (@RequestBody ResetPasswordDTO resetPasswordDTO) {
        Users user = userService.ifUserExists(resetPasswordDTO.getUser());
        if (user != null) {
            Mail mail = new Mail(resetPasswordDTO.getEmail(), UserCode.getCode());
            emailService.sendPasswordByCode(mail);
            System.out.println("=============== "+UserCode.getCode());
            userService.updateUserPassword(user, UserCode.getCode());
            return true;
        } else {
            return false;
        }
    }

    @PostMapping(path ="/affectation", consumes = { "application/json" })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public @ResponseBody Student affecterStudentCours (@RequestBody AffectationStudentCoursDTO affectationStudentCoursDTO) {
        return studentService.affectation(affectationStudentCoursDTO.getCoursIds(), affectationStudentCoursDTO.getStudentId());
    }
}
