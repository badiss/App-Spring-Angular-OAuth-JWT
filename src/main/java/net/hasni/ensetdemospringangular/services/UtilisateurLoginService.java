package net.hasni.ensetdemospringangular.services;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.UtilisateurLogin;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.repository.UtilisateurLoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class UtilisateurLoginService {

    private UtilisateurLoginRepository utilisateurLoginRepository;

    public UtilisateurLoginService(UtilisateurLoginRepository utilisateurLoginRepository) {
        this.utilisateurLoginRepository = utilisateurLoginRepository;

    }

    public UtilisateurLogin getUtilisateurLogin (String username) {
        UtilisateurLogin utilisateurLogin = utilisateurLoginRepository.findByUsername(username);
        return utilisateurLogin;
    }

}
