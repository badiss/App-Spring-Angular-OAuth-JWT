package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PaymentService {

    Payment updatePaymentStatus (PaymentStatus statut, Long id);
    Payment savePayment(MultipartFile file, PaymentDTO paymentDTO) throws IOException;
    Payment updatePayment(PaymentDTO paymentDTO, Long id) throws IOException;
    byte[] getFilePayment (Long paymentId) throws IOException;
    void deletePayment(Long PaymentId);
}
