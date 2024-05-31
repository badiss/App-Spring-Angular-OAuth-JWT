package net.hasni.ensetdemospringangular.services;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.entities.StudentInformations;
import net.hasni.ensetdemospringangular.repository.StudentInformationsRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentInformationsService {

    private StudentInformationsRepository studentInformationsRepository;

    public StudentInformationsService(StudentInformationsRepository studentInformationsRepository) {
        this.studentInformationsRepository = studentInformationsRepository;

    }

}
