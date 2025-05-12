package net.hasni.ensetdemospringangular.web;

import net.hasni.ensetdemospringangular.Exception.ApiMethodeArgNotValidException;
import net.hasni.ensetdemospringangular.Exception.ApiNotFoundException;
import net.hasni.ensetdemospringangular.Exception.ApiRequestException;
import net.hasni.ensetdemospringangular.dto.*;
import net.hasni.ensetdemospringangular.entities.*;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.servicesImpl.*;
import net.hasni.ensetdemospringangular.util.UserCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private StudentServiceImpl studentService;
    private PaymentServiceImpl paymentService;
    private LoginServiceImpl loginService;
    private CoursServiceImpl coursService;
    private UserServiceImpl userService;
    private EmailServiceImpl emailService;
    private FactureServiceImpl factureService;

    public StudentPaymentRestController(StudentServiceImpl studentService,
                                        PaymentServiceImpl paymentService, LoginServiceImpl loginService,
                                        CoursServiceImpl coursService, UserServiceImpl userService,
                                        EmailServiceImpl emailService, FactureServiceImpl factureService) {
        this.studentService = studentService;
        this.paymentService = paymentService;
        this.loginService = loginService;
        this.coursService = coursService;
        this.userService = userService;
        this.emailService = emailService;
        this.factureService = factureService;

    }

    /**
     * Consulter liste des payments
     * @return
     */
    @GetMapping(path="/listPayment")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> listPayment() throws ApiNotFoundException {
        List<Payment> list = paymentService.listPayments();
        if (list.isEmpty()) {
            throw new ApiNotFoundException("Liste des payments est vide, pas de payments pour l'instant");
        }
        return list;
    }

    /**
     * Consulter liste des payments de chaque étudiant.
     * @return
     */
    @GetMapping(path="/students/{code}/payments")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<Payment>> getPaymentsByStudent(@PathVariable String code) throws ApiNotFoundException  {
        List<Payment> list = paymentService.getPaymentsByStudent(code);
        if (list.isEmpty()) {
            throw new ApiNotFoundException("Liste des payments avec le code "+code+" est vide, pas des payments pour l'instant");
        }
        return new ResponseEntity<List<Payment>>(list, HttpStatus.OK);
    }

    /**
     * Consulter liste des payments par statut.
     * @return
     */
    @GetMapping(path="/paymentStatus")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByStatus(@RequestParam String status) throws ApiNotFoundException, ApiMethodeArgNotValidException{
        PaymentStatus result = null;
        for (PaymentStatus st : PaymentStatus.values()) {
            if (st.name().equalsIgnoreCase(status)) {
                result = st;
                break;
            }
        }

        if (result == null) {
            throw new ApiMethodeArgNotValidException("Vérifier la valeur de ton argument status, il n'existe pas dans l'enum: "+status);
        }

        List<Payment> list = paymentService.getPaymentsByStatus(result);
        if (list.isEmpty()) {
            throw new ApiNotFoundException("Liste des payments avec le status "+status+" est vide, pas des payments pour l'instant");
        }
        return list;
    }

    /**
     * Consulter liste des payments par type.
     * @return
     */
    @GetMapping(path="/paymentType")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> getPaymentsByType(@RequestParam String type) throws ApiMethodeArgNotValidException, ApiNotFoundException {
        PaymentType result = null;
        for (PaymentType ty : PaymentType.values()) {
            if (ty.name().equalsIgnoreCase(type)) {
                result = ty;
                break;
            }
        }
        if (result == null) {
            throw new ApiMethodeArgNotValidException("Vérifier la valeur de ton argument type, il n'existe pas dans l'enum: "+type);
        }
        List<Payment> list = paymentService.getPaymentsByType(result);
        if (list.isEmpty()) {
            throw new ApiNotFoundException("Liste des payments avec le type "+type+" est vide, pas des payments pour l'instant");
        }
        return list;
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
        return paymentService.getPaymentByID(id);
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

    @PutMapping(path ="/updatePayment/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment updatePayment(@RequestBody PaymentDTO paymentDTO, @PathVariable Long id) throws ApiRequestException {
        try {
            return paymentService.updatePayment(paymentDTO, id);
        }catch (ApiRequestException e) {
            throw new ApiRequestException("La modification du payment est failed", e.getCause());
        }

    }

    /**
     * Récupérer le file de paymnt par payment ID (Rq: pour tester le service, il faut ajouter un paymentFile avec le service avant)
     * @param paymentId
     * @return
     * @throws IOException
     */
    @GetMapping(path ="/paymentFile/{paymentId}", produces = { MediaType.APPLICATION_PDF_VALUE })
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public byte[] getFilePayment (@PathVariable Long paymentId) throws IOException, ApiNotFoundException{
        try {
            return paymentService.getFilePayment(paymentId);
        } catch (ApiNotFoundException exception) {
            throw new ApiNotFoundException("Ce payment ne contient pas un fichier, cont");
        }

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


    /**
     * Consulter liste des factures
     * @return
     */
    @GetMapping(path="/listFacture")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Facture> listFacture() throws ApiNotFoundException {
        List<Facture> list = factureService.listFacture();
        if (list.isEmpty()) {
            throw new ApiNotFoundException("Liste des factures est vide, pas de facture pour l'instant");
        }
        return list;
    }

    /**
     * Consulter liste des facture de chaque Payment.
     * @return
     */
    @GetMapping(path="/facturesPayment/{PaymentId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<Facture>> getFacturesByPayment(@PathVariable Long PaymentId) throws ApiNotFoundException  {
        List<Facture> list = factureService.getFacturesByPayment(PaymentId);
        if (list.isEmpty()) {
            throw new ApiNotFoundException("Liste des facture avec le payment "+PaymentId+" est vide, pas des factures pour l'instant");
        }
        return new ResponseEntity<List<Facture>>(list, HttpStatus.OK);
    }

    @PutMapping(path ="/updateFacture")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void updateFacture (@RequestBody List<FactureDTO> listDTO) throws ApiRequestException {
        try {
            if (listDTO.isEmpty()) {
                throw new ApiNotFoundException("La liste des facture est vide");
            }
            factureService.updateFactures(listDTO);
        }catch (ApiRequestException e) {
            throw new ApiRequestException("La modification des factures est failed", e.getCause());
        }
    }
}
