package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.repository.CoursRepository;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.services.CoursService;
import org.springframework.stereotype.Service;

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
}
