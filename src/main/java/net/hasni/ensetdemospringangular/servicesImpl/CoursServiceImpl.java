package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.services.CoursService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    private CoursRepository coursRepository;

    public CoursServiceImpl(PaymentRepository paymentRepository, StudentRepository studentRepository, CoursRepository coursRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.coursRepository = coursRepository;
    }


    public List<Cours> listCours () {
       return coursRepository.findAll();
    }

    @Override
    public Cours saveCour(CoursDTO coursDTO) throws IOException {
        // Création d'un objet Cours a sauvegarder.
        Cours cours = Cours.builder()
                .titre(coursDTO.getTitre())
                .date(coursDTO.getDate())
                .heure(coursDTO.getHeure())
                .obligatoire(coursDTO.getObligatoire())
                .build();
        return coursRepository.save(cours);
    }
}
