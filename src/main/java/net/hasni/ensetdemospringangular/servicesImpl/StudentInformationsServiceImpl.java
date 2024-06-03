package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.repository.StudentInformationsRepository;
import net.hasni.ensetdemospringangular.services.StudentInformationsService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentInformationsServiceImpl implements StudentInformationsService {

    private StudentInformationsRepository studentInformationsRepository;

    public StudentInformationsServiceImpl(StudentInformationsRepository studentInformationsRepository) {
        this.studentInformationsRepository = studentInformationsRepository;

    }

}
