package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.dto.CoursDTO;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Cours;
import net.hasni.ensetdemospringangular.entities.Payment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CoursService {

    List<Cours> listCours ();
    Cours saveCour(CoursDTO coursDTO) throws IOException;
}
